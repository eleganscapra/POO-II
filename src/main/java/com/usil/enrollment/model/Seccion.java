package com.usil.enrollment.model;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Seccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    
    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;
    
    private String codigoBloque;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int capacidad;
    private int inscritos = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public Docente getDocente() { return docente; }
    public void setDocente(Docente docente) { this.docente = docente; }

    public String getCodigoBloque() { return codigoBloque; }
    public void setCodigoBloque(String codigoBloque) { this.codigoBloque = codigoBloque; }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public int getInscritos() { return inscritos; }
    public void setInscritos(int inscritos) { this.inscritos = inscritos; }
    
    public boolean isLleno() {
        return inscritos >= capacidad;
    }
}
