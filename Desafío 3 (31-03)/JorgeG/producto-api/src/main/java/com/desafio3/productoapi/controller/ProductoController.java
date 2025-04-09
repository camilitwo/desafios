package com.desafio3.productoapi.controller;

import com.desafio3.productoapi.dto.ProductoRequest;
import com.desafio3.productoapi.dto.ProductoResponse;
import com.desafio3.productoapi.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(
            @Valid @RequestBody ProductoRequest request) {  // ¡@Valid aquí!
        return ResponseEntity.ok(productoService.crearProducto(request));
    }

    @GetMapping
    public List<ProductoResponse> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/buscar")
    public List<ProductoResponse> buscarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria
    ) {
        return productoService.buscarProductos(nombre, categoria);
    }
}