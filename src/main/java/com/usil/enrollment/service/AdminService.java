package com.usil.enrollment.service;

import com.usil.enrollment.build.CursoBuilder;
import com.usil.enrollment.model.Curso;
import com.usil.enrollment.model.Matricula;
import com.usil.enrollment.repository.CursoRepository;
import com.usil.enrollment.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    public Curso crearCurso(
            String codigo,
            String nombre,
            int creditos) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código del curso es obligatorio");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del curso es obligatorio");
        }

        if (creditos <= 0) {
            throw new IllegalArgumentException(
                    "Los créditos deben ser mayores a 0");
        }

        if (creditos > 6) {
            throw new IllegalArgumentException(
                    "Los créditos no pueden ser mayores a 6");
        }

        Curso curso = new Curso();

        CursoBuilder builder = new CursoBuilder(curso);

        Curso nuevoCurso = builder
                .codigo(codigo)
                .nombre(nombre)
                .creditos(creditos)
                .build();

        return cursoRepository.save(nuevoCurso);
    }

    public List<Curso> obtenerCursos() {
        return cursoRepository.findAll();
    }

    public List<Matricula> obtenerMatriculas() {
        return matriculaRepository.findAll();
    }
}