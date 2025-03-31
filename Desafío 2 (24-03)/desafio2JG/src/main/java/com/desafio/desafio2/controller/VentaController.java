package com.desafio.desafio2.controller;

import com.desafio.desafio2.dto.VentaRequest;
import com.desafio.desafio2.model.Venta;
import com.desafio.desafio2.model.Vendedor;
import com.desafio.desafio2.repository.VendedorRepository;
import com.desafio.desafio2.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody VentaRequest request) {
        Vendedor vendedor = vendedorRepository.findById(request.getVendedorId())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        Venta venta = new Venta();
        venta.setVendedor(vendedor);
        venta.setMonto(request.getMontoVendido());
        venta.setFechaVenta(LocalDateTime.now());

        Venta ventaGuardada = ventaRepository.save(venta);
        return ResponseEntity.ok(ventaGuardada);
    }
}