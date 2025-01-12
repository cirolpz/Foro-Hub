package alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record RespuestaResponseDTO(
        Long id,
        @NotNull @NotBlank String mensaje,
        Long topicoId,
        Long autorId,
        LocalDateTime fechaCreacion,
        @NotNull Boolean solucion
) {}