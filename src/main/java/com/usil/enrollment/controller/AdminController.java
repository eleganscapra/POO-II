package com.usil.enrollment.controller;
import com.usil.enrollment.model.Curso;
import com.usil.enrollment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("curso", new Curso());
        model.addAttribute("cursos", adminService.obtenerCursos());
        model.addAttribute("docentes", adminService.obtenerDocentes());
        model.addAttribute("secciones", adminService.obtenerSecciones());
        return "admin/admin-dashboard";
    }

    @PostMapping("/guardar")
    public String guardarCurso(
            @RequestParam String codigo,
            @RequestParam String nombre,
            @RequestParam int creditos,
            @RequestParam(required = false) java.util.List<Long> prerrequisitosIds,
            @RequestParam(required = false) java.util.List<Long> docenteIds,
            Model model) {

        adminService.crearCurso(codigo, nombre, creditos, prerrequisitosIds, docenteIds);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/editar")
    public String editarCurso(
            @RequestParam Long id,
            @RequestParam String codigo,
            @RequestParam String nombre,
            @RequestParam int creditos,
            @RequestParam(required = false) java.util.List<Long> prerrequisitosIds,
            @RequestParam(required = false) java.util.List<Long> docenteIds,
            Model model) {

        adminService.editarCurso(id, codigo, nombre, creditos, prerrequisitosIds, docenteIds);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/eliminar")
    public String eliminarCurso(@RequestParam Long id) {
        adminService.eliminarCurso(id);
        return "redirect:/admin/dashboard";
    }

    // Docentes
    @PostMapping("/docente/guardar")
    public String guardarDocente(@RequestParam String nombre, @RequestParam String departamento) {
        adminService.crearDocente(nombre, departamento);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/docente/editar")
    public String editarDocente(@RequestParam Long id, @RequestParam String nombre, @RequestParam String departamento) {
        adminService.editarDocente(id, nombre, departamento);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/docente/eliminar")
    public String eliminarDocente(@RequestParam Long id) {
        adminService.eliminarDocente(id);
        return "redirect:/admin/dashboard";
    }

    // Secciones
    @PostMapping("/seccion/guardar")
    public String guardarSeccion(
            @RequestParam Long cursoId,
            @RequestParam Long docenteId,
            @RequestParam String codigoBloque,
            @RequestParam String diaSemana,
            @RequestParam String horaInicio,
            @RequestParam String horaFin,
            @RequestParam int capacidad) {
        adminService.crearSeccion(cursoId, docenteId, codigoBloque, diaSemana, java.time.LocalTime.parse(horaInicio), java.time.LocalTime.parse(horaFin), capacidad);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/seccion/editar")
    public String editarSeccion(
            @RequestParam Long id,
            @RequestParam Long cursoId,
            @RequestParam Long docenteId,
            @RequestParam String codigoBloque,
            @RequestParam String diaSemana,
            @RequestParam String horaInicio,
            @RequestParam String horaFin,
            @RequestParam int capacidad) {
        adminService.editarSeccion(id, cursoId, docenteId, codigoBloque, diaSemana, java.time.LocalTime.parse(horaInicio), java.time.LocalTime.parse(horaFin), capacidad);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/seccion/eliminar")
    public String eliminarSeccion(@RequestParam Long id) {
        adminService.eliminarSeccion(id);
        return "redirect:/admin/dashboard";
    }
}