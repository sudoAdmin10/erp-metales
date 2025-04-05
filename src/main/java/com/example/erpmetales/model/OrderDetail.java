package com.example.erpmetales.model;

import java.time.LocalDateTime;

public class OrderDetail {
    private int id;
    private String customerName; // Nombre del cliente
    private String productName; // Nombre del producto
    private int customerId; // Para operaciones CRUD
    private int productId; // Para operaciones CRUD
    private LocalDateTime orderDate;
    private int amount;
    private double total;
    private String status;
    private String description; // Para ordenes rechazadas
    private String defective_parts; // Para ordenes rechazadas

    public double getTotal() {
        return total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefective_parts() {
        return defective_parts;
    }

    public void setDefective_parts(String defective_parts) {
        this.defective_parts = defective_parts;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
