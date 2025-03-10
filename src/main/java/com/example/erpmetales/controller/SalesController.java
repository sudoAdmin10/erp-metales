package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;

@RestController
@RequestMapping("/customers")
public class SalesController {

    @Autowired
    private SalesDao salesDao;

    // Obtener todos los clientes
    @GetMapping
    public List<Customer> getCustomers() {
        return salesDao.getAllCustomers();
    }

    // Agregar un nuevo cliente
    @PostMapping
    public String addCustomer(@RequestBody Customer customer) {
        int result = salesDao.addCustomer(customer);
        return result == 1 ? "Customer added successfully" : "Error adding customer";
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
