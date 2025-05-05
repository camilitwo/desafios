package com.example.desafio3cs.controller;

import jakarta.validation.Valid;
import com.example.desafio3cs.dto.ProductoRequest;
import com.example.desafio3cs.dto.ProductoResponse;
import com.example.desafio3cs.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")

public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public ProductoResponse crear(@RequestBody @Valid ProductoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/buscar")
    public List<ProductoResponse> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria
    ) {
        if (nombre != null) return service.buscarPorNombre(nombre);
        if (categoria != null) return service.buscarPorCategoria(categoria);
        return service.listar();
    }
}
