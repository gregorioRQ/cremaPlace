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

## 6. Sistema de Alertas de Faltante y Caducidad

### 6.1 Alerta de Faltante Predictivo de Stock

- El sistema calcula el consumo promedio diario de cada ingrediente a partir del uso histórico.
- Con el stock actual y el consumo promedio, se estiman los **días de stock restante**.
- Cuando los días restantes caen por debajo de un **umbral configurable** (por defecto: 7 días), se genera una notificación de "faltante inminente".
- El STOCK_MANAGER configura dicho umbral desde su panel.
- El STOCK_MANAGER debe reabastecer el ingrediente antes de que se agote.
- Si el stock llega a 0, se mantiene la regla existente: se deshabilita la venta del producto/variante afectado hasta reponer.

### 6.2 Alerta de Caducidad / Vencimiento de Materia Prima

- Cada ingrediente puede registrar una **fecha de caducidad**, cargada manualmente por el Product Manager al momento en que el producto ingresa a la bodega.
- El sistema notifica al STOCK_MANAGER cuando un ingrediente se acerca a vencer (ventana de caducidad definida por ingrediente, cargada por el Product Manager).
- Se notifica también cuando el ingrediente ya **venció**.
- Los ingredientes vencidos no pueden usarse en producción; el STOCK_MANAGER debe gestionar su retiro (registrado como merma, visible solo lectura).

### 6.3 Registro de Eventos (Auditoría en BD)

- El sistema **debe registrar en la base de datos** el día en que ocurrió un faltante de materia prima y qué materia prima faltó.
- El sistema **debe registrar en la base de datos** el día en que caducó un producto y qué producto caducó.
- Estos registros son de solo lectura para el STOCK_MANAGER y se usan para reporte histórico y análisis de mermas.

### 6.4 Visualización de Alertas

- Nueva vista en `/dashboard/stock/alertas` que lista: faltantes predictivos, próximos a vencer y vencidos.
- Contador de alertas activas en el dashboard principal `/dashboard/stock`.
- Las alertas son **visuales por ahora** (no se descarta push en el futuro).
- Las alertas se marcan como leídas/resueltas tras la acción del STOCK_MANAGER.

### 6.5 Acciones del STOCK_MANAGER ante alertas

- Reponer stock del ingrediente con faltante predictivo (formulario existente de reposición).
- Priorizar uso de ingredientes cercanos a vencer (FIFO).
- Marcar ingrediente como vencido y retirar de la venta/producción.
