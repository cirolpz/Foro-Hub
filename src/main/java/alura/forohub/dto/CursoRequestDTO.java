package alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRequestDTO(@NotNull
                              @NotBlank
                              String nombre,
                              @NotNull
                              @NotBlank
                              String categoria) {
}
