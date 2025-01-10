package alura.forohub.controller;

import alura.forohub.dto.PerfilDTO;
import alura.forohub.dto.UsuarioRequestDTO;
import alura.forohub.dto.UsuarioResponseDTO;
import alura.forohub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO nuevoUsuario = usuarioService.registrar(usuarioDTO);
        URI location = URI.create("/usuarios/" + nuevoUsuario.id());
        return ResponseEntity.created(location).body(nuevoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> traerUsuario(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        usuarioService.validarExistenciaUsuario(id);
        UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarPorId(id, usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/perfiles")
    public ResponseEntity<Set<PerfilDTO>> listarPerfilesDeUsuario(@PathVariable Long id) {
        Set<PerfilDTO> perfiles = usuarioService.listarPerfilesDeUsuario(id);
        return ResponseEntity.ok(perfiles);
    }
}
