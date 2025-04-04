package com.example.erpmetales.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErpmetalesController {

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    // ------------------- PACKAGING ------------------------------------
    @RequestMapping(value = { "/packaging" }, method = RequestMethod.GET)
    public ModelAndView packaging() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("package/packaging");
        return modelAndView;
    }

}
