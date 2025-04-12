package com.desafios.sistordenes.controller;

import com.desafios.sistordenes.dto.OrdenFiltroRequest;
import com.desafios.sistordenes.dto.OrdenRequest;
import com.desafios.sistordenes.dto.OrdenResponse;
import com.desafios.sistordenes.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService service;

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@RequestBody @Valid OrdenRequest request) {
        return ResponseEntity.ok(service.crear(request));
    }

    @GetMapping
    public ResponseEntity<Page<OrdenResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<OrdenResponse>> buscar(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Pageable pageable) {
        return ResponseEntity.ok(service.buscar(cliente, fecha, pageable));
    }

    @PostMapping("/buscarpaginado")
    public ResponseEntity<Page<OrdenResponse>> buscarConPaginacion(
            @RequestBody @Valid OrdenFiltroRequest filtro,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        // Limitar tamaño máximo de página
        int safeSize = Math.min(size, 50);
        Pageable pageable = PageRequest.of(page, safeSize);

        return ResponseEntity.ok(service.buscar(filtro.getCliente(), filtro.getFecha(), pageable));
    }


}
