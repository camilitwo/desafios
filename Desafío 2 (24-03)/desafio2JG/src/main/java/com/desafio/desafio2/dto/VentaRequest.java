package com.desafio.desafio2.dto;

public class VentaRequest {
    private String tipoVendedor; // "JUNIOR", "SENIOR", "FREELANCE"
    private double monto;
    private Long vendedorId; // ID del vendedor


    // Getters y Setters
    public String getTipoVendedor() {
        return tipoVendedor;
    }

    public void setTipoVendedor(String tipoVendedor) {
        this.tipoVendedor = tipoVendedor;
    }

    public double getMontoVendido() {
        return monto;
    }

    public void setMontoVendido(double monto) {
        this.monto = monto;
    }
    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }


}