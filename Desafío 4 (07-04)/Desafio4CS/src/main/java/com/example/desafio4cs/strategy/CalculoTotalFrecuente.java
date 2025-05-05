package com.example.desafio4cs.strategy;

public class CalculoTotalFrecuente implements CalculoTotalStrategy {
    @Override
    public double calcular(double totalOriginal) {
        return totalOriginal * 0.95; // 5% de descuento
    }
}
