package alura.forohub.service;

import alura.forohub.dto.RespuestaDTO;
import alura.forohub.entity.Respuesta;
import alura.forohub.repository.RespuestaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;

    public RespuestaService(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }
    public RespuestaDTO crear(RespuestaDTO respuestaDTO) {
        Respuesta respuesta = convertirAEntidad(respuestaDTO);
        Respuesta nuevaRespuesta = respuestaRepository.save(respuesta);
        return convertirADTO(nuevaRespuesta);
    }

    public List<RespuestaDTO> listarTodas() {
        return respuestaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public RespuestaDTO buscarPorId(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID " + id));
        return convertirADTO(respuesta);
    }

    public RespuestaDTO actualizar(Long id, RespuestaDTO respuestaDTO) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID " + id));

        respuesta.setTopico(respuestaDTO.topico());
        respuesta.setAutor(respuestaDTO.autor());
        respuesta.setMensaje(respuestaDTO.mensaje());
        respuesta.setSolucion(respuestaDTO.solucion());
        respuesta.setFechaCreacion(respuestaDTO.fechaCreacion());

        Respuesta respuestaActualizada = respuestaRepository.save(respuesta);
        return convertirADTO(respuestaActualizada);
    }

    public void eliminar(Long id) {
        if (!respuestaRepository.existsById(id)) {
            throw new RuntimeException("Respuesta no encontrada con ID " + id);
        }
        respuestaRepository.deleteById(id);
    }

    private Respuesta convertirAEntidad(RespuestaDTO respuestaDTO) {
        Respuesta respuesta = new Respuesta();
        respuesta.setTopico(respuestaDTO.topico());
        respuesta.setAutor(respuestaDTO.autor());
        respuesta.setMensaje(respuestaDTO.mensaje());
        respuesta.setSolucion(respuestaDTO.solucion());
        respuesta.setFechaCreacion(respuestaDTO.fechaCreacion());
        return respuesta;
    }

    private RespuestaDTO convertirADTO(Respuesta respuesta) {
        return new RespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor(),
                respuesta.getSolucion()
        );
    }
}