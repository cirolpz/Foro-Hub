package alura.forohub.dto;


import alura.forohub.entity.Topico;

import java.time.LocalDateTime;
import java.util.List;

public record TopicoResponseDTO(Long id, String title, String message, LocalDateTime fechaCreacion, String status, UsuarioDTO autor, CursoDTO curso) {

}
