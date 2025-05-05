package com.example.desafio2cs.controller;

import com.example.desafio2cs.factory.CommissionStrategyFactory;
import com.example.desafio2cs.strategy.CommissionStrategy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comisiones")
public class CommissionController {

    @GetMapping
    public double calcularComision(
            @RequestParam String tipoVendedor,
            @RequestParam double montoVentas) {

        CommissionStrategy strategy = CommissionStrategyFactory.getCommissionStrategy(tipoVendedor);
        return strategy.calculateCommission(montoVentas);
    }
}