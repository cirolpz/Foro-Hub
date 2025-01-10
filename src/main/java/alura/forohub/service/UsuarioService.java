package alura.forohub.service;
import alura.forohub.dto.UsuarioRequestDTO;
import alura.forohub.dto.UsuarioResponseDTO;
import alura.forohub.entity.Usuario;
import alura.forohub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return convertirAUsuarioDTO(usuario);
    }

    public void validarExistenciaUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    public UsuarioResponseDTO actualizarPorId(Long id, @Valid UsuarioRequestDTO usuarioRequestDTO) {
        validarExistenciaUsuario(id);
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
        );
        usuarioExistente.setNombre(usuarioRequestDTO.nombre());
        usuarioExistente.setCorreoElectronico(usuarioRequestDTO.email());
        // Encripta la contraseña si se proporciona
        if (usuarioRequestDTO.contrasena() != null && !usuarioRequestDTO.contrasena().isEmpty()) {
            usuarioExistente.setContrasena(passwordEncoder.encode(usuarioRequestDTO.contrasena()));
        }
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return convertirAUsuarioDTO(usuarioActualizado);
    }

    public void eliminarUsuario(Long id) {
        validarExistenciaUsuario(id);
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponseDTO registrar(@Valid UsuarioRequestDTO usuarioDTO) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(usuarioDTO.nombre());
        nuevoUsuario.setCorreoElectronico(usuarioDTO.email());
        // Encripta la contraseña antes de guardarla
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuarioDTO.contrasena()));
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return convertirAUsuarioDTO(usuarioGuardado);
    }

    private UsuarioResponseDTO convertirAUsuarioDTO(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico());
    }
}
