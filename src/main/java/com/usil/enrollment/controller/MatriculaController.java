package com.usil.enrollment.controller;
import com.usil.enrollment.model.Curso;
import com.usil.enrollment.model.Estudiante;
import com.usil.enrollment.model.Docente;
import com.usil.enrollment.repository.MatriculaRepository;
import com.usil.enrollment.repository.EstudianteRepository;
import com.usil.enrollment.service.CursoService;
import com.usil.enrollment.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/matricula")
public class MatriculaController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private MatriculaRepository matriculaRepository;

    // el prototipo simula que haya un estudiante loggeado, no se creo un login porque no estaba dentro
    // de nuestros abjetivos, aun asi en la base de datos se implementaron dos usuarios
    private Estudiante getLoggedInEstudiante() {
        return estudianteRepository.findAll().stream().findFirst() // busca en la tabla de estudiantes
                .orElseThrow(() -> new RuntimeException("No hay estudiantes registrados en la base de datos."));
    }       // si no hubiese ninguno, muestra el error por falta de tiempo de respuesta

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Estudiante estudiante = getLoggedInEstudiante();
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("matriculasOficiales", matriculaRepository.findByEstudianteIdAndMatriculado(estudiante.getId(), true));
        model.addAttribute("preMatriculas", matriculaRepository.findByEstudianteIdAndMatriculado(estudiante.getId(), false));
        return "matricula/dashboard";
    }

    @GetMapping("/cursos")
    public String selectCourse(Model model) {
        Estudiante estudiante = getLoggedInEstudiante();
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("cursosDisponibles", cursoService.obtenerCursosDisponibles(estudiante));
        return "matricula/course-selection";
    }

    @GetMapping("/docentes")
    public String selectTeacher(@RequestParam Long cursoId, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(cursoId);
        model.addAttribute("curso", curso);
        model.addAttribute("docentes", cursoService.obtenerDocentesPorCurso(cursoId));
        return "matricula/teacher-selection";
    }

    @GetMapping("/horarios")
    public String selectSchedule(@RequestParam Long cursoId, @RequestParam Long docenteId, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(cursoId);
        Docente docente = cursoService.obtenerDocentePorId(docenteId);
        model.addAttribute("curso", curso);
        model.addAttribute("docente", docente);
        model.addAttribute("secciones", cursoService.obtenerSeccionesPorCursoYDocente(cursoId, docenteId));
        return "matricula/schedule-selection";
    }

    @PostMapping("/agregar")
    public String confirmEnrollment(@RequestParam Long seccionId, RedirectAttributes redirectAttributes) {
        Estudiante estudiante = getLoggedInEstudiante();
        try {
            matriculaService.preMatricularEstudiante(estudiante.getId(), seccionId);
            redirectAttributes.addFlashAttribute("successMessage", "Curso agregado al horario temporal con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al agregar curso: " + e.getMessage());
        }
        return "redirect:/matricula/dashboard";
    }
    
    @PostMapping("/eliminar")
    public String deleteEnrollment(@RequestParam Long matriculaId, RedirectAttributes redirectAttributes) {
        Estudiante estudiante = getLoggedInEstudiante();
        try {
            matriculaService.eliminarMatriculaTemporal(matriculaId, estudiante.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Curso eliminado del horario temporal.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar curso: " + e.getMessage());
        }
        return "redirect:/matricula/dashboard";
    }
    
    @PostMapping("/emitir")
    public String finalizeEnrollments(RedirectAttributes redirectAttributes) {
        Estudiante estudiante = getLoggedInEstudiante();
        matriculaService.emitirMatriculas(estudiante.getId());
        redirectAttributes.addFlashAttribute("successMessage", "¡Matrícula confirmada y emitida exitosamente!");
        return "redirect:/matricula/dashboard";
    }

    @PostMapping("/retiro-total")
    public String withdrawTotal(RedirectAttributes redirectAttributes) {
        Estudiante estudiante = getLoggedInEstudiante();
        try {
            matriculaService.eliminarMatriculaCompleta(estudiante.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Se ha cancelado y eliminado exitosamente toda su matrícula del ciclo. Sus vacantes han sido liberadas.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al procesar el retiro total: " + e.getMessage());
        }
        return "redirect:/matricula/dashboard";
    }
}
