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

    @RequestMapping(value = { "/sales" }, method = RequestMethod.GET)
    public ModelAndView sales() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("sales");
        return modelAndView;
    }

    @RequestMapping(value = { "/production" }, method = RequestMethod.GET)
    public ModelAndView production() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("production");
        return modelAndView;
    }

    @RequestMapping(value = { "/quality" }, method = RequestMethod.GET)
    public ModelAndView quality() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("quality");
        return modelAndView;
    }

    @RequestMapping(value = { "/packaging" }, method = RequestMethod.GET)
    public ModelAndView packaging() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("packaging");
        return modelAndView;
    }

}
