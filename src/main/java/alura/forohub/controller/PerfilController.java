package alura.forohub.controller;

import alura.forohub.dto.PerfilDTO;
import alura.forohub.service.PerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping
    public ResponseEntity<PerfilDTO> registrar(@RequestBody @Valid PerfilDTO perfilDTO) {
        PerfilDTO nuevoPerfil = perfilService.registrar(perfilDTO);
        URI location = URI.create("/perfiles/" + nuevoPerfil.id());
        return ResponseEntity.created(location).body(nuevoPerfil);
    }

    @GetMapping
    public ResponseEntity<List<PerfilDTO>> listarPerfiles() {
        List<PerfilDTO> perfiles = perfilService.listarTodos();
        return ResponseEntity.ok(perfiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> traerPerfil(@PathVariable Long id) {
        PerfilDTO perfil = perfilService.buscarPorId(id);
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> actualizarPerfil(@PathVariable Long id, @RequestBody @Valid PerfilDTO perfilDTO) {
        PerfilDTO perfilActualizado = perfilService.actualizarPorId(id, perfilDTO);
        return ResponseEntity.ok(perfilActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Long id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{perfilId}/asignar-usuario/{usuarioId}")
    public ResponseEntity<Void> asignarUsuario(@PathVariable Long perfilId, @PathVariable Long usuarioId) {
        perfilService.asignarUsuario(perfilId, usuarioId);
        return ResponseEntity.ok().build();
    }
}