package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private final ErpmetalesApplication erpmetalesApplication;

    @Autowired
    private SalesDao salesDao;

    SalesController(ErpmetalesApplication erpmetalesApplication) {
        this.erpmetalesApplication = erpmetalesApplication;
    }

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        List<Customer> customers = salesDao.getAllCustomers();
        model.addAttribute("lista_clientes", customers);
        return "sales/customers";
    }

    @GetMapping("/suppliers")
    public String getSuppliers(Model model) {
        return "sales/suppliers";
    }

    @GetMapping("/reports")
    public String getReports(Model model) {
        List<Customer> customers = salesDao.getAllCustomers();
        model.addAttribute("lista_clientes", customers);
        return "sales/reports";
    }

    @GetMapping("/customers/new")
    public String showNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "sales/new-customer";
    }

    // Agregar un cliente
    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        int result = salesDao.addCustomer(customer);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Cliente agregado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al agregar el cliente.");
        }
        return "redirect:/sales/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable int id, Model model) {
        Customer customer = salesDao.getCustomerById(id);
        if (customer == null) {
            return "redirect:/sales/customers";
        }
        model.addAttribute("customer", customer);
        return "sales/edit-customer";
    }

    // Actualizar un cliente
    @PostMapping("/customers/edit/{id}")
    public String updateCustomer(@PathVariable int id, @ModelAttribute Customer customer,
            RedirectAttributes redirectAttributes) {
        customer.setId(id);
        int result = salesDao.updateCustomer(customer);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Cliente actualizado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el cliente.");
        }
        return "redirect:/sales/customers";
    }

    // Eliminar un cliente
    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        int result = salesDao.deleteCustomer(id);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Cliente eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el cliente.");
        }
        return "redirect:/sales/customers";
    }

}
