package com.usil.enrollment.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String codigoAlumno;
    private String carrera;
    
    @ManyToMany
    @JoinTable(
        name = "estudiante_cursos_aprobados",
        joinColumns = @JoinColumn(name = "estudiante_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursosAprobados = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoAlumno() { return codigoAlumno; }
    public void setCodigoAlumno(String codigoAlumno) { this.codigoAlumno = codigoAlumno; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public List<Curso> getCursosAprobados() { return cursosAprobados; }
    public void setCursosAprobados(List<Curso> cursosAprobados) { this.cursosAprobados = cursosAprobados; }
}
