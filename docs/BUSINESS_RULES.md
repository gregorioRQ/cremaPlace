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
| **Compras/Proveedores** | - | - | Total | Total |
| **Usuarios** | Solo propio perfil | - | - | Total |

## 6. Registro y Autenticación
- **Registro:**
    - Inmediato tras completar el formulario.
    - **Administración Inicial:** El primer usuario registrado obtiene automáticamente el rol `ADMIN`.
    - Email único.
    - Campos obligatorios: Nombre, Email, Teléfono.
    - Contraseña: Mínimo 8 caracteres (sin requisitos de complejidad).
- **Login y Sesión:**
    - Duración de sesión: 30 minutos de inactividad.
    - Bloqueo de cuenta: Tras 8 intentos fallidos consecutivos.

