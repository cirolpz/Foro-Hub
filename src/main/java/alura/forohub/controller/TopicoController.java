package alura.forohub.controller;

import alura.forohub.dto.TopicoRequestDTO;
import alura.forohub.dto.TopicoResponseDTO;
import alura.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> registrar(@RequestBody @Valid TopicoRequestDTO topicoRequestDTO) {
        TopicoResponseDTO   nuevoTopicoDTO = topicoService.registrar(topicoRequestDTO);
        URI location = URI.create("/topicos/" + nuevoTopicoDTO.id());
        return ResponseEntity.created(location).body(nuevoTopicoDTO);
    }
    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listado(@RequestParam(required = false) String curso,   // Filtro por nombre del curso
                                                           @RequestParam(required = false) LocalDateTime fecha_creacion,  // Filtro por fecha de creación
                                                           @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC ) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listado(curso, fecha_creacion, paginacion));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO>listado(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO>listadoActualizar(@PathVariable Long id, @Valid @RequestBody TopicoRequestDTO topicoRequestDTO) {
        topicoService.validarExistenciaTopico(id);

        TopicoResponseDTO topicoActualizado = topicoService.actualizarPorId(id, topicoRequestDTO);
        return ResponseEntity.ok(topicoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id); // Llama al servicio
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }



}
