package com.desafios.sistordenes.service;

import com.desafios.sistordenes.builder.OrdenResponseBuilder;
import com.desafios.sistordenes.dto.OrdenRequest;
import com.desafios.sistordenes.dto.OrdenResponse;
import com.desafios.sistordenes.dto.ProductoRequest;
import com.desafios.sistordenes.model.Orden;
import com.desafios.sistordenes.model.Producto;
import com.desafios.sistordenes.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository repository;

    public OrdenResponse crear(OrdenRequest request) {
        Objects.requireNonNull(request.getProductos(), "La orden debe contener al menos un producto");

        Orden orden = new Orden();
        orden.setCliente(request.getCliente());
        orden.setFecha(request.getFecha());

        double totalCalculado = request.getProductos().stream()
                .mapToDouble(p -> p.getPrecioUnitario() * p.getCantidad())
                .sum();
        orden.setTotal(totalCalculado);

        List<Producto> productos = request.getProductos().stream().map(pr -> {
            Producto producto = new Producto();
            producto.setNombre(pr.getNombre());
            producto.setCantidad(pr.getCantidad());
            producto.setPrecioUnitario(pr.getPrecioUnitario());
            producto.setOrden(orden);
            return producto;
        }).toList();

        orden.setProductos(productos);
        Orden guardada = repository.save(orden);

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

    public Orden getOrdenPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada con id: " + id));
    }

    public OrdenResponse eliminarProductoDeOrden(Long idOrden, Long idProducto) {
        Orden orden = repository.findById(idOrden)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        boolean eliminado = orden.getProductos().removeIf(p -> p.getId().equals(idProducto));
        if (!eliminado) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado en esta orden");
        }

        Orden actualizada = repository.save(orden);
        return OrdenResponseBuilder.fromEntity(actualizada);
    }

    public OrdenResponse actualizarProductoEnOrden(Long idOrden, Long idProducto, ProductoRequest request) {
        Orden orden = repository.findById(idOrden)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        Producto producto = orden.getProductos().stream()
                .filter(p -> p.getId().equals(idProducto))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado en esta orden"));

        producto.setNombre(request.getNombre());
        producto.setCantidad(request.getCantidad());
        producto.setPrecioUnitario(request.getPrecioUnitario());

        double nuevoTotal = orden.getProductos().stream()
                .mapToDouble(p -> p.getCantidad() * p.getPrecioUnitario())
                .sum();

        orden.setTotal(nuevoTotal);

        Orden actualizada = repository.save(orden);
        return OrdenResponseBuilder.fromEntity(actualizada);
    }

    public OrdenResponse actualizarProductos(Long idOrden, List<ProductoRequest> nuevosProductos) {
        Orden orden = repository.findById(idOrden)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        // Eliminar productos actuales (con orphanRemoval = true)
        orden.getProductos().clear();

        // Agregar los nuevos productos
        for (ProductoRequest p : nuevosProductos) {
            Producto producto = new Producto();
            producto.setNombre(p.getNombre());
            producto.setCantidad(p.getCantidad());
            producto.setPrecioUnitario(p.getPrecioUnitario());
            producto.setOrden(orden); // importante mantener la relación bidireccional
            orden.getProductos().add(producto);
        }

        // Recalcular total
        double nuevoTotal = orden.getProductos().stream()
                .mapToDouble(p -> p.getCantidad() * p.getPrecioUnitario())
                .sum();
        orden.setTotal(nuevoTotal);

        Orden actualizada = repository.save(orden);
        return OrdenResponseBuilder.fromEntity(actualizada);
    }
    public OrdenResponse agregarProducto(Long idOrden, ProductoRequest request) {
        Orden orden = repository.findById(idOrden)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setCantidad(request.getCantidad());
        producto.setPrecioUnitario(request.getPrecioUnitario());
        producto.setOrden(orden);

        orden.getProductos().add(producto); // agregar sin reemplazar

        // Recalcular total
        double nuevoTotal = orden.getProductos().stream()
                .mapToDouble(p -> p.getCantidad() * p.getPrecioUnitario())
                .sum();
        orden.setTotal(nuevoTotal);

        Orden actualizada = repository.save(orden);
        return OrdenResponseBuilder.fromEntity(actualizada);
    }
    public void eliminarOrden(Long idOrden) {
        Orden orden = repository.findById(idOrden)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada con id: " + idOrden));
        repository.delete(orden);
    }

}
