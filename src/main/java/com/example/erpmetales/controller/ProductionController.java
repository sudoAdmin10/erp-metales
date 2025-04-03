package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.ProductionDao;
import com.example.erpmetales.model.OrderDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/production")
public class ProductionController {

    private final ErpmetalesApplication erpmetalesApplication;

    @Autowired
    private ProductionDao productionDao;

    ProductionController(ErpmetalesApplication erpmetalesApplication) {
        this.erpmetalesApplication = erpmetalesApplication;
    }

    // VISTAS
    // ------------------------------------------------------------------------

    @GetMapping("")
    public String showProductionPage(Model model) {
        List<OrderDetail> orders = productionDao.getAllOrdersPending();

        model.addAttribute("lista_ordenes_pendientes", orders);

        return "production/production";
    }

}
