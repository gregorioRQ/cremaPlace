# Reglas de Negocio - Rol STOCK_MANAGER

Este documento contiene las reglas de negocio específicas para el rol **STOCK_MANAGER** en el sistema CremaPlace.

## 1. Responsabilidades del STOCK_MANAGER

El STOCK_MANAGER es responsable de monitorear el inventario de materias primas mediante su panel en `/dashboard/stock`.

### Monitoreo de Bajo Stock
- Responder a las notificaciones de bajo stock reponiendo los ingredientes necesarios.
- El sistema monitorea continuamente los niveles de stock de materias primas.
- Cuando el stock de un ingrediente cae por debajo de su umbral mínimo configurable (por defecto: 10 unidades), se genera una notificación.

### Reposición de Stock
- Aumentar el stock de ingredientes cuando sea necesario mediante la interfaz de reposición.
- Esta operación se realiza mediante un formulario sencillo que permite especificar la cantidad a añadir.
- El sistema actualiza inmediatamente el inventario disponible tras la reposición.

### Inventario de Productos Terminados
- Ver el inventario de productos terminados (manteniendo funcionalidad existente).

### Compras y Proveedores
- Coordinar con proveedores para abastecimiento (manteniendo responsabilidad existente).
- Acceso total a compras/proveedores.

## 2. Validación de Stock en Venta

- Si el stock llega a 0, se deshabilita la venta de ese producto/variante específico hasta reponer stock.

## 3. Reportes de Mermas (Solo Lectura)

- Puede ver los reportes de mermas registrados por el JEFE_COCINA.
- **No puede** crear, editar ni eliminar reportes de mermas.

## 4. Plantilla de Lote (Solo Lectura)

- Puede ver los lotes de producción para control de inventario.
- **No puede** crear ni editar lotes.

## 5. Acceso

- Panel exclusivo en `/dashboard/stock/**`.
- Redirigido automáticamente al hacer login si posee el rol `STOCK_MANAGER`.
