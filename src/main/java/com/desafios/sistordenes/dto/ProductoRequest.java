package com.desafios.sistordenes.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class ProductoRequest {

    @NotBlank
    private String nombre;

    @Min(1)
    private Integer cantidad;

    @Min(1)
    private Double precioUnitario;
}
