package com.example.erpmetales.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.erpmetales.model.Users;
import com.example.erpmetales.service.UserRoleService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@Controller
public class ErpmetalesController {

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView home(Model model, Principal principal) {

        ModelAndView modelAndView = new ModelAndView();

        String userRole = userRoleService.getUserRole(principal);

        // Agregar el rol al modelo
        model.addAttribute("userRole", userRole);

        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    private String getUserRole(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .findFirst()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .orElse("ROLE_USER"); // Default role if not found
        }
        return "ROLE_USER";
    }

}
