package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.QualityDao;
import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.OrderDetail;

@Controller
@RequestMapping("/quality")
public class QualityController {
    private final ErpmetalesApplication erpmetalesApplication;

    private QualityDao qualityDao;
    private SalesDao salesDao;

    @Autowired
    QualityController(ErpmetalesApplication erpmetalesApplication, QualityDao qualityDao, SalesDao salesDao) {
        this.erpmetalesApplication = erpmetalesApplication;
        this.qualityDao = qualityDao;
        this.salesDao = salesDao;
    }

    // VISTAS
    // ------------------------------------------------------------------------
    @GetMapping("")
    public String showqualityPage(Model model) {
        List<OrderDetail> orders = qualityDao.getAllOrdersPending();
        List<Customer> customers = salesDao.getAllCustomers();

        model.addAttribute("lista_ordenes_pendientes", orders);
        model.addAttribute("lista_clientes", customers);

        return "quality/quality";
    }

}
