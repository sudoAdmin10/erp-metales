package com.example.erpmetales.model;

import java.util.List;

public class CustomerOrder extends BaseEntity implements Order {
    private Customer customer;
    private List<Product> products;
    private double total;
    private String state;

    @Override
    public void procesarOrden() {
        this.state = "Pagado";
        System.out.println("Pedido procesado correctamente.");
    }

    @Override
    public void cancelarOrden() {
        this.state = "Cancelado";
        System.out.println("Pedido cancelado.");
    }

    // Getters and setters

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
