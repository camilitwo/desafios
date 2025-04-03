package com.desafio.desafio2.dto;

import lombok.Data;

@Data
public class VentaRequest {
    private String tipoVendedor; // "JUNIOR", "SENIOR", "FREELANCE"
    private double monto;
    private Long vendedorId; // ID del vendedor


}