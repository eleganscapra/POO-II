package com.usil.enrollment.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
    
    @ManyToOne
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;
    
    private LocalDateTime fechaMatricula;
    
    // true = Oficialmente Matriculado, false = Pre-matriculado (Horario Temporal)
    private boolean matriculado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public Seccion getSeccion() { return seccion; }
    public void setSeccion(Seccion seccion) { this.seccion = seccion; }

    public LocalDateTime getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDateTime fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public boolean isMatriculado() { return matriculado; }
    public void setMatriculado(boolean matriculado) { this.matriculado = matriculado; }
}
