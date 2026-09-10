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

## Arquitectura
- **Dashboard:** Implementación única con fragmentos dinámicos (`Thymeleaf`) según el rol del usuario.

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
