# Reglas de Negocio - Tienda de Dulces

## 1. Catálogo
- **Alfajores:**
  - Sabores: Campo libre.
  - Tamaños: Simple y Triple.
  - Precios: Fijos por variante.
  - Tiempo de preparación base: Campo numérico.
- **Tartas:**
  - Sabores: Campo libre.
  - Porciones: Unidad (precio fijo) y Mitad (precio = 50% de la unidad).
  - Tiempo de preparación base: Campo numérico.

## 2. Pedidos
- **Mínimo de compra:** Obligatorio 2 Alfajores (tamaño Simple) Y 1 Tarta (porción Mitad) del sabor elegido.
- **Validación de Stock:** Estricta al confirmar el pedido.
- **Estados:** Pendiente, Pagado, En Preparación, Listo, Entregado.
- **Gestión de Estados:** 
  - `USER`: Actualiza a "Pagado" (subir comprobante), puede cancelar si está "Pendiente" o "Pagado".
  - `COCINERO`: Actualiza a "En preparación" y "Listo".
- **Estimación de tiempo:** 
  - Sugerencia automática al confirmar (Suma tiempos base).
  - Ajuste manual opcional por el `COCINERO` al iniciar preparación.
- **Cancelación:** Si el `USER` cancela (Pendiente/Pagado), el stock se restituye automáticamente.

## 3. Inventario
- Si el stock llega a 0, se deshabilita la venta de ese producto/variante específico hasta reponer stock.
- Responsable: `STOCK_MANAGER`.

## 4. Clientes
- **Registro:** Obligatorio para realizar pedidos (sesiones).
- **Atributos:** Nombre, Email, Teléfono.

## 5. Roles y Permisos

 
| Acción | USER | COCINERO | STOCK_MANAGER | ADMIN (JEFE) |
| :--- | :---: | :---: | :---: | :---: |
| **Catálogo** | Ver | Ver | Ver/Editar | Total |
| **Pedidos** | Crear/Ver propios | Ver pagados | Ver necesidades | Total |
| **Estado Pedido** | Pagado | En prep/Listo | - | Total |
| **Cancelar Pedido** | Si (Pend/Pagado) | - | - | Total |
| **Estimar tiempo** | - | Si (Al preparar) | - | Total |
| **Inventario** | - | - | Ver/Editar | Total |
| **Gestión de Ingredientes** | - | - | Ver/Editar/Reponer | Total |
| **Compras/Proveedores** | - | - | Total | Total |
| **Usuarios** | Solo propio perfil | - | - | Total |

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

## 7. Roles y Responsabilidades Actualizadas

### 7.1. Rol COCINERO
- Preparar los pedidos marcándolos como "En preparación" y posteriormente "Listo" al finalizar.
- **Nueva responsabilidad:** Al marcar un pedido como "Listo", el sistema deduce automáticamente las materias primas según las recetas asociadas.
- No necesita intervenir en la gestión de materias primas, pero debe asegurar que los pedidos se completen correctamente para que se active el proceso de deducción.

### 7.2. Rol STOCK_MANAGER
- Monitorear el inventario de materias primas mediante su panel en `/dashboard/stock`.
- **Nueva responsabilidad:** Responder a las notificaciones de bajo stock reponiendo los ingredientes necesarios.
- **Nueva responsabilidad:** Aumentar el stock de ingredientes cuando sea necesario mediante la interfaz de reposición.
- Ver el inventario de productos terminados (manteniendo funcionalidad existente).
- Coordinar con proveedores para abastecimiento (manteniendo responsabilidad existente).

## 8. Registro y Autenticación
- **Registro:**
    - Inmediato tras completar el formulario.
    - **Administración Inicial:** El primer usuario registrado obtiene automáticamente el rol `ADMIN`.
    - Email único.
    - Campos obligatorios: Nombre, Email, Teléfono.
    - Contraseña: Mínimo 8 caracteres (sin requisitos de complejidad).
- **Login y Sesión:**
    - Duración de sesión: 30 minutos de inactividad.
    - Bloqueo de cuenta: Tras 8 intentos fallidos consecutivos.
- **Asignación de Rol Inicial:**
    - Usuarios registrados reciben solo rol `USER` por defecto.
    - Al hacer login, son redirigidos a `/dashboard/sin-rol` con mensaje informativo.
    - Solo un `ADMIN` puede asignar roles funcionales (`COCINERO`, `STOCK_MANAGER`, `ADMIN`) desde `/dashboard/admin/users`.
    - Una vez asignado rol funcional, el usuario accede a su dashboard correspondiente en siguiente login.

