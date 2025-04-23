package com.desafios.sistordenes.repository;

import com.desafios.sistordenes.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
