# Reglas de Negocio - Rol JEFE_COCINA (Jefe de Cocina)

Este documento contiene las reglas de negocio específicas para el rol **JEFE_COCINA** en el sistema CremaPlace.

## 1. Responsabilidades del JEFE_COCINA

El JEFE_COCINA es responsable de coordinar la ejecución diaria de la producción en la cocina: repartir el trabajo entre los cocineros, monitorear el avance de las órdenes de producción y metas, registrar reportes de mermas y gestionar las plantillas de elaboración por lote.

La **Orden de Producción Diaria**, la **venta proyectada**, las **metas de producción del día** y las **recetas base** son emitidas y mantenidas exclusivamente por el rol **ADMIN**. El JEFE_COCINA tiene acceso de **solo lectura** a dicha información.

### 1.1. Orden de Producción Diaria
- La Orden de Producción Diaria es emitida por el **ADMIN** desde el panel de administración.
- El JEFE_COCINA tiene **solo lectura** de la Orden de Producción Diaria generada por el ADMIN.
- Revisar el stock actual de productos terminados (alfajores y tartas) de forma informativa.
- La venta proyectada (colchón adicional) por producto es definida por el **ADMIN**.
- Visualizar a los cocineros activos y las cuotas de producción asignadas.
- Monitorear el avance global de la orden diaria y el estado de cada cocinero.

### 1.2. Gestión de Recetas Base (Bill of Materials)
- Las recetas base son creadas y mantenidas exclusivamente por el **ADMIN**.
- El JEFE_COCINA tiene **solo lectura** de las recetas base de cada producto terminado.
- No puede registrar, modificar ni ajustar las cantidades de ingredientes de ninguna receta base.
- Los cambios aplican a futuras órdenes de producción y cálculos de consumo de materia prima, bajo responsabilidad del ADMIN.

### 1.3. Metas de Producción del Día
- Las metas de producción del día son emitidas por el **ADMIN** (selección de productos terminados y cantidad meta a producir para cada uno).
- El JEFE_COCINA tiene **solo lectura** de las metas de producción del día.
- El JEFE_COCINA es responsable de **repartir el trabajo entre los cocineros** asignando las cuotas de producción de cada uno.
- El sistema calculará automáticamente la cantidad exacta de ingredientes necesarios basándose en las recetas base configuradas por el ADMIN.

### 1.4. Reportes de Mermas
- Registrar reportes de mermas: documentar pérdidas de materia prima o producto terminado durante la producción (ejemplo: 6 kg de masa perdida por error de horneado, error de dosificación en receta, producto quemado, etc.), especificando ingrediente/producto, cantidad, motivo y fecha.

### 1.5. Plantilla de Elaboración por Lote
- Configurar plantilla de elaboración por lote: definir fecha de elaboración y fecha sugerida de vencimiento para cada lote producido.

## 2. Acceso

- Panel exclusivo en `/dashboard/jefe-cocina/**` (incluye distribución de cuotas, mermas y lotes).
- Redirigido automáticamente al hacer login si posee el rol `JEFE_COCINA`.
- Acceso de solo lectura a la Orden de Producción Diaria, venta proyectada, recetas base y metas de producción (gestionadas por el ADMIN).

## 3. Gestión de Recetas Base

### Definición de una receta base
- Cada producto terminado (alfajor, tarta, etc.) tiene una receta asociada.
- La receta especifica la cantidad de cada ingrediente necesario para producir una unidad del producto.
- Ejemplo de receta base:
  - Producto: Tapa de alfajor de maicena (cantidad base: 100 unidades)
  - Harina: 30 g por unidad
  - Azúcar: 15 g por unidad
  - Maicena: 20 g por unidad
  - Huevo: 5 g por unidad

### Ajuste de cantidades
- El **ADMIN** puede editar las cantidades de ingredientes de cualquier receta base en cualquier momento.
- Los cambios aplican a futuras órdenes de producción y cálculos de consumo de materia prima.
- El JEFE_COCINA solo puede ver el detalle de las recetas, no puede modificarlas.

### Uso del sistema
- Al generar una Orden de Producción Diaria, el sistema consulta estas recetas para descontar automáticamente los ingredientes del inventario según la meta de producción asignada.
- Los COCINEROS pueden ver el detalle de la receta asociada a cada producto en su panel, pero no pueden modificarla.

## 4. Orden de Producción Diaria

### 4.1. Objetivo
Generar una planificación diaria de producción que asegure que los cocineros alcancen una cuota de productos terminados basada en:
- Stock actual de productos terminados.
- Pedidos anticipados / pagados pendientes de preparar.
- Venta proyectada ingresada por el ADMIN.

