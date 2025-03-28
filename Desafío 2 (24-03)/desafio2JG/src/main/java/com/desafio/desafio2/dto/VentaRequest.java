package com.desafio.desafio2.dto;

public class VentaRequest {
    private String tipoVendedor; // "JUNIOR", "SENIOR", "FREELANCE"
    private double montoVendido;

    // Getters y Setters
    public String getTipoVendedor() {
        return tipoVendedor;
    }

    public void setTipoVendedor(String tipoVendedor) {
        this.tipoVendedor = tipoVendedor;
    }

    public double getMontoVendido() {
        return montoVendido;
    }

    public void setMontoVendido(double montoVendido) {
        this.montoVendido = montoVendido;
    }
}