# Reglas de Negocio - Rol COCINERO

Este documento contiene las reglas de negocio específicas para el rol **COCINERO** en el sistema CremaPlace.

## 1. Responsabilidades del COCINERO

El COCINERO es responsable de ejecutar las cuotas de producción asignadas en su panel `/dashboard/cocinero`, reportar fallas durante la producción y registrar los tiempos de elaboración de los lotes.

### 1.1. Ejecución de Cuotas de Producción
- El COCINERO visualiza las cuotas de producción asignadas por el JEFE_COCINA en su panel `/dashboard/cocinero`.
- Puede cambiar el estado de su cuota a: `EN_PROCESO`, `COMPLETADA`, `SALTADA`.
- Al completar su cuota, el sistema registra los productos terminados en el inventario correspondiente.
- **No reporta unidades producidas de su cuota asignada.**

### 1.2. Registro de Fallas durante la Producción (Mini Formulario Obligatorio)
- Si el COCINERO detecta cualquier tipo de falla durante la producción (falta de un ingrediente, error de temperatura en el horno, falla de algún sistema de cocina, etc.), debe completar obligatoriamente un mini formulario con:
  - **Falla:** descripción del problema detectado.
  - **Cocinero:** identificador del usuario COCINERO (automático según sesión).
  - **Orden/lote perdido:** orden o cantidad de productos perdidos (ejemplo: 40 fondos de tartas).
- Esta información se envía automáticamente al reporte de mermas (colección `mermas_produccion`).
- El sistema notifica al **JEFE_COCINA** sobre la falla registrada.

## 2. Gestión de Estados de Pedido (COCINERO)

- El COCINERO **no puede ver pedidos pagados**.
- Puede cambiar el estado de sus cuotas de producción a: En preparación, Listo, Cancelado.

## 3. Ver Detalle de Receta y Stock

- Puede ver el detalle de cada receta consolidada asociada a su cuota de producción.
- Puede ver el stock disponible de cada ingrediente.
- **No puede modificar** las recetas base (solo lectura).

## 4. Cuotas de Producción (Orden de Producción Diaria)

- Visualiza las cuotas de producción asignadas por el JEFE_COCINA en su panel `/dashboard/cocinero`.
- Al completar su cuota, el sistema registra los productos terminados en el inventario correspondiente.

## 5. Reportes de Mermas

- **No puede ver reportes de mermas.**
- **No puede** crear, editar ni eliminar reportes de mermas.
- Únicamente puede registrar fallas a través del mini formulario obligatorio (sección 1.2), el cual se deriva al reporte de mermas gestionado por el JEFE_COCINA.

## 6. Plantilla de Lote

- Puede ver los lotes de producción asignados a él (solo lectura).
- **No puede** crear ni editar lotes.
- El sistema toma el tiempo transcurrido desde que la elaboración de un lote empieza hasta que se termina, registrando el tiempo real de elaboración por lote.

## 7. Acceso

- Panel exclusivo en `/dashboard/cocinero/**`.
- Redirigido automáticamente al hacer login si posee el rol `COCINERO`.
