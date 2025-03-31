package com.desafio.desafio2.repository;

import com.desafio.desafio2.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}