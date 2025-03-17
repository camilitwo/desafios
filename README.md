# Desafío Semana 1: Introducción a Patrones de Diseño - Factory Pattern en Spring Boot

## Objetivo
Familiarizarse con la creación de objetos usando el patrón **Factory** en **Spring Boot** para mejorar la modularidad y mantenibilidad del código.

## Requisitos
- Java 17
- Spring Boot 3+
- Maven
- PostgreSQL (Opcional)
- Postman o cURL para probar las APIs

## Descripción del Desafío
Construir un servicio en **Spring Boot** que gestione la creación de **usuarios** con diferentes roles (**ADMIN, USER, GUEST**) utilizando el **Factory Pattern**. Cada usuario tendrá permisos diferentes al interactuar con la aplicación.

## Pasos para completar el desafío

### 1. Crear un nuevo proyecto Spring Boot
Usa Spring Initializr para generar un proyecto con las siguientes dependencias:
- Spring Web
- Spring Data JPA
- PostgreSQL Driver (Opcional si decides persistir datos)

#### IMPORTANTE!! Haz un fork y genera una carpeta con tu nombre y después haz tu commit!
