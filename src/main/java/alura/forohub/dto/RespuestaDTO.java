package alura.forohub.dto;

import alura.forohub.entity.Topico;
import alura.forohub.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RespuestaDTO(Long id, @NotNull @NotBlank String mensaje, @NotNull Topico topico,  LocalDateTime fechaCreacion, @NotNull Usuario autor, @NotNull Boolean solucion) {
}
