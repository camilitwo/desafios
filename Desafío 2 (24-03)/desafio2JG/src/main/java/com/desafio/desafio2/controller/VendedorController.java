package com.desafio.desafio2.controller;

import com.desafio.desafio2.model.Vendedor;
import com.desafio.desafio2.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    @Autowired
    private VendedorRepository vendedorRepository;

    @PostMapping
    public ResponseEntity<Vendedor> crearVendedor(@RequestBody Vendedor vendedor) {
        Vendedor nuevoVendedor = vendedorRepository.save(vendedor);
        return ResponseEntity.ok(nuevoVendedor);
    }
}