package com.desafios.sistordenes.repository;

import com.desafios.sistordenes.model.Orden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    Page<Orden> findByClienteIgnoreCase(String cliente, Pageable pageable);


    Page<Orden> findByFecha(LocalDate fecha, Pageable pageable);
}
