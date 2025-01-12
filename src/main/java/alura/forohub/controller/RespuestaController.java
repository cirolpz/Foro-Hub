package alura.forohub.controller;

import alura.forohub.dto.RespuestaDTO;
import alura.forohub.service.RespuestaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> crearRespuesta(@RequestBody @Valid RespuestaDTO respuestaDTO) {
        RespuestaDTO nuevaRespuesta = respuestaService.crear(respuestaDTO);
        URI location = URI.create("/respuestas/" + nuevaRespuesta.id());
        return ResponseEntity.created(location).body(nuevaRespuesta);
    }

    @GetMapping
    public ResponseEntity<List<RespuestaDTO>> listarRespuestas() {
        List<RespuestaDTO> respuestas = respuestaService.listarTodas();
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerRespuesta(@PathVariable Long id) {
        RespuestaDTO respuesta = respuestaService.buscarPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid RespuestaDTO respuestaDTO) {
        RespuestaDTO respuestaActualizada = respuestaService.actualizar(id, respuestaDTO);
        return ResponseEntity.ok(respuestaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        respuestaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
