package alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioResponseDTO(Long id,
                                 @NotNull
                                 @NotBlank
                                String nombre,
                                 @NotNull
                                 @NotBlank
                                 String email) {
}
