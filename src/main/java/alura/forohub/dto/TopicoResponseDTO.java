package alura.forohub.dto;


import java.time.LocalDateTime;

public record TopicoResponseDTO(Long id, String title, String message, LocalDateTime fechaCreacion, String status, UsuarioResponseDTO autor, CursoResponseDTO curso) {

}
