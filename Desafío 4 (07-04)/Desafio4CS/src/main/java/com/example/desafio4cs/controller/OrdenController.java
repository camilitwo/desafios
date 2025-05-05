package com.example.desafio4cs.controller;

import com.example.desafio4cs.dto.OrdenRequest;
import com.example.desafio4cs.dto.OrdenResponse;
import com.example.desafio4cs.service.OrdenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@RequestBody @Valid OrdenRequest request) {
        return ResponseEntity.ok(ordenService.crear(request));
    }

    @GetMapping
    public ResponseEntity<Page<OrdenResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(ordenService.listar(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<OrdenResponse>> buscar(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Pageable pageable) {

        return ResponseEntity.ok(ordenService.buscar(cliente, fecha, pageable));
    }
}
