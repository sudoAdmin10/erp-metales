package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.PackagingDao;
import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.OrderDetail;

@Controller
@RequestMapping("/packaging")
public class PackagingController {
    private final ErpmetalesApplication erpmetalesApplication;

    private PackagingDao packagingDao;
    private SalesDao salesDao;

    @Autowired
    PackagingController(ErpmetalesApplication erpmetalesApplication, PackagingDao packagingDao, SalesDao salesDao) {
        this.erpmetalesApplication = erpmetalesApplication;
        this.packagingDao = packagingDao;
        this.salesDao = salesDao;
    }

    @GetMapping("")
    public String showPackagingPage(Model model) {
        List<OrderDetail> orders = packagingDao.getAllOrdersAcepted();
        List<Customer> customers = salesDao.getAllCustomers();

        model.addAttribute("lista_ordenes_pendientes", orders);
        model.addAttribute("lista_clientes", customers);
        model.addAttribute("orden_cliente", null);

        return "package/packaging";
    }
}
