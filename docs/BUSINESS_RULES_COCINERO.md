# Reglas de Negocio - Rol COCINERO

Este documento contiene las reglas de negocio específicas para el rol **COCINERO** en el sistema CremaPlace.

## 1. Responsabilidades del COCINERO

El COCINERO es responsable de preparar los pedidos mediante las acciones **COMENZAR**, **TERMINAR** y **SALTEAR**.

### COMENZAR
- Cambia el estado de "Pagado" a "En preparación".
- Deduce inmediatamente los ingredientes según las recetas asociadas.
- Registra el tiempo estimado de preparación (input obligatorio).

### TERMINAR
- Cambia el estado de "En preparación" a "Listo" (stock ya fue deducido en COMENZAR).

### SALTEAR
- Cancela el pedido con un motivo opcional.
- No restaura el stock ya deducido (si corresponde).
- Solo se puede cancelar si aún no está Listo/Entregado/Cancelado.

## 2. Gestión de Estados de Pedido (COCINERO)

- Puede ver pedidos pagados y en preparación (con detalle).
- Puede cambiar el estado de los pedidos a: En preparación, Listo, Cancelado.

## 3. Ver Detalle de Pedido + Receta

- Puede ver el detalle de cada pedido incluyendo la receta consolidada.
- Puede ver el stock disponible de cada ingrediente.
- **No puede modificar** las recetas base (solo lectura).

## 4. Cuotas de Producción (Orden de Producción Diaria)

- Visualiza las cuotas de producción asignadas por el JEFE_COCINA en su panel `/dashboard/cocinero`.
- Reporta unidades producidas de su cuota asignada.
- Al completar su cuota, el sistema registra los productos terminados en el inventario correspondiente.

## 5. Reportes de Mermas (Solo Lectura)

- Puede ver los reportes de mermas registrados por el JEFE_COCINA.
- **No puede** crear, editar ni eliminar reportes de mermas.

## 6. Plantilla de Lote (Solo Lectura)

- Puede ver los lotes de producción asignados a él.
- **No puede** crear ni editar lotes.

## 7. Acceso

- Panel exclusivo en `/dashboard/cocinero/**`.
- Redirigido automáticamente al hacer login si posee el rol `COCINERO`.
