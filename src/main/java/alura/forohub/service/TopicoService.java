package alura.forohub.service;

import alura.forohub.dto.CursoDTO;
import alura.forohub.dto.TopicoRequestDTO;
import alura.forohub.dto.TopicoResponseDTO;
import alura.forohub.dto.UsuarioDTO;
import alura.forohub.entity.Curso;
import alura.forohub.entity.Topico;
import alura.forohub.entity.Usuario;
import alura.forohub.repository.CursoRepository;
import alura.forohub.repository.TopicoRepository;
import alura.forohub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public TopicoResponseDTO registrar(@Valid TopicoRequestDTO topicoRequestDTO) {
        // Verificar si ya existe un tópico con el mismo título y mensaje
        boolean existe = topicoRepository.existsByTitleAndMessage(
                topicoRequestDTO.title(),
                topicoRequestDTO.message()
        );
        if (existe) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ya existe un tópico con el mismo título y mensaje."
            );
        }

        // Busca entidades relacionadas (autor y curso)
        Usuario autor = usuarioRepository.findById(topicoRequestDTO.autorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado."
                ));
        Curso curso = cursoRepository.findById(topicoRequestDTO.cursoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Curso no encontrado."
                ));

        // Crea y guarda el nuevo tópico
        Topico topico = new Topico(
                null, // ID generado automáticamente
                topicoRequestDTO.title(),
                topicoRequestDTO.message(),
                LocalDateTime.now(),
                "ABIERTO", // Estado inicial
                autor,
                curso
        );
        topicoRepository.save(topico);

        // Devuelve un DTO con los datos del tópico creado
        return new TopicoResponseDTO(
                topico.getId(),
                topico.getTitle(),
                topico.getMessage(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                new UsuarioDTO(autor.getId(), autor.getNombre()),
                new CursoDTO(curso.getId(), curso.getNombre())
        );
    }

    public Page<TopicoResponseDTO> listado(String cursoNombre, LocalDateTime fecha_creacion, Pageable paginacion) {

        Specification<Topico> spec = Specification.where(null);

        if (cursoNombre != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("curso").get("nombre"), "%" + cursoNombre + "%"));
        }

        if (fecha_creacion != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("fechaCreacion"), fecha_creacion)); // Usar "fechaCreacion" en lugar de "fecha_creacion"
        }

        Page<Topico> topicos = topicoRepository.findAll(spec, paginacion);

        return topicos.map(topico -> {
            Usuario autor = topico.getAutor();
            Curso curso = topico.getCurso();
            return new TopicoResponseDTO(
                    topico.getId(),
                    topico.getTitle(),
                    topico.getMessage(),
                    topico.getFechaCreacion(),
                    topico.getStatus(),
                    new UsuarioDTO(autor.getId(), autor.getNombre()),
                    new CursoDTO(curso.getId(), curso.getNombre())
            );
        });
    }

    public TopicoResponseDTO buscarPorId(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tópico no encontrado."
                ));

        Usuario autor = topico.getAutor();
        Curso curso = topico.getCurso();
        if (topico.getTitle() == null || topico.getMessage() == null || topico.getFechaCreacion() == null || topico.getStatus() == null || autor.getId() == null || curso.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Campos obligatorios del tópico están incompletos."
            );
        }
        return new TopicoResponseDTO(
                topico.getId(),
                topico.getTitle(),
                topico.getMessage(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                new UsuarioDTO(autor.getId(), autor.getNombre()),
                new CursoDTO(curso.getId(), curso.getNombre())
        );
    }

    public void validarExistenciaTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "El tópico con ID " + id + " no existe."
            );
        }
    }

    public TopicoResponseDTO actualizarPorId(Long id, TopicoRequestDTO requestDTO) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El tópico con ID " + id + " no existe."
                ));

        topico.setTitle(requestDTO.title());
        topico.setMessage(requestDTO.message());
        topico.setAutor(usuarioRepository.findById(requestDTO.autorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El autor con ID " + requestDTO.autorId() + " no existe."
                )));
        topico.setCurso(cursoRepository.findById(requestDTO.cursoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El curso con ID " + requestDTO.cursoId() + " no existe."
                )));

        Topico topicoActualizado = topicoRepository.save(topico);
        return new TopicoResponseDTO(
                topicoActualizado.getId(),
                topicoActualizado.getTitle(),
                topicoActualizado.getMessage(),
                topicoActualizado.getFechaCreacion(),
                topicoActualizado.getStatus(),
                new UsuarioDTO(topicoActualizado.getAutor().getId(), topicoActualizado.getAutor().getNombre()),
                new CursoDTO(topicoActualizado.getCurso().getId(), topicoActualizado.getCurso().getNombre())
        );
    }

    public void eliminarTopico(Long id) {
        if (topicoRepository.findById(id).isPresent()) {
            topicoRepository.deleteById(id); // Elimina el tópico
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado."); // Maneja error 404
        }
    }
}
