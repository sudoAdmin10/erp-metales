package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.erpmetales.ErpmetalesApplication;

@Controller
@RequestMapping("/access-denied")
public class CustomErrorController {

    @RequestMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
