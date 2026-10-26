# Reglas de Negocio - Tienda de Dulces

Este documento es el índice maestro de todas las reglas de negocio del sistema CremaPlace.
Para facilitar la lectura y mantenimiento, las reglas están divididas por rol en documentos separados.

## 📄 Documentos por Rol

| Rol | Documento | Descripción |
|-----|-----------|-------------|
| **General (compartido)** | [`BUSINESS_RULES_GENERAL.md`](./BUSINESS_RULES_GENERAL.md) | Catálogo, pedidos, inventario, clientes, ingredientes, autenticación |
| **COCINERO** | [`BUSINESS_RULES_COCINERO.md`](./BUSINESS_RULES_COCINERO.md) | Preparación de pedidos, cuotas, avance de producción |
| **STOCK_MANAGER** | [`BUSINESS_RULES_STOCK_MANAGER.md`](./BUSINESS_RULES_STOCK_MANAGER.md) | Inventario, bajo stock, reposición, proveedores |
| **JEFE_COCINA** | [`BUSINESS_RULES_JEFE_COCINA.md`](./BUSINESS_RULES_JEFE_COCINA.md) | Orden de producción, recetas, metas, planillas, mermas, lotes |
| **ADMIN** | [`BUSINESS_RULES_ADMIN.md`](./BUSINESS_RULES_ADMIN.md) | Usuarios, catálogo, pedidos, inventario, acceso total |
| **Matriz de Permisos** | [`BUSINESS_RULES_MATRIX.md`](./BUSINESS_RULES_MATRIX.md) | Tabla resumen de permisos por rol y acción |

## 🎯 Resumen de Roles del Sistema

| Rol | Descripción | Panel de Acceso |
|-----|-------------|-----------------|
| **USER** | Cliente que realiza pedidos | `/dashboard/cliente` |
| **COCINERO** | Prepara pedidos asignados | `/dashboard/cocinero` |
| **STOCK_MANAGER** | Gestiona inventario y proveedores | `/dashboard/stock` |
| **JEFE_COCINA** | Planifica producción diaria y lotes | `/dashboard/jefe-cocina` |
| **ADMIN** | Acceso total al sistema | `/dashboard/admin` |

## ⚡ Inicio Rápido

- ¿Eres **COCINERO**? Lee [`BUSINESS_RULES_COCINERO.md`](./BUSINESS_RULES_COCINERO.md)
- ¿Eres **STOCK_MANAGER**? Lee [`BUSINESS_RULES_STOCK_MANAGER.md`](./BUSINESS_RULES_STOCK_MANAGER.md)
- ¿Eres **JEFE_COCINA**? Lee [`BUSINESS_RULES_JEFE_COCINA.md`](./BUSINESS_RULES_JEFE_COCINA.md)
- ¿Eres **ADMIN**? Lee [`BUSINESS_RULES_ADMIN.md`](./BUSINESS_RULES_ADMIN.md)
- ¿Necesitas ver **todos los permisos**? Lee [`BUSINESS_RULES_MATRIX.md`](./BUSINESS_RULES_MATRIX.md)
- ¿Necesitas las **reglas generales**? Lee [`BUSINESS_RULES_GENERAL.md`](./BUSINESS_RULES_GENERAL.md)
