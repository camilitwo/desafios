package com.desafio.desafio2.repository;

import com.desafio.desafio2.model.TipoVendedor;
import com.desafio.desafio2.model.Vendedor;
import com.desafio.desafio2.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Optional<Vendedor> findByTipo(TipoVendedor tipo);
}
