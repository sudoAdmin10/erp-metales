package com.example.erpmetales.controller;

import com.example.erpmetales.model.Users;
import com.example.erpmetales.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        System.out.println("-------0--------");

        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {

        if (error != null) {
            System.out.println("---------------------------1----------------------------");
            model.addAttribute("error", "Email o contraseña incorrectos");
        }
        if (logout != null) {
            System.out.println("-----------------------------2--------------------------");

            model.addAttribute("message", "Sesión cerrada correctamente");
        }
        if (registered != null) {
            System.out.println("--------------------------------3-----------------------");

            model.addAttribute("message", "Registro exitoso. Por favor inicie sesión");
        }

        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(Users user, Model model) {
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "auth/register";
        }
        user.setRole("USER"); // Default role
        userService.registerUser(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
}
