package alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoResponseDTO(Long id,
                               @NotNull
                               @NotBlank
                               String nombre,
                               @NotNull
                               @NotBlank
                               String categoria) {}
