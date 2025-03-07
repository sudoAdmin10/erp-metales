package com.example.erpmetales.model;

public class Send {
    private Order order;
    private String address;
    private String state; // "En tránsito", "Entregado"

    // Constructor, Getters y Setters

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
