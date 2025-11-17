Tareas asignadas a Fede

Rol principal: Persistencia y DAO / Conexión a BD

Responsabilidades detalladas:

1) Base de datos y scripts SQL (prioridad alta)
- Diseñar y entregar `sql/create_db.sql` con instrucciones para crear la base de datos y las tablas `empleado` y `legajo`.
- Implementar la relación 1→1 con clave foránea única en `legajo(a_id)` apuntando a `empleado(id)` y `ON DELETE CASCADE`.
- Crear `sql/seed_data.sql` con datos de prueba mínimos para demo (2 empleados y sus legajos).

2) Configuración y conexión
- Implementar `config/DatabaseConnection.java` con método estático que retorne `java.sql.Connection` leyendo propiedades externas (ej. `config/db.properties`).
- Documentar credenciales de prueba en `docs/README_ASSIGNMENT.md`.

3) DAO
- Implementar `dao/GenericDao<T>` (interfaz) con métodos: `crear(T)`, `leer(long)`, `leerTodos()`, `actualizar(T)`, `eliminar(long)`.
- Implementar DAOs concretos `dao/EmpleadoDaoJDBC` y `dao/LegajoDaoJDBC` usando `PreparedStatement` y aceptando una `Connection` externa en métodos que la necesiten.
- Asegurar manejo de recursos (try-with-resources) y traducción de SQLException a excepciones de la capa.

4) Tests básicos y documentación
- Probar los scripts SQL contra una instancia MySQL local y reportar cualquier ajuste.
- Colaborar en el README con pasos para crear la base de datos.

Criterios de aceptación
- Scripts SQL (`sql/create_db.sql`, `sql/seed_data.sql`) ejecutables sin errores en MySQL 8+.
- `DatabaseConnection.getConnection()` funciona con las credenciales de prueba.
- DAOs compilan y usan `PreparedStatement` en todas las consultas.

Plazos sugeridos
- Scripts y DatabaseConnection: 2 días.
- DAOs iniciales: 3-4 días.

Notas
- Usar transacciones coordinadas por la capa Service; por ahora los DAOs deben aceptar `Connection` externa para soportarlo.