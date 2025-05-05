package com.example.desafio4cs.repository;

import com.example.desafio4cs.model.Orden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    // Buscar por cliente
    Page<Orden> findByClienteContainingIgnoreCase(String cliente, Pageable pageable);

    // Buscar por fecha exacta
    Page<Orden> findByFecha(LocalDate fecha, Pageable pageable);
}
