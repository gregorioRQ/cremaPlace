# CremaPlace

Sistema de gestión para tienda de dulces especializado en la venta de alfajores y tartas.

## Descripción
CremaPlace es una aplicación web diseñada para gestionar el catálogo de productos, el flujo de pedidos de los clientes, la producción (cocina) y el inventario, permitiendo un flujo de trabajo eficiente entre distintos roles.

## Tecnologías
- **Backend:** Java, Spring Boot
- **Base de Datos:** MongoDB
- **Gestor de dependencias:** Maven
- **Frontend:** Thymeleaf + Vanilla JS
- **Seguridad:** Spring Security (Manejo de roles y sesiones)
- **APIs:** REST (CRUD y lógica) + SOAP (Catálogo)
- **Observabilidad:** AOP (Aspect Oriented Programming) para Logging

## Arquitectura de Seguridad
- **Bootstrap de Administrador:** El primer usuario registrado obtiene automáticamente el rol `ADMIN` de forma atómica y segura mediante transacciones de MongoDB (`@Transactional`), evitando condiciones de carrera (*race conditions*).
- **Redirección Inteligente Post-Login:** Implementación de `CustomAuthenticationSuccessHandler` para dirigir automáticamente al usuario a su área correspondiente (`/dashboard/admin`, `/dashboard/user`, etc.) inmediatamente después de autenticarse con éxito.
- **Protección Estricta de Rutas:** Configuración en `SecurityConfig` que restringe el acceso a nivel de URL (ej. `/dashboard/admin/**` requiere `ROLE_ADMIN`), impidiendo que los usuarios accedan de manera manual a paneles que no les corresponden.
- **Dashboard Modular:** Vistas unificadas con fragmentos dinámicos de Thymeleaf cargados según el rol validado por el servidor.

## Reglas de Negocio
Para más detalles, consultar el archivo `docs/BUSINESS_RULES.md`.

## Roles
- **USER:** Cliente (Gestión de pedidos propios).
- **COCINERO:** Producción y gestión de tiempos de preparación.
- **STOCK_MANAGER:** Gestión de inventario y compras.
- **ADMIN:** Gestión total del sistema.

## Estructura del Repositorio
- `src/`: Código fuente de la aplicación.
- `docs/`: Documentación del proyecto y reglas de negocio.
