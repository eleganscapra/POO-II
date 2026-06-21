package com.usil.enrollment.service;
import com.usil.enrollment.model.Curso;
import com.usil.enrollment.model.Docente;
import com.usil.enrollment.model.Estudiante;
import com.usil.enrollment.model.Seccion;
import com.usil.enrollment.repository.CursoRepository;
import com.usil.enrollment.repository.SeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private MatriculaService matriculaService;

    // Obtiene los cursos que un estudiante es elegible para tomar (cumple prerrequisitos).
    public List<Curso> obtenerCursosDisponibles(Estudiante estudiante) {
        List<Curso> todosLosCursos = cursoRepository.findAll();
        return todosLosCursos.stream()
                .filter(curso -> matriculaService.verificarPrerrequisitos(estudiante, curso))
                .collect(Collectors.toList());
    }

    public List<Docente> obtenerDocentesPorCurso(Long cursoId) {
        List<Seccion> secciones = seccionRepository.findByCursoId(cursoId);
        return secciones.stream()
                .map(Seccion::getDocente)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Seccion> obtenerSeccionesPorCursoYDocente(Long cursoId, Long docenteId) {
        return seccionRepository.findByCursoIdAndDocenteId(cursoId, docenteId);
    }
    
    public Curso obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }
    
    public Docente obtenerDocentePorId(Long id) {
        return seccionRepository.findAll().stream()
                .map(Seccion::getDocente)
                .filter(t -> t.getId().equals(id))
                .findFirst().orElse(null);
    }
}
