package com.example.desafio4cs.dto;

import com.example.desafio4cs.model.TipoCliente;

import java.time.LocalDate;

public class OrdenResponse {

    private Long id;
    private String cliente;
    private LocalDate fecha;
    private Double total;
    private TipoCliente tipoCliente;

    public OrdenResponse(Long id, String cliente, LocalDate fecha, Double total, TipoCliente tipoCliente) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
        this.tipoCliente = tipoCliente;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Double getTotal() {
        return total;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }
}
