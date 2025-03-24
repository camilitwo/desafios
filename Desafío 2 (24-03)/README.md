# Desafío Semana 2 (24-03): Introducción a Patrones de Diseño - Factory Pattern en Spring Boot

## Objetivo
Aplicar el Patrón Strategy para encapsular diferentes estrategias de cálculo de comisión según el tipo de vendedor: Junior, Senior y Freelance.

## Requisitos
- Java 17
- Spring Boot
- Maven
- PostgreSQL (Opcional)
- Postman o cURL para probar las APIs

## Descripción del Desafío
Crear una API REST que calcule la comisión correspondiente según el tipo de vendedor y el monto total vendido.

### Reglas de Negocio
- Junior: 5% de comisión.
- Senior: 10% de comisión.
- Freelance: 15% si vende más de $1.000.000, si no, 8%.


## Pasos para completar el desafío

### 1. Crear un nuevo proyecto Spring Boot
Usa Spring Initializr para generar un proyecto con las siguientes dependencias:
- Spring Web
- Spring Data JPA
- PostgreSQL Driver (Opcional si decides persistir datos)

#### **IMPORTANTE!!** Haz un fork y genera una carpeta con tu nombre y después haz tu commit!

###### una pequeña ayuda

#### Crear una interfaz unica para las estrategias de comisiones
```java
public interface CommissionStrategy {
    double calculateCommission(double totalSales);
}
```

#### Crear una clase para cada estrategia de comisiones
```java
public class JuniorCommissionStrategy implements CommissionStrategy {
    @Override
    public double calculateCommission(double totalSales) {
        return totalSales * 0.05;
    }
}

// TODO:: implementar SeniorCommissionStrategy y FreelanceCommissionStrategy
```

#### Del desafío pasado, utilizar el factory pattern para instanciar la estrategia de comisiones
```java
public class CommissionStrategyFactory {
    public static CommissionStrategy getCommissionStrategy(String type) {
        switch (type) {
            case "Junior":
                return new JuniorCommissionStrategy();
            case "Senior":
                return new SeniorCommissionStrategy();
            case "Freelance":
                return new FreelanceCommissionStrategy();
            default:
                throw new IllegalArgumentException("Invalid commission type");
        }
    }
}
```

