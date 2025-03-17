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

import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesDao salesDao;

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        List<Customer> customers = salesDao.getAllCustomers();
        model.addAttribute("lista_clientes", customers);
        return "sales/customers";
    }

    @GetMapping("/customers/new")
    public String showNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "sales/new-customer";
    }

    // Agregar un cliente
    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        salesDao.addCustomer(customer);
        return "redirect:/sales/customers"; // Redirigir a la lista de clientes
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        customer.setId(id);
        int result = salesDao.updateCustomer(customer);
        return result == 1 ? "Customer updated successfully" : "Error updating customer";
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        int result = salesDao.deleteCustomer(id);
        return result == 1 ? "Customer deleted successfully" : "Error deleting customer";
    }
}
