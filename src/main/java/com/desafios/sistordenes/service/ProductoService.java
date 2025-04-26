package com.desafios.sistordenes.service;

import com.desafios.sistordenes.dto.ProductoRequest;
import com.desafios.sistordenes.dto.ProductoResponse;
import com.desafios.sistordenes.model.Producto;
import com.desafios.sistordenes.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public ProductoResponse crear(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setCantidad(request.getCantidad());
        producto.setPrecioUnitario(request.getPrecioUnitario());
        Producto guardado = repository.save(producto);
        return new ProductoResponse(guardado.getNombre(), guardado.getCantidad(), guardado.getPrecioUnitario());
    }

    public Page<ProductoResponse> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(p -> new ProductoResponse(p.getNombre(), p.getCantidad(), p.getPrecioUnitario()));
    }

    public Page<ProductoResponse> buscarPorNombre(String nombre, Pageable pageable) {
        return repository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(p -> new ProductoResponse(p.getNombre(), p.getCantidad(), p.getPrecioUnitario()));
    }
    public void eliminar(Long id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con id: " + id));

        repository.delete(producto);

    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + id));

        producto.setNombre(request.getNombre());
        producto.setCantidad(request.getCantidad());
        producto.setPrecioUnitario(request.getPrecioUnitario());

        Producto actualizado = repository.save(producto);
        return new ProductoResponse(actualizado.getNombre(), actualizado.getCantidad(), actualizado.getPrecioUnitario());
    }

}
