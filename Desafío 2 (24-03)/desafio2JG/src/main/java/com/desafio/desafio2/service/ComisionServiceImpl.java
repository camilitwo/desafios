package com.desafio.desafio2.service;

import com.desafio.desafio2.dto.ComisionResponse;
import com.desafio.desafio2.dto.VentaRequest;
import com.desafio.desafio2.model.TipoVendedor;
import com.desafio.desafio2.model.Vendedor;
import com.desafio.desafio2.model.Venta;
import com.desafio.desafio2.repository.VendedorRepository;
import com.desafio.desafio2.repository.VentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ComisionServiceImpl {
    private final ComisionFactory factory;
    private final VendedorRepository vendedorRepository;
    private final VentaRepository ventaRepository;

    @Transactional
    public ComisionResponse calcularComision(VentaRequest request) {
        // 1. Validaciones iniciales
        if (request == null || request.getTipoVendedor() == null || request.getMonto() <= 0) {
            throw new IllegalArgumentException("La solicitud no es válida");
        }

        // 2. Convertir y verificar tipo de vendedor
        TipoVendedor tipo;
        try {
            tipo = TipoVendedor.valueOf(request.getTipoVendedor().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de vendedor no válido: " + request.getTipoVendedor());
        }

        // 3. Obtener estrategia de comisión
        CommissionStrategy strategy = factory.getCommissionStrategy(tipo.name());
        if (strategy == null) {
            throw new IllegalArgumentException("Estrategia no encontrada para el tipo: " + tipo);
        }

        // 4. Calcular comisión
        double comision = strategy.calculateCommission(request.getMonto());

        // Debug: Verificar cálculo
        System.out.println("[DEBUG] Comisión calculada: " + comision +
                " para monto: " + request.getMonto() +
                " y tipo: " + tipo);

        // 5. Buscar o crear vendedor
        Vendedor vendedor = vendedorRepository.findById(request.getVendedorId())
                .orElseGet(() -> crearVendedorPorDefecto(tipo));

        // 6. Crear y guardar venta
        Venta venta = new Venta();
        venta.setVendedor(vendedor);
        venta.setMonto(request.getMonto()); // Asegurar que coincida con la entidad
        venta.setComision(comision);
        venta.setFechaVenta(LocalDateTime.now());

        Venta ventaGuardada = ventaRepository.save(venta);

        // Debug: Verificar persistencia
        System.out.println("[DEBUG] Venta persistida - ID: " + ventaGuardada.getId() +
                " | Monto: " + ventaGuardada.getMonto() +
                " | Comisión: " + ventaGuardada.getComision() +
                " | Vendedor ID: " + ventaGuardada.getVendedor().getId());

        // 7. Retornar respuesta
        return construirRespuesta(comision, tipo, ventaGuardada);
    }

    private Vendedor crearVendedorPorDefecto(TipoVendedor tipo) {
        Vendedor vendedor = new Vendedor();
        vendedor.setNombre("Vendedor " + tipo.name());
        vendedor.setTipo(tipo);
        return vendedorRepository.save(vendedor);
    }

    private ComisionResponse construirRespuesta(double comision, TipoVendedor tipo, Venta venta) {
        return new ComisionResponse(
                comision,
                "Comisión calculada para " + tipo +
                        " | Venta ID: " + venta.getId() +
                        " | Monto: " + venta.getMonto() +
                        " | Comisión: " + venta.getComision() +
                        " | Vendedor: " + venta.getVendedor().getNombre()
        );
    }
}