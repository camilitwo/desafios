package com.desafio3.productoapi.dto;
import lombok.Builder;


import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class ProductoRequest {
    private String nombre;
    private Double precio;
    private String categoria;
}