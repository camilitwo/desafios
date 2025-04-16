package com.desafios.sistordenes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenResponse {
    private Long id;
    private String cliente;
    private LocalDate fecha;
    private Double total;

    private List<ProductoResponse> productos;
}
