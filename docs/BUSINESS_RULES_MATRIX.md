# Matriz de Permisos por Rol - Tienda de Dulces

Este documento resume los permisos de cada rol en el sistema CremaPlace.

## Matriz de Permisos

| Acción | USER | COCINERO | STOCK_MANAGER | JEFE_COCINA | ADMIN |
|:---|:---:|:---:|:---:|:---:|:---:|
| **Catálogo** | Ver | Ver | Ver/Editar | Ver | Total |
| **Pedidos** | Crear/Ver propios | Ver pagados y en preparación (con detalle) | Ver necesidades | Ver orden de producción | Total |
| **Estado Pedido** | Pagado | En prep/Listo/Cancelado | - | Ver/Asignar cuotas | Total |
| **Orden de Producción** | - | - | - | Crear/Generar/Distribuir | Total |
| **Cuotas de Producción** | - | Ver/Reportar avance | - | Ver avance global | Total |
| **Cancelar Pedido** | Si (Pend/Pagado) | Sí (con motivo) | - | - | Total |
| **Estimar tiempo** | - | Sí (input visible) | - | - | Total |
| **Ver detalle pedido + receta** | - | Sí | - | Sí | Total |
| **Inventario** | - | - | Ver/Editar/Reponer | Ver | Total |
| **Gestión de Ingredientes** | - | - | Ver/Editar/Reponer | Ver | Total |
| **Gestión de Recetas Base** | - | Ver | - | Crear/Editar/Eliminar | Total |
| **Compras/Proveedores** | - | - | Total | - | Total |
| **Usuarios** | Solo propio perfil | - | - | - | Total |
| **Metas de Producción** | - | - | - | Crear/Cargar/Completar | Total |
| **Planillas de Producción** | - | - | - | Ver/Generar/Descargar | Total |
| **Reportes de Mermas** | - | Ver | - | Crear/Ver/Editar/Eliminar | Total |
| **Plantilla de Lote** | - | Ver | - | Crear/Editar/Ver | Total |

## Leyenda

- **-** : Sin acceso
- **Ver** : Solo lectura
- **Crear/Cargar/Completar** : Puede crear y modificar estados
- **Crear/Editar/Eliminar** : Control total sobre el recurso
- **Ver/Editar/Reponer** : Puede ver, editar y reponer stock
- **Total** : Acceso completo (CRUD + administración)

## Resumen por Rol

### USER (Cliente)
- Ver catálogo, crear/ver pedidos propios, pagar pedidos, cancelar pedidos pendientes/pagados.

### COCINERO
- Ver catálogo y pedidos, gestionar estados (COMENZAR/TERMINAR/SALTEAR), ver recetas (solo lectura), ver cuotas y reportar avance, ver mermas y lotes (solo lectura).

### STOCK_MANAGER
- Ver/editar/reponer inventario, compras/proveedores (total), ver mermas y lotes (solo lectura).

### JEFE_COCINA
- Ver catálogo, ver orden de producción y cuotas, crear/generar/distribuir orden de producción, crear/editar/eliminar recetas, crear/cargar/completar metas, ver/generar/descargar planillas, crear/ver/editar/eliminar mermas, crear/editar/ver lotes.

### ADMIN
- Acceso total a todos los módulos.
