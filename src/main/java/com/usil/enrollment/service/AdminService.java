package com.usil.enrollment.service;
import com.usil.enrollment.build.CursoBuilder;
import com.usil.enrollment.model.Curso;
import com.usil.enrollment.model.Docente;
import com.usil.enrollment.model.Seccion;
import com.usil.enrollment.repository.CursoRepository;
import com.usil.enrollment.repository.DocenteRepository;
import com.usil.enrollment.repository.SeccionRepository;
import com.usil.enrollment.repository.EstudianteRepository;
import com.usil.enrollment.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    public Curso crearCurso(
            String codigo,
            String nombre,
            int creditos,
            List<Long> prerrequisitosIds,
            List<Long> docenteIds) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del curso es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        if (creditos <= 0 || creditos > 6) {
            throw new IllegalArgumentException("Los créditos deben estar entre 1 y 6");
        }

        Curso curso = new Curso();
        CursoBuilder builder = new CursoBuilder(curso);

        builder.codigo(codigo)
                .nombre(nombre)
                .creditos(creditos);

        if (prerrequisitosIds != null && !prerrequisitosIds.isEmpty()) {
            List<Curso> prerrequisitos = cursoRepository.findAllById(prerrequisitosIds);
            builder.prerrequisitos(prerrequisitos);
        }
        if (docenteIds != null && !docenteIds.isEmpty()) {
            List<Docente> docentes = docenteRepository.findAllById(docenteIds);
            builder.docentes(docentes);
        }
        return cursoRepository.save(builder.build());
    }

    public Curso editarCurso(Long id, String codigo, String nombre, int creditos, List<Long> prerrequisitosIds, List<Long> docenteIds) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del curso es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        if (creditos <= 0 || creditos > 6) {
            throw new IllegalArgumentException("Los créditos deben estar entre 1 y 6");
        }

        curso.setCodigo(codigo);
        curso.setNombre(nombre);
        curso.setCreditos(creditos);

        if (prerrequisitosIds != null && !prerrequisitosIds.isEmpty()) {
            List<Curso> prerrequisitos = cursoRepository.findAllById(prerrequisitosIds);
            curso.setPrerrequisitos(prerrequisitos);
        } else {
            curso.setPrerrequisitos(new java.util.ArrayList<>());
        }

        if (docenteIds != null && !docenteIds.isEmpty()) {
            List<Docente> docentes = docenteRepository.findAllById(docenteIds);
            curso.setDocentes(docentes);
        } else {
            curso.setDocentes(new java.util.ArrayList<>());
        }

        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public List<Curso> obtenerCursos() {
        return cursoRepository.findAll();
    }

    // Docentes
    public Docente crearDocente(String nombre, String departamento) {
        Docente docente = new Docente();
        docente.setNombre(nombre);
        docente.setDepartamento(departamento);
        return docenteRepository.save(docente);
    }

    public Docente editarDocente(Long id, String nombre, String departamento) {
        Docente docente = docenteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));
        docente.setNombre(nombre);
        docente.setDepartamento(departamento);
        return docenteRepository.save(docente);
    }

    public void eliminarDocente(Long id) {
        docenteRepository.deleteById(id);
    }

    public List<Docente> obtenerDocentes() {
        return docenteRepository.findAll();
    }

    // Secciones
    public Seccion crearSeccion(Long cursoId, Long docenteId, String codigoBloque, String diaSemana, LocalTime horaInicio, LocalTime horaFin, int capacidad) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        Docente docente = docenteRepository.findById(docenteId).orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));

        validarCruceHorario(docenteId, diaSemana, horaInicio, horaFin, null);

        Seccion seccion = new Seccion();
        seccion.setCurso(curso);
        seccion.setDocente(docente);
        seccion.setCodigoBloque(codigoBloque);
        seccion.setDiaSemana(diaSemana);
        seccion.setHoraInicio(horaInicio);
        seccion.setHoraFin(horaFin);
        seccion.setCapacidad(capacidad);
        return seccionRepository.save(seccion);
    }

    public Seccion editarSeccion(Long id, Long cursoId, Long docenteId, String codigoBloque, String diaSemana, LocalTime horaInicio, LocalTime horaFin, int capacidad) {
        Seccion seccion = seccionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Seccion no encontrada"));
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        Docente docente = docenteRepository.findById(docenteId).orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));

        validarCruceHorario(docenteId, diaSemana, horaInicio, horaFin, id);

        seccion.setCurso(curso);
        seccion.setDocente(docente);
        seccion.setCodigoBloque(codigoBloque);
        seccion.setDiaSemana(diaSemana);
        seccion.setHoraInicio(horaInicio);
        seccion.setHoraFin(horaFin);
        seccion.setCapacidad(capacidad);
        return seccionRepository.save(seccion);
    }

    public void eliminarSeccion(Long id) {
        seccionRepository.deleteById(id);
    }

    public List<Seccion> obtenerSecciones() {
        return seccionRepository.findAll();
    }

    private void validarCruceHorario(Long docenteId, String diaSemana, LocalTime horaInicio, LocalTime horaFin, Long seccionIdExcluida) {
        List<Seccion> seccionesDocente = seccionRepository.findByDocenteIdAndDiaSemana(docenteId, diaSemana);
        for (Seccion s : seccionesDocente) {
            if (seccionIdExcluida != null && s.getId().equals(seccionIdExcluida)) {
                continue;
            }
            if (horaInicio.isBefore(s.getHoraFin()) && horaFin.isAfter(s.getHoraInicio())) {
                throw new IllegalArgumentException("El docente tiene un cruce de horario en este bloque: " + s.getCodigoBloque() + " (" + s.getHoraInicio() + " - " + s.getHoraFin() + ")");
            }
        }
    }
}