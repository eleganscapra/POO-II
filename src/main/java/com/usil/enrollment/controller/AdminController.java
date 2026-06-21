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
        return "admin/admin-dashboard";
    }

    @PostMapping("/guardar")
    public String guardarCurso(
            @RequestParam String codigo,
            @RequestParam String nombre,
            @RequestParam int creditos,
            Model model) {

        adminService.crearCurso(codigo, nombre, creditos);

        model.addAttribute("mensaje", "Curso registrado correctamente");
        return "admin/confirmacion";
    }
}