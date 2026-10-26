# Reglas de Negocio - Rol ADMIN (Jefe General)

Este documento contiene las reglas de negocio específicas para el rol **ADMIN** en el sistema CremaPlace.

## 1. Responsabilidades del ADMIN

El ADMIN tiene acceso total a todas las funcionalidades del sistema.

### 1.1. Gestión de Usuarios y Roles
- Solo un `ADMIN` puede asignar roles funcionales (`COCINERO`, `STOCK_MANAGER`, `JEFE_COCINA`, `ADMIN`) desde `/dashboard/admin/users`.
- Puede desbloquear cuentas de usuario bloqueadas.
- Puede buscar usuarios por nombre de usuario.

### 1.2. Gestión de Catálogo
- **Alfajores:** Agregar, editar y eliminar alfajores (sabor, tamaño, precio, tiempo de preparación).
- **Tartas:** Agregar, editar y eliminar tartas (sabor, precio por unidad, tiempo de preparación).

### 1.3. Gestión de Pedidos
- Ver todos los pedidos del sistema.
- Actualizar el estado de cualquier pedido.

### 1.4. Gestión de Inventario
- Ver todo el inventario de productos terminados.
- Actualizar el stock de productos.

### 1.5. Gestión de Recetas Base
- Acceso total a las recetas base (Bill of Materials) de todos los productos.
- Puede definir la vida útil de los productos en el catálogo.

### 1.6. Gestión de Compras y Proveedores
- Acceso total a compras y proveedores.
- Crear, editar y eliminar proveedores.
- Crear, editar y eliminar órdenes de compra.

## 2. Acceso

- Panel exclusivo en `/dashboard/admin/**`.
- Redirigido automáticamente al hacer login si posee el rol `ADMIN`.

## 3. Permisos por Módulo

| Módulo | Permiso ADMIN |
|--------|--------------|
| Catálogo | Total |
| Pedidos | Total |
| Estado Pedido | Total |
| Cancelar Pedido | Total |
| Inventario | Total |
| Gestión de Ingredientes | Total |
| Gestión de Recetas Base | Total |
| Compras/Proveedores | Total |
| Usuarios | Total |
| Orden de Producción | Total |
| Cuotas de Producción | Total |
| Planillas de Producción | Total |
| Reportes de Mermas | Total |
| Plantilla de Lote | Total |
