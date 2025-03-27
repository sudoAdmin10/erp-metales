package com.example.erpmetales.model;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int customerId;
    private int productId;
    private int amount;
    private LocalDateTime orderDate;
    private String status; // "PENDIENTE", "PROCESADA", "CANCELADA"

    // Constructor vacío
    public Order() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Constructor con parámetros
    public Order(int id, int customerId, int productId, int amount, LocalDateTime orderDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.amount = amount;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Métodos para procesar y cancelar órdenes
    public void procesarOrden() {
        this.status = "PROCESADA";
    }

    public void cancelarOrden() {
        this.status = "CANCELADA";
    }
}
