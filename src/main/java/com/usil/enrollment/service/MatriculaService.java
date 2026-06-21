package com.usil.enrollment.service;
import com.usil.enrollment.model.Curso;
import com.usil.enrollment.model.Matricula;
import com.usil.enrollment.model.Seccion;
import com.usil.enrollment.model.Estudiante;
import com.usil.enrollment.repository.MatriculaRepository;
import com.usil.enrollment.repository.SeccionRepository;
import com.usil.enrollment.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Verifica si el estudiante cumple todos los prerrequisitos para un curso dado.

    public boolean verificarPrerrequisitos(Estudiante estudiante, Curso curso) {
        if (curso.getPrerrequisitos() == null || curso.getPrerrequisitos().isEmpty()) {
            return true;
        }
        List<Curso> cursosAprobados = estudiante.getCursosAprobados();
        for (Curso prerrequisito : curso.getPrerrequisitos()) {
            if (!cursosAprobados.contains(prerrequisito)) {
                return false;
            }
        }
        return true;
    }

    // verifica si hay conflictos de horarios

    public boolean verificarCruceHorarios(Estudiante estudiante, Seccion nuevaSeccion) {
        List<Matricula> oficiales = matriculaRepository.findByEstudianteIdAndMatriculado(estudiante.getId(), true);
        List<Matricula> temporales = matriculaRepository.findByEstudianteIdAndMatriculado(estudiante.getId(), false);
        
        List<Matricula> todasActivas = Stream.concat(oficiales.stream(), temporales.stream())
                                           .collect(Collectors.toList());
                                           
        for (Matricula matricula : todasActivas) {
            Seccion seccionMatriculada = matricula.getSeccion();
            if (seccionMatriculada.getDiaSemana().equals(nuevaSeccion.getDiaSemana())) {
                if (nuevaSeccion.getHoraInicio().isBefore(seccionMatriculada.getHoraFin()) && 
                    nuevaSeccion.getHoraFin().isAfter(seccionMatriculada.getHoraInicio())) {
                    return true; // hay conflicto
                }
            }
        }
        return false;
    }

    // pre-matricula a un estudiante en una seccion. Lo agrega al horario temporal (matriculado = false).

    @Transactional
    public Matricula preMatricularEstudiante(Long estudianteId, Long seccionId) throws Exception {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new Exception("Estudiante no encontrado"));
        Seccion seccion = seccionRepository.findById(seccionId)
                .orElseThrow(() -> new Exception("Sección no encontrada"));

        if (seccion.isLleno()) {
            throw new Exception("La sección ha alcanzado su máxima capacidad");
        }

        if (!verificarPrerrequisitos(estudiante, seccion.getCurso())) {
            throw new Exception("El estudiante no cumple los prerrequisitos para este curso");
        }

        if (verificarCruceHorarios(estudiante, seccion)) {
            throw new Exception("Cruce de horarios con otro curso ya matriculado o agregado");
        }
        
        // revisa que el estudiante no este pre-matriculado o matriculado en el curso
        List<Matricula> oficiales = matriculaRepository.findByEstudianteIdAndMatriculado(estudianteId, true);
        List<Matricula> temporales = matriculaRepository.findByEstudianteIdAndMatriculado(estudianteId, false);
        List<Matricula> todasActivas = Stream.concat(oficiales.stream(), temporales.stream()).collect(Collectors.toList());
        
        for(Matricula m : todasActivas) {
            if(m.getSeccion().getCurso().getId().equals(seccion.getCurso().getId())) {
                throw new Exception("Ya estás matriculado o tienes pre-matriculado este curso");
            }
        }

        // proceso de pre-matricula
        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setSeccion(seccion);
        matricula.setFechaMatricula(LocalDateTime.now());
        matricula.setMatriculado(false);

        seccion.setInscritos(seccion.getInscritos() + 1);
        seccionRepository.save(seccion);

        return matriculaRepository.save(matricula);
    }
    
    @Transactional
    public void eliminarMatriculaTemporal(Long matriculaId, Long estudianteId) throws Exception {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new Exception("Matrícula no encontrada"));
                
        if (!matricula.getEstudiante().getId().equals(estudianteId)) {
            throw new Exception("No tienes permiso para eliminar esta matrícula");
        }
        
        if (matricula.isMatriculado()) {
            throw new Exception("Solo se pueden eliminar cursos que estén en estado de pre-matrícula");
        }
        
        // reducir cantidad de inscritos
        Seccion seccion = matricula.getSeccion();
        seccion.setInscritos(seccion.getInscritos() - 1);
        seccionRepository.save(seccion);
        
        matriculaRepository.delete(matricula);
    }
    
    @Transactional
    public void emitirMatriculas(Long estudianteId) {
        List<Matricula> preMatriculados = matriculaRepository.findByEstudianteIdAndMatriculado(estudianteId, false);
        for (Matricula matricula : preMatriculados) {
            matricula.setMatriculado(true);
            matriculaRepository.save(matricula);
        }
    }

    @Transactional
    // en caso deba eliminar la matrícula
    public void eliminarMatriculaCompleta(Long estudianteId) {
        List<Matricula> todasLasMatriculas = matriculaRepository.findByEstudianteId(estudianteId);
        
        for (Matricula matricula : todasLasMatriculas) {
            // reduce cantidad de inscritos
            Seccion seccion = matricula.getSeccion();
            seccion.setInscritos(seccion.getInscritos() - 1);
            seccionRepository.save(seccion);
            
            // elminar matriucla
            matriculaRepository.delete(matricula);
        }
    }
}
