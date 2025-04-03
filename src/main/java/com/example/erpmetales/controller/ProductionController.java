package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.ProductionDao;
import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.OrderDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/production")
public class ProductionController {

    private final ErpmetalesApplication erpmetalesApplication;

    private ProductionDao productionDao;
    private SalesDao salesDao;

    @Autowired
    ProductionController(ErpmetalesApplication erpmetalesApplication, ProductionDao productionDao, SalesDao salesDao) {
        this.erpmetalesApplication = erpmetalesApplication;
        this.productionDao = productionDao;
        this.salesDao = salesDao;
    }

    // VISTAS
    // ------------------------------------------------------------------------

    @GetMapping("")
    public String showProductionPage(Model model) {
        List<OrderDetail> orders = productionDao.getAllOrdersPending();
        List<Customer> customers = salesDao.getAllCustomers();

        model.addAttribute("lista_ordenes_pendientes", orders);
        model.addAttribute("lista_clientes", customers);

        return "production/production";
    }

}
