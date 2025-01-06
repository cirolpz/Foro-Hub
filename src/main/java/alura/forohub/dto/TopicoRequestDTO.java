package alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoRequestDTO(
        @NotNull
        @NotBlank
        String title,
        @NotBlank
        @NotNull
        String message,
        @NotNull
        Long autorId,
        @NotNull
        Long cursoId) {
    public boolean existsByTituloAndMensaje(String title, String message) {
        return this.title.equals(title) && this.message.equals(message);
    }
}
