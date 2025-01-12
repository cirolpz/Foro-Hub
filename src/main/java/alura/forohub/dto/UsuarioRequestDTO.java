package alura.forohub.dto;

import jakarta.validation.constraints.*;

public record UsuarioRequestDTO(
        @NotNull
        @NotBlank
        String nombre,
        @NotNull
        @NotBlank
                @Email
        String email,
        @NotNull
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&_]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número.")
        String contrasena) {
}