### 4.2. Flujo de Creación de la Orden Diaria

1. **Sugerencia automática:** El sistema muestra por producto:
   - Stock actual disponible.
   - Pedidos anticipados del día (estado Pagado).
   - Producción sugerida = Pedidos anticipados - Stock actual (si es positivo, mínimo 0).

2. **Proyección del ADMIN:** El ADMIN ajusta la cantidad proyectada adicional por producto (colchón de stock). El JEFE_COCINA solo puede visualizar este valor (solo lectura).

3. **Meta total de producción:**
   ```
   Meta Total = Pedidos Anticipados + Venta Proyectada - Stock Actual
   ```

4. **Confirmación y descuento de materia prima:**
   - Al crear/confirmar la Orden Diaria (por el ADMIN), el sistema **descuenta inmediatamente** todos los ingredientes necesarios según las recetas (Bill of Materials) de cada producto a producir.
   - Si falta stock de algún ingrediente, el sistema mostrará advertencia pero permitirá la creación (el STOCK_MANAGER será responsable de reponer).

5. **Distribución de cuotas:**
   - La meta total se reparte en partes iguales entre los cocineros seleccionados por el JEFE_COCINA.
   - Ejemplo: si se requieren 100 alfajores y hay 2 cocineros activos, cada uno recibe una cuota de 50 unidades.
   - Cada cocinero visualiza su cuota asignada en su panel `/dashboard/cocinero`.

### 4.3. Estados de la Orden de Producción
- `CREADA`: Orden generada (por el ADMIN), ingredientes descontados, cuotas asignadas.
- `EN_PROCESO`: Al menos un cocinero ha iniciado su cuota.
- `COMPLETADA`: Todas las cuotas alcanzaron su meta.

## 5. Metas de Producción Diaria y Planillas

### 5.1. Objetivo
Permitir que el ADMIN defina las metas de producción del día, calcular el consumo exacto de materia prima basado en las recetas base, y generar automáticamente una planilla de producción al finalizar la jornada. El JEFE_COCINA reparte el trabajo entre los cocineros.

### 5.2. Carga de Metas de Producción

El ADMIN accede al panel de administración y:
1. Selecciona los productos terminados a producir (alfajores, tartas, etc.).
2. Ingresa la cantidad meta para cada producto.
3. El sistema muestra el cálculo automático de ingredientes necesarios:
   - Consulta la receta base de cada producto.
   - Multiplica las cantidades de ingredientes por la meta de producción.
   - Suma el total por ingrediente entre todos los productos.
4. Al confirmar, se descuenta inmediatamente la materia prima del inventario.
5. El JEFE_COCINA distribuye las metas en cuotas entre los cocineros seleccionados.

El JEFE_COCINA tiene **solo lectura** de las metas de producción del día.

### 5.3. Ejemplo de Cálculo

**Metas del día:**
- Tapa de alfajor de maicena: 100 unidades
- Tarta de frutilla: 30 unidades

**Ingredientes calculados automáticamente:**
- Harina: 100 × 30 g = 3000 g (para tapas)
- Azúcar: 100 × 15 g = 1500 g (para tapas) + 30 × X g (tarta)
- Maicena: 100 × 20 g = 2000 g
- Huevo: 100 × 5 g = 500 g (para tapas)
- Frutilla: 30 × Y g (para tarta)

### 5.4. Estados de la Meta de Producción
- `PENDIENTE`: Metas cargadas por el ADMIN, ingredientes calculados y descontados, cuotas asignadas por el JEFE_COCINA.
- `EN_PROCESO`: Al menos un cocinero ha iniciado su cuota.
- `COMPLETADA`: Todas las cuotas alcanzaron su meta.

### 5.5. Planilla Automática de Producción

Cuando el JEFE_COCINA marca una meta como **COMPLETADA**:

1. El sistema valida que todas las cuotas de los cocineros estén cumplidas.
2. Genera automáticamente una **Planilla de Producción** con:
   - Fecha y hora de producción.
   - Responsable (JEFE_COCINA).
   - Listado de productos con cantidad meta vs cantidad producida.
   - Detalle de ingredientes consumidos (cantidad exacta por ingrediente).
   - Desglose por cocinero (cuota asignada vs cuota cumplida).
   - Estado final: `GENERADA`.
3. La planilla se guarda en la colección de base de datos `planillas_produccion`.
4. El JEFE_COCINA puede ver el historial de planillas en `/dashboard/jefe-cocina/planillas`.
5. Opcional: descarga de la planilla en formato PDF o impresión directa.

### 5.6. Flujo Completo

```
Carga de metas (ADMIN) → Cálculo de ingredientes → Descuento de stock →
Distribución de cuotas (JEFE_COCINA) → Producción por cocineros →
Marcar como completada → Generar planilla automática → Guardar en DB
```

## 6. Sistema de Reporte de Mermas

### 6.1. Objetivo
Permitir que el JEFE_COCINA registre pérdidas de materia prima o producto terminado durante el proceso de producción, almacenando la información en la base de datos para control de costos y trazabilidad.

### 6.2. Registro de una Merma

El JEFE_COCINA accede a `/dashboard/jefe-cocina/mermas` y completa un formulario con:

- **Producto o ingrediente afectado:** selección del item (ejemplo: masa de alfajor, harina, dulce de leche).
- **Cantidad perdida:** valor numérico con unidad de medida (ejemplo: 6 kg, 500 g, 10 unidades).
- **Motivo de la merma:** descripción del error (ejemplo: error de horneado, error de ingrediente en la receta, producto quemado, falla de máquina, etc.).
- **Fecha y hora:** automática al momento del registro.
- **Responsable:** JEFE_COCINA que registra la merma (automático según sesión).

### 6.3. Ejemplo de Registro

| Campo | Valor |
|-------|-------|
| Producto | Masa de alfajor de maicena |
| Cantidad perdida | 6 kg |
| Motivo | Error de horneado (masa quemada) |
| Fecha | 2026-10-26 |
| Responsable | JEFE_COCINA (usuario logueado) |

### 6.4. Almacenamiento en Base de Datos

Cada reporte de merma se guarda en la colección `mermas_produccion` con los siguientes campos:

- `id`
- `fecha`
- `productoId` o `ingredienteId`
- `nombreProducto` / `nombreIngrediente`
- `cantidadPerdida`
- `unidadMedida`
- `motivo`
- `registradoPor` (ID del JEFE_COCINA)

### 6.5. Permisos y Acceso

- **JEFE_COCINA:** puede crear, ver, editar y eliminar reportes de mermas.
- **COCINERO:** solo puede ver los reportes (solo lectura).
- **STOCK_MANAGER:** solo puede ver los reportes (solo lectura).
- **ADMIN:** acceso total.

### 6.6. Flujo Completo

```
Ocurre merma en producción → JEFE_COCINA registra merma → 
Sistema guarda en DB colección mermas_produccion → 
Disponible para consulta y reportes de costo
```

## 7. Plantilla de Elaboración por Lote

### 7.1. Objetivo
Registrar la fecha de elaboración y la fecha sugerida de vencimiento para cada lote de producción, permitiendo trazabilidad y control de caducidad de productos terminados.

### 7.2. Campos de la Plantilla de Lote

Cada lote de producción contendrá los siguientes campos:

- **Número de lote:** identificador único generado automáticamente.
- **Producto:** producto terminado elaborado (alfajor, tarta, etc.).
- **Cantidad producida:** unidades o peso total del lote.
- **Fecha de elaboración:** fecha en que se produjo el lote (registrada automáticamente al crear el lote).
- **Fecha sugerida de vencimiento:** fecha estimada de caducidad calculada basada en la vida útil del producto.
- **Cocinero responsable:** usuario que ejecutó el lote.
- **Estado:** `EN_PROCESO`, `COMPLETADO`, `VENCIDO`.

### 7.3. Cálculo de la Fecha Sugerida de Vencimiento

La fecha sugerida de vencimiento se calcula automáticamente como:

```
Fecha de Vencimiento = Fecha de Elaboración + Vida Útil del Producto (días)
```

La vida útil por tipo de producto es mantenida por el JEFE_COCINA o ADMIN en el catálogo de productos.

### 7.4. Almacenamiento en Base de Datos

Cada lote se guarda en la colección `lotes_produccion` con los siguientes campos:

- `id`
- `numeroLote`
- `productoId`
- `nombreProducto`
- `cantidadProducida`
- `fechaElaboracion`
- `fechaVencimientoSugerida`
- `cocineroId`
- `estado`

### 7.5. Permisos y Acceso

- **JEFE_COCINA:** puede crear, editar y ver lotes, y definir la vida útil de los productos.
- **COCINERO:** solo puede ver los lotes asignados (solo lectura).
- **STOCK_MANAGER:** solo puede ver los lotes para control de inventario (solo lectura).
- **ADMIN:** acceso total.

### 7.6. Flujo Completo

```
Producción de lote → Asignar número de lote → Registrar fecha de elaboración → 
Calcular fecha sugerida de vencimiento → Guardar en DB colección lotes_produccion → 
Monitorear estado del lote (EN_PROCESO → COMPLETADO → VENCIDO)
```
