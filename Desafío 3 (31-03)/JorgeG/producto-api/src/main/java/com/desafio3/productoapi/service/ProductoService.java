package com.desafio3.productoapi.service;

import com.desafio3.productoapi.dto.ProductoRequest;
import com.desafio3.productoapi.dto.ProductoResponse;
import com.desafio3.productoapi.entity.Producto;
import com.desafio3.productoapi.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear un producto
    public ProductoResponse crearProducto(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setCategoria(request.getCategoria());

        Producto productoGuardado = productoRepository.save(producto);
        return new ProductoResponse(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getCategoria()
        );
    }

    // Listar todos los productos
    public List<ProductoResponse> listarProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getCategoria()
                ))
                .collect(Collectors.toList());
    }

    // Buscar productos por nombre o categoría
    public List<ProductoResponse> buscarProductos(String nombre, String categoria) {
        if (nombre != null) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                    .map(producto -> new ProductoResponse(
                            producto.getId(),
                            producto.getNombre(),
                            producto.getCategoria()
                    ))
                    .collect(Collectors.toList());
        } else if (categoria != null) {
            return productoRepository.findByCategoria(categoria).stream()
                    .map(producto -> new ProductoResponse(
                            producto.getId(),
                            producto.getNombre(),
                            producto.getCategoria()
                    ))
                    .collect(Collectors.toList());
        } else {
            return listarProductos();
        }
    }
}