package com.desafios.sistordenes.controller;

import com.desafios.sistordenes.dto.OrdenResponse;
import com.desafios.sistordenes.dto.ProductoRequest;
import com.desafios.sistordenes.dto.ProductoResponse;
import com.desafios.sistordenes.model.Orden;
import com.desafios.sistordenes.repository.OrdenRepository;
import com.desafios.sistordenes.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;
    @Autowired
    private OrdenRepository ordenRepository;


    @PostMapping
    public ProductoResponse crear(@RequestBody @Valid ProductoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public Page<ProductoResponse> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/buscar")
    public Page<ProductoResponse> buscar(@RequestParam String nombre, Pageable pageable) {
        return service.buscarPorNombre(nombre, pageable);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id, @RequestBody @Valid ProductoRequest request) {
        return service.actualizar(id, request);
    }

}
