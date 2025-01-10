package alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotNull
        @NotBlank
        String nombre,
        @NotNull
        @NotBlank
        String email,
        @NotNull
        @NotBlank
        String contrasena) {}

