# Reglas de Negocio Generales - Tienda de Dulces

Este documento contiene las reglas de negocio compartidas por todos los roles del sistema.

## 1. Catálogo

### 1.1. Alfajores
- **Sabores:** Campo libre.
- **Tamaños:** Simple y Triple.
- **Precios:** Fijos por variante.
- **Tiempo de preparación base:** Campo numérico.

### 1.2. Tartas
- **Sabores:** Campo libre.
- **Porciones:** Unidad (precio fijo) y Mitad (precio = 50% de la unidad).
- **Tiempo de preparación base:** Campo numérico.

## 2. Pedidos

### 2.1. Mínimo de compra
Obligatorio 2 Alfajores (tamaño Simple) Y 1 Tarta (porción Mitad) del sabor elegido.

### 2.2. Validación de Stock
Estricta al confirmar el pedido.

### 2.3. Estados de Pedido
- Pendiente
- Pagado
- En Preparación
- Listo
- Entregado
- Cancelado

### 2.4. Gestión de Estados

#### USER (Cliente)
- Actualiza a "Pagado" (subir comprobante).
- Puede cancelar si está "Pendiente" o "Pagado" (stock restituido).

#### COCINERO
- `COMENZAR`: PAGADO → EN_PREPARACION (deduce ingredientes según recetas + establece tiempo estimado).
- `TERMINAR`: EN_PREPARACION → LISTO (solo cambia estado, stock ya deducido).
- `SALTEAR`: CANCELADO (registra motivo opcional, **no** restaura stock).

### 2.5. Estimación de Tiempo
- Sugerencia automática al confirmar (suma tiempos base de productos).
- Ajuste manual obligatorio por el `COCINERO` al iniciar preparación (input visible en tarjeta y detalle).

### 2.6. Cancelación por USUARIO
Si el `USER` cancela (Pendiente/Pagado), el stock se restituye automáticamente.

## 3. Inventario

- Si el stock llega a 0, se deshabilita la venta de ese producto/variante específico hasta reponer stock.
- Responsable: `STOCK_MANAGER`.

## 4. Clientes

- **Registro:** Obligatorio para realizar pedidos (sesiones).
- **Atributos:** Nombre, Email, Teléfono.

## 6. Gestión de Ingredientes y Materia Prima

### 6.1. Sistema de Recetas (Bill of Materials)
- Cada producto terminado (alfajor, tarta) tiene asociada una receta que especifica las cantidades exactas de materias primas necesarias para su producción.
- Ejemplo de receta para Alfajor Chocolate Simple:
  - Harina: 30g
  - Azúcar: 15g
  - Chocolate: 10g
  - Huevo: 5g
- Las recetas son mantenidas por el ADMIN y utilizadas automáticamente por el sistema.

### 6.2. Deducción Automática de Ingredientes
- Cuando un pedido marcado como "Listo" por el COCINERO se completa, el sistema deduce automáticamente las materias primas del inventario según las recetas asociadas.
- Esta deducción ocurre en el método `completarPedido()` del OrderService.
- Si falta stock de algún ingrediente, el sistema mostrará una advertencia pero permitirá la finalización (el STOCK_MANAGER será responsable de reponer).

### 6.3. Notificaciones de Bajo Stock
- El sistema monitorea continuamente los niveles de stock de materias primas.
- Cuando el stock de un ingrediente cae por debajo de su umbral mínimo configurable (por defecto: 10 unidades), se genera una notificación.
- Las notificaciones aparecen como un globo de notificación en la esquina superior derecha del panel del STOCK_MANAGER.
- El globo muestra un conteo de ingredientes con bajo stock y al hacer clic revela la lista detallada.

### 6.4. Reposición de Stock
- El STOCK_MANAGER tiene la capacidad de aumentar el stock de cualquier ingrediente desde su panel en `/dashboard/stock`.
- Esta operación se realiza mediante un formulario sencillo que permite especificar la cantidad a añadir.
- El sistema actualiza inmediatamente el inventario disponible tras la reposición.

## 8. Registro y Autenticación

### 8.1. Registro
- Inmediato tras completar el formulario.
- **Administración Inicial:** El primer usuario registrado obtiene automáticamente el rol `ADMIN`.
- Email único.
- Campos obligatorios: Nombre, Email, Teléfono.
- Contraseña: Mínimo 8 caracteres (sin requisitos de complejidad).

### 8.2. Login y Sesión
- Duración de sesión: 30 minutos de inactividad.
- Bloqueo de cuenta: Tras 8 intentos fallidos consecutivos.

### 8.3. Asignación de Rol Inicial
- Usuarios registrados reciben rol `USER` (CLIENTE) por defecto.
- Al hacer login, acceden a `/dashboard/cliente` para ver el catálogo de productos.
- Solo un `ADMIN` puede asignar roles funcionales (`COCINERO`, `STOCK_MANAGER`, `JEFE_COCINA`, `ADMIN`) desde `/dashboard/admin/users`.
- Una vez asignado rol funcional, el usuario accede a su dashboard correspondiente en siguiente login.
