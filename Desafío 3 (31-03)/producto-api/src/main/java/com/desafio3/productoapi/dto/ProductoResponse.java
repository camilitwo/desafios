package com.desafio3.productoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String categoria;
}