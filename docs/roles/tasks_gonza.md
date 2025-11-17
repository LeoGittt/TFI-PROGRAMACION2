Tareas asignadas a Gonza

Rol principal: Lógica de negocio (Service) y AppMenu (consola)

Responsabilidades detalladas:

1) Services y transacciones (prioridad alta)
- Implementar `service/GenericService<T>` con operaciones: `insertar`, `actualizar`, `eliminar`, `getById`, `getAll`.
- Implementar `service/EmpleadoService` y `service/LegajoService` que:
  - Abran una conexión, hagan `setAutoCommit(false)` y orquesten operaciones compuestas (ej.: crear Legajo, asociarlo a Empleado y crear Empleado).
  - Ejecuten `commit()` si todo OK o `rollback()` ante excepción.
  - Restablezcan `autoCommit(true)` y cierren recursos.
  - Validaciones: campos obligatorios, formatos (dni), y regla 1→1 (no permitir más de un Legajo por Empleado).

2) AppMenu (consola) y Main
- Implementar `main/AppMenu.java` que permita CRUD completo de Empleado y Legajo, búsquedas (ej.: búsqueda por DNI), listado y eliminación lógica.
- Manejo robusto de entradas inválidas, IDs inexistentes y errores de BD.
- Convertir entradas a mayúsculas donde aplique y mostrar mensajes claros de éxito/error.

3) Integración y pruebas
- Integrar con DAOs y Entities, realizar pruebas end-to-end.
- Preparar pasos para demostrar un rollback simulado (forzar error tras crear Legajo para mostrar rollback).

4) Video y demo
- Liderar la parte del video que muestra la operación transaccional y el rollback.
- Encargarse de las capturas de la consola para el informe.

Criterios de aceptación
- Services realizan transacciones con commit/rollback y validaciones.
- AppMenu es usable y cubre los casos requeridos por la consigna.

Plazos sugeridos
- Services básicos: 4 días.
- AppMenu y pruebas: 2-3 días.

Notas
- Coordinar con Fede la firma de los DAOs para pasar `Connection` en los métodos que lo requieran.