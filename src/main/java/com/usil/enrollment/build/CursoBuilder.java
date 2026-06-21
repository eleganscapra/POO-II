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

    public Curso build(){
        return curso;
    }
}