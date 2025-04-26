package com.desafios.sistordenes.controller;

import com.desafios.sistordenes.dto.*;
import com.desafios.sistordenes.model.Orden;
import com.desafios.sistordenes.service.EmailService;
import com.desafios.sistordenes.service.OrdenService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService service;

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@RequestBody @Valid OrdenRequest request) {
        OrdenResponse orden = service.crear(request);
        return ResponseEntity.ok(orden);
    }

    @GetMapping
    public ResponseEntity<Page<OrdenResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<OrdenResponse>> buscar(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Pageable pageable) {
        return ResponseEntity.ok(service.buscar(cliente, fecha, pageable));
    }

    @PostMapping("/buscarpaginado")
    public ResponseEntity<Page<OrdenResponse>> buscarConPaginacion(
            @RequestBody @Valid OrdenFiltroRequest filtro,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        int safeSize = Math.min(size, 50);
        Pageable pageable = PageRequest.of(page, safeSize);

        return ResponseEntity.ok(service.buscar(filtro.getCliente(), filtro.getFecha(), pageable));
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<List<ProductoResponse>> obtenerProductosPorOrden(@PathVariable Long id) {
        Orden orden = service.getOrdenPorId(id);
        List<ProductoResponse> productos = orden.getProductos().stream()
                .map(p -> new ProductoResponse(p.getNombre(), p.getCantidad(), p.getPrecioUnitario()))
                .toList();

        return ResponseEntity.ok(productos);
    }

    @DeleteMapping("/{idOrden}/productos/{idProducto}")
    public ResponseEntity<OrdenResponse> eliminarProductoDeOrden(
            @PathVariable Long idOrden,
            @PathVariable Long idProducto) {
        return ResponseEntity.ok(service.eliminarProductoDeOrden(idOrden, idProducto));
    }

    @PutMapping("/{idOrden}/productos/{idProducto}")
    public ResponseEntity<OrdenResponse> actualizarProductoEnOrden(
            @PathVariable Long idOrden,
            @PathVariable Long idProducto,
            @RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.ok(service.actualizarProductoEnOrden(idOrden, idProducto, request));
    }

    @PostMapping("/{idOrden}/productos")
    public ResponseEntity<OrdenResponse> agregarProducto(
            @PathVariable Long idOrden,
            @RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.ok(service.agregarProducto(idOrden, request));
    }
    @DeleteMapping("/{idOrden}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long idOrden) {
        service.eliminarOrden(idOrden);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> enviarPorEmail(@RequestBody EmailRequest request) {
        try {
            if (request.getEmails() == null || request.getEmails().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Debe ingresar al menos un email."));
            }

            if (request.getOrdenes() == null || request.getOrdenes().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Debe enviar órdenes para generar el CSV."));
            }

            emailService.enviarEmailConCsv(request.getEmails(), request.getOrdenes());

            return ResponseEntity.ok(Map.of("message", "Correo enviado correctamente."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("message", "Error al enviar el correo."));
        }
    }


}
