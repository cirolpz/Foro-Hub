package alura.forohub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuesta")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "topico_id", referencedColumnName = "id", nullable = false)
    private Topico topico;

    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id", nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    private Boolean solucion = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }

    public Respuesta(Long id, String mensaje, Topico topico, LocalDateTime fechaCreacion, Usuario autor, Boolean solucion) {
        this.id = id;
        this.mensaje = mensaje;
        this.topico = topico;
        this.fechaCreacion = fechaCreacion;
        this.autor = autor;
        this.solucion = solucion;
    }

    public Respuesta() {
    }
}

