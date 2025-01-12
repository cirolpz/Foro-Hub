package alura.forohub.controller;

import alura.forohub.dto.CursoRequestDTO;
import alura.forohub.dto.CursoResponseDTO;
import alura.forohub.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {
    private final CursoService cursoService;
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> registrar(@RequestBody @Valid CursoRequestDTO cursoDTO) {
        CursoResponseDTO nuevoCurso = cursoService.registrar(cursoDTO);
        URI location = URI.create("/cursos/" + nuevoCurso.id());
        return ResponseEntity.created(location).body(nuevoCurso);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listarCursos() {
        List<CursoResponseDTO> cursos = cursoService.listarTodos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> traerCurso(@PathVariable Long id) {
        CursoResponseDTO curso = cursoService.buscarPorId(id);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoRequestDTO cursoDTO) {
        cursoService.validarExistenciaCurso(id);
        CursoResponseDTO cursoActualizado = cursoService.actualizarPorId(id, cursoDTO);
        return ResponseEntity.ok(cursoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}