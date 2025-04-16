package com.desafios.sistordenes.service;

import com.desafios.sistordenes.builder.OrdenResponseBuilder;
import com.desafios.sistordenes.dto.OrdenRequest;
import com.desafios.sistordenes.dto.OrdenResponse;
import com.desafios.sistordenes.model.Orden;
import com.desafios.sistordenes.model.Producto;
import com.desafios.sistordenes.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository repository;

    public OrdenResponse crear(OrdenRequest request) {
        Orden orden = new Orden();
        orden.setCliente(request.getCliente());
        orden.setFecha(request.getFecha());

        // Calcular el total automáticamente desde los productos
        double totalCalculado = request.getProductos().stream()
                .mapToDouble(p -> p.getPrecioUnitario() * p.getCantidad())
                .sum();

        orden.setTotal(totalCalculado);

        // Mapear productos a entidad
        List<Producto> productos = request.getProductos().stream().map(pr -> {
            Producto producto = new Producto();
            producto.setNombre(pr.getNombre());
            producto.setCantidad(pr.getCantidad());
            producto.setPrecioUnitario(pr.getPrecioUnitario());
            producto.setOrden(orden); // establecer relación
            return producto;
        }).toList();

        orden.setProductos(productos);

        // Guardar orden completa
        Orden guardada = repository.save(orden);

        // Usar Builder para construir la respuesta
        return OrdenResponseBuilder.fromEntity(guardada);
    }

    public Page<OrdenResponse> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(OrdenResponseBuilder::fromEntity);
    }

    public Page<OrdenResponse> buscar(String cliente, LocalDate fecha, Pageable pageable) {
        if (cliente != null) {
            return repository.findByClienteIgnoreCase(cliente, pageable)
                    .map(OrdenResponseBuilder::fromEntity);
        } else if (fecha != null) {
            return repository.findByFecha(fecha, pageable)
                    .map(OrdenResponseBuilder::fromEntity);
        }
        return listar(pageable);
    }
}
