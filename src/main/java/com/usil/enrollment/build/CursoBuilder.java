package com.usil.enrollment.build;
import com.usil.enrollment.model.Curso;

public class CursoBuilder {

    private Curso curso;

    public CursoBuilder(Curso curso) {
        this.curso = curso;
    }

    public CursoBuilder codigo(String codigo){
        curso.setCodigo(codigo);
        return this;
    }

    public CursoBuilder nombre(String nombre){
        curso.setNombre(nombre);
        return this;
    }

    public CursoBuilder creditos(int creditos){
        curso.setCreditos(creditos);
        return this;
    }

    public CursoBuilder prerrequisitos(java.util.List<Curso> prerrequisitos) {
        curso.setPrerrequisitos(prerrequisitos);
        return this;
    }

    public CursoBuilder docentes(java.util.List<com.usil.enrollment.model.Docente> docentes) {
        curso.setDocentes(docentes);
        return this;
    }

    public Curso build(){
        return curso;
    }
}