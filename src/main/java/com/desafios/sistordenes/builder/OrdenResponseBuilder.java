package com.desafios.sistordenes.builder;

import com.desafios.sistordenes.dto.OrdenResponse;
import com.desafios.sistordenes.dto.ProductoResponse;
import com.desafios.sistordenes.model.Orden;

import java.util.List;
import java.util.stream.Collectors;

public class OrdenResponseBuilder {

    public static OrdenResponse fromEntity(Orden orden) {
        List<ProductoResponse> productos = orden.getProductos().stream()
                .map(p -> new ProductoResponse(
                        p.getNombre(),
                        p.getCantidad(),
                        p.getPrecioUnitario()))
                .collect(Collectors.toList());

        return new OrdenResponse(
                orden.getId(),
                orden.getCliente(),
                orden.getFecha(),
                orden.getTotal(),
                productos
        );
    }
}
