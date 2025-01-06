package alura.forohub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "topico")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255, name = "titulo")
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false, name = "mensaje")
    private String message;

    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "status",nullable = false,columnDefinition = "TEXT")
    private String status;

    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Usuario autor;
    @ManyToOne
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    private Curso curso;

    // Constructor vacío
    public Topico() {
    }

    // Constructor con argumentos
    public Topico(Long id, String title, String message, LocalDateTime fechaCreacion, String status, Usuario autor, Curso curso) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
