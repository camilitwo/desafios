package com.desafios.sistordenes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoResponse {

    private String nombre;
    private Integer cantidad;
    private Double precioUnitario;
}
