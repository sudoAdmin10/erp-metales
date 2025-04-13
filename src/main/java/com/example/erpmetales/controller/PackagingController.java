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

    @PostMapping("/order/deliver")
    public String sendOrder(@RequestParam("id") int orderId, RedirectAttributes redirectAttributes) {
        int result = packagingDao.updateOrderStatus(orderId, "Delivered");

        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order delivered successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error to deliver order.");
        }

        return "redirect:/packaging";
    }
}
