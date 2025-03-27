package com.example.erpmetales.model;

public class OrderProduction extends BaseEntity {
    private Product product;
    private int quantity;
    private String state;

    /*
     * @Override
     * public void procesarOrden() {
     * this.state = "Completado";
     * System.out.println("Orden de producción completada.");
     * }
     * 
     * @Override
     * public void cancelarOrden() {
     * this.state = "Cancelada";
     * System.out.println("Orden de producción cancelada.");
     * }
     */

    // Constructor, Getters y Setters

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
