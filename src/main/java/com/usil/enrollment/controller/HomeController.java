package com.usil.enrollment.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private com.usil.enrollment.repository.EstudianteRepository estudianteRepository;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "home/home";
    }

    @PostMapping("/seleccionar-estudiante")
    public String seleccionarEstudiante(@RequestParam Long estudianteId, HttpSession session) {
        session.setAttribute("estudianteId", estudianteId);
        return "redirect:/matricula/dashboard";
    }
}