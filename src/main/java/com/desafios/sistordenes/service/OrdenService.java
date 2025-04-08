package com.desafios.sistordenes.service;

import com.desafios.sistordenes.dto.OrdenRequest;
import com.desafios.sistordenes.dto.OrdenResponse;
import com.desafios.sistordenes.model.Orden;
import com.desafios.sistordenes.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository repository;

    public OrdenResponse crear(OrdenRequest request) {
        Orden orden = new Orden();
        orden.setCliente(request.getCliente());
        orden.setFecha(request.getFecha());
        orden.setTotal(request.getTotal());

        Orden guardada = repository.save(orden);

        return new OrdenResponse(
                guardada.getId(),
                guardada.getCliente(),
                guardada.getFecha(),
                guardada.getTotal()
        );
    }

    public Page<OrdenResponse> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal()));
    }

    public Page<OrdenResponse> buscar(String cliente, LocalDate fecha, Pageable pageable) {
        if (cliente != null) {
            return repository.findByClienteIgnoreCase(cliente, pageable)
                    .map(o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal()));
        } else if (fecha != null) {
            return repository.findByFecha(fecha, pageable)
                    .map(o -> new OrdenResponse(o.getId(), o.getCliente(), o.getFecha(), o.getTotal()));
        }
        return listar(pageable);
    }
}
