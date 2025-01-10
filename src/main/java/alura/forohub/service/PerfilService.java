package alura.forohub.service;

import alura.forohub.dto.PerfilDTO;
import alura.forohub.entity.Perfil;
import alura.forohub.entity.Usuario;
import alura.forohub.repository.PerfilRepository;
import alura.forohub.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilService {
    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(PerfilRepository perfilRepository, UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PerfilDTO registrar(PerfilDTO perfilDTO) {
        Perfil perfil = new Perfil();
        perfil.setNombre(perfilDTO.nombre());
        Perfil perfilGuardado = perfilRepository.save(perfil);
        return convertirAPerfilDTO(perfilGuardado);
    }

    public List<PerfilDTO> listarTodos() {
        return perfilRepository.findAll().stream()
                .map(this::convertirAPerfilDTO)
                .collect(Collectors.toList());
    }

    public PerfilDTO buscarPorId(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado"));
        return convertirAPerfilDTO(perfil);
    }

    public PerfilDTO actualizarPorId(Long id, PerfilDTO perfilDTO) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado"));
        perfil.setNombre(perfilDTO.nombre());
        Perfil perfilActualizado = perfilRepository.save(perfil);
        return convertirAPerfilDTO(perfilActualizado);
    }

    public void eliminarPerfil(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado");
        }
        perfilRepository.deleteById(id);
    }

    @Transactional
    public void asignarUsuario(Long perfilId, Long usuarioId) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Verifica si el usuario ya está asociado al perfil
            perfil.getUsuarios().add(usuario);
            perfilRepository.save(perfil);

    }


    private PerfilDTO convertirAPerfilDTO(Perfil perfil) {
        return new PerfilDTO(perfil.getId(), perfil.getNombre());
    }
}
