package com.example.desafio4cs.service;

import com.example.desafio4cs.dto.OrdenRequest;
import com.example.desafio4cs.dto.OrdenResponse;
import com.example.desafio4cs.model.Orden;
import com.example.desafio4cs.repository.OrdenRepository;
import com.example.desafio4cs.strategy.CalculoTotalFactory;
import com.example.desafio4cs.strategy.CalculoTotalStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    public OrdenResponse crear(OrdenRequest request) {
        // Aplicar estrategia según tipo de cliente
        CalculoTotalStrategy estrategia = CalculoTotalFactory.obtenerEstrategia(request.getTipoCliente());
        double totalCalculado = estrategia.calcular(request.getTotal());

        // Crear la entidad
        Orden orden = new Orden();
        orden.setCliente(request.getCliente());
        orden.setFecha(request.getFecha());
        orden.setTipoCliente(request.getTipoCliente());
        orden.setTotal(totalCalculado);

        // Guardar y devolver respuesta
        Orden guardada = ordenRepository.save(orden);

        return new OrdenResponse(
                guardada.getId(),
                guardada.getCliente(),
                guardada.getFecha(),
                guardada.getTotal(),
                guardada.getTipoCliente()
        );
    }

    public Page<OrdenResponse> listar(Pageable pageable) {
        return ordenRepository.findAll(pageable).map(
                o -> new OrdenResponse(
                        o.getId(),
                        o.getCliente(),
                        o.getFecha(),
                        o.getTotal(),
                        o.getTipoCliente())
        );
    }

    public Page<OrdenResponse> buscar(String cliente, LocalDate fecha, Pageable pageable) {
        if (cliente != null && !cliente.isBlank()) {
            return ordenRepository.findByClienteContainingIgnoreCase(cliente, pageable)
                    .map(o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal(), o.getTipoCliente()));
        } else if (fecha != null) {
            return ordenRepository.findByFecha(fecha, pageable)
                    .map(o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal(), o.getTipoCliente()));
        }

        return listar(pageable);
    }
}
