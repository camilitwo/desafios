package com.desafios.sistordenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Órdenes de Compra",
                version = "1.0",
                description = "Documentación interactiva de la API para registrar y consultar órdenes de compra con productos"
        )
)
@SpringBootApplication
public class SistOrdenesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistOrdenesApplication.class, args);
    }

}
