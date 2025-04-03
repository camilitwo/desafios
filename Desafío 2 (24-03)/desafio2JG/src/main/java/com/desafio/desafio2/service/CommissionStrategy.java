package com.desafio.desafio2.service;

import com.desafio.desafio2.dto.ComisionResponse;
import com.desafio.desafio2.dto.VentaRequest;

public interface CommissionStrategy {
    double calculateCommission(double totalSales);
}