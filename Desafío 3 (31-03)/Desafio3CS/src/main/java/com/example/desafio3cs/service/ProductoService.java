package com.example.desafio3cs.service;

import com.example.desafio3cs.dto.ProductoRequest;
import com.example.desafio3cs.dto.ProductoResponse;
import com.example.desafio3cs.model.Producto;
import com.example.desafio3cs.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; // ✅ Import necesario

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public ProductoResponse crear(ProductoRequest request) {
        Producto p = new Producto();
        p.setNombre(request.getNombre());
        p.setPrecio(request.getPrecio());
        p.setCategoria(request.getCategoria());

        Producto guardado = repository.save(p);
        return new ProductoResponse(guardado.getId(), guardado.getNombre(), guardado.getCategoria());
    }

    public List<ProductoResponse> listar() {
        return repository.findAll().stream()
                .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getCategoria()))
                .collect(Collectors.toList());
    }

    public List<ProductoResponse> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getCategoria()))
                .collect(Collectors.toList());
    }

    public List<ProductoResponse> buscarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria).stream()
                .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getCategoria()))
                .collect(Collectors.toList());
    }
}
