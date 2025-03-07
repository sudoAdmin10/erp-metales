package com.example.erpmetales.model;

public class QualityReport extends BaseEntity {
    private OrderProduction ordenProduccion;
    private String result; // "Aprobado" o "Rechazado"
    private String remarks;// Descripcion

    // Constructors, getters, and setters

    public OrderProduction getOrdenProduccion() {
        return ordenProduccion;
    }

    public void setOrdenProduccion(OrderProduction ordenProduccion) {
        this.ordenProduccion = ordenProduccion;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
