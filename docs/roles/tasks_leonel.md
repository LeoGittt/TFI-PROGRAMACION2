Tareas asignadas a Leonel

Rol principal: Líder de diseño y documentación / Implementación de Entities

Responsabilidades detalladas:

1) Diseño y UML (prioridad alta)
- Crear el diagrama UML (clases, atributos, métodos, paquetes y relación 1→1 unidireccional: Empleado → Legajo).
- Guardar el diagrama como `docs/uml/empleado_legajo.png` (o .jpg/.pdf).
- Redactar un breve documento de decisiones de diseño (por qué FK única en B, opciones consideradas).

2) Entities (Java)
- Implementar las entidades en `entities/`: `Empleado` (A) y `Legajo` (B).
  - Incluir `id: Long`, `eliminado: Boolean`, constructores (vacío y completo), getters/setters y `toString()` legible.
  - En `Empleado`, agregar `private Legajo legajo;` como referencia 1→1.
- Escribir tests unitarios simples (si hay tiempo) que construyan instancias y verifiquen `toString()` y getters/setters.

3) Documentación y entrega
- Redactar la sección "Dominio elegido" y "Decisiones de diseño" en `docs/README_ASSIGNMENT.md`.
- Colaborar en el informe PDF: sección de Diseño y UML.

4) Soporte en integración
- Revisar el trabajo de DAOs y Services para asegurar que las entidades cumplen con el mapeo y validaciones.

Criterios de aceptación
- `entities/Empleado.java` y `entities/Legajo.java` compilan y siguen la especificación de campos.
- UML disponible en `docs/uml/empleado_legajo.png`.

Plazos sugeridos
- UML y entities básicos: 2 días.

Notas
- Si surgen dudas en longitudes o nombres, usar los sugeridos en la especificación (ej. `dni` máx. 15, `nroLegajo` máx. 20).