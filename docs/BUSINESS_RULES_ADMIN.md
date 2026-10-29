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
- Define la vida útil de los productos en el catálogo.

### 1.3. Gestión de Pedidos
- Ver todos los pedidos del sistema.
- Actualizar el estado de cualquier pedido.

### 1.4. Gestión de Inventario
- Ver todo el inventario de productos terminados.
- Actualizar el stock de productos.

### 1.5. Gestión de Recetas Base
- Registrar y mantener las recetas base de cada producto terminado, especificando la cantidad exacta de cada ingrediente requerido por unidad producida.
- Puede ajustar las cantidades de ingredientes de cualquier receta base en cualquier momento.
- Los cambios aplican a futuras órdenes de producción y cálculos de consumo de materia prima.

### 1.6. Orden de Producción Diaria
- Emitir la **Orden de Producción Diaria** desde `/dashboard/admin/orden-produccion`.
- Revisar el stock actual de productos terminados (alfajores y tartas).
- Ingresar la **venta proyectada** (colchón adicional) por producto para el día.
- Seleccionar a los cocineros activos que participarán en la producción del día.
- El sistema distribuye automáticamente las metas de producción entre los cocineros asignados (cuotas).
- Monitorear el avance global de la orden diaria y el estado de cada cocinero.
- Al crear/confirmar la Orden Diaria, el sistema descuenta inmediatamente todos los ingredientes necesarios según las recetas (Bill of Materials). Si falta stock de algún ingrediente, el sistema muestra advertencia pero permite la creación (el STOCK_MANAGER será responsable de reponer).

### 1.7. Metas de Producción del Día
- Cargar las metas de producción del día: seleccionar productos terminados y definir la cantidad meta a producir para cada uno (ejemplo: 100 tapas de alfajor de maicena, 50 tartas de frutilla).
- El sistema calculará automáticamente la cantidad exacta de ingredientes necesarios basándose en las recetas base configuradas.
- Al confirmar, se descuenta inmediatamente la materia prima del inventario.

### 1.8. Gestión de Compras y Proveedores
- Acceso total a compras y proveedores.
- Crear, editar y eliminar proveedores.
- Crear, editar y eliminar órdenes de compra.

## 2. Recálculo Dinámico de Costos y Precios de Venta Sugeridos

### 2.1 Costo Unitario de Materia Prima

- Cada ingrediente debe tener un **costo unitario** (por gramo o unidad) registrado en el sistema.
- El costo unitario se ingresa **manualmente por el ADMIN** al recibir insumos (orden de compra o ingreso a bodega).
- El sistema utiliza el **costo más reciente conocido** de cada ingrediente para los cálculos de producción.

### 2.2 Costo de Producción por Producto

- El costo de producir un alfajor o tarta se calcula como: **Σ (costo unitario del ingrediente × cantidad usada en su receta)**.
- El sistema **recalcula dinámicamente** el costo de producción de cada producto del catálogo cada vez que el ADMIN actualiza el costo de cualquiera de sus ingredientes (inflación o subida de precios).
- El ADMIN puede consultar el costo de producción actual de cada producto desde `/dashboard/admin/costos`.

### 2.3 Margen de Ganancia y Precio de Venta Sugerido

- El ADMIN configura un **margen de ganancia deseado por producto individual** (porcentaje).
- Fórmula de cálculo: `Precio Sugerido = Costo de Producción / (1 - Margen)`.
- El sistema compara el precio sugerido con el **precio real** cargado en el catálogo del producto.
- Si el precio real es inferior al sugerido, el sistema muestra una advertencia de **"precio por debajo del margen deseado"**.

### 2.4 Acción del ADMIN sobre precios

- El precio sugerido es una **recomendación visual**; no se aplica automáticamente al catálogo.
- El ADMIN puede aceptar el precio sugerido y actualizar manualmente el precio del producto, o mantener el precio actual.

### 2.5 Historial de Costos

- El sistema registra un **historial de costos de producción por producto y fecha** (versiones por fecha).
- Cada vez que el costo de producción de un producto cambia (por variación en costo de insumos), se crea un nuevo registro con la fecha del cambio.
- El ADMIN puede ver reportes de variación de costos a lo largo del tiempo para análisis de inflación.

## 3. Reportes de Ventas por Día

### 3.1 Días con Mayor Cantidad de Ventas

- El sistema debe identificar y mostrar los días con mayor cantidad de ventas.
- "Cantidad de ventas" se entiende como el número total de pedidos realizados en un día.
- El sistema agrupa los pedidos por fecha (día) a partir del campo `fechaCreacion` de cada pedido.
- El ADMIN puede ver un listado ordenado de días, de mayor a menor cantidad de ventas.

### 3.2 Cantidad de Productos Vendidos por Día

- Además del número de pedidos, el sistema muestra la cantidad total de productos vendidos por día (suma de alfajores y tartas de cada pedido).
- Cada producto dentro de un pedido cuenta como una unidad vendida.

### 3.3 Visualización del Reporte

- Nueva vista en `/dashboard/admin/reportes/ventas` que muestra:
  - Ranking de días con más ventas (cantidad de pedidos y cantidad de productos).
  - Rango de fechas seleccionable por el ADMIN (por defecto: los últimos 30 días).
  - Total de ventas acumulado (monto y cantidad) en el período seleccionado.

### 3.4 Acceso al Reporte

- Exclusivo para el rol ADMIN desde `/dashboard/admin/**`.
- Los datos son de solo lectura.

## 4. Acceso

- Panel exclusivo en `/dashboard/admin/**`.
- Redirigido automáticamente al hacer login si posee el rol `ADMIN`.

## 5. Permisos por Módulo

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
| Venta Proyectada | Total |
| Cuotas de Producción | Total |
| Metas de Producción | Total |
| Planillas de Producción | Total |
| Reportes de Mermas | Total |
| Plantilla de Lote | Total |
