## Resumen del TP — Asignación de roles y dominio elegido

### Dominio elegido
- Empleado → Legajo (A → B unidireccional 1→1)

### Justificación
- Campos bien definidos en la especificación, ejemplos claros de unicidad (dni, nroLegajo) y casos de uso fáciles de demostrar en consola y video.

---

## Qué ya está hecho (Leonel)
Estos son los cambios y archivos que ya creé y están listos en el repositorio:

- `docs/roles/tasks_leonel.md` — Lista de responsabilidades y tareas para mí (UML, entities, documentación).
- `docs/roles/tasks_fede.md` — Archivos de rol creados para Fede (para que él sepa sus tareas concretas).
- `docs/roles/tasks_gonza.md` — Archivos de rol creados para Gonza (para que él sepa sus tareas concretas).
- `docs/README_ASSIGNMENT.md` — (este archivo) resumen del dominio, estado y pasos siguientes.
- `sql/create_db.sql` — Script inicial para crear la base de datos y las tablas `empleado` y `legajo` con FK única en `legajo.a_id`.
- `sql/seed_data.sql` — Datos de prueba mínimos (2 empleados y legajos) para levantar la demo.

Estado: la tarea de "Diseño y elección de dominio" está marcada como COMPLETADA en el plan de trabajo.

---

## Qué deben hacer los demás integrantes (tareas claras y concretas)

Nota: Yo soy Leonel y me encargo de UML y entities. Abajo están las tareas puntuales para Fede y Gonza.

### Fede — Persistencia / DAOs / Conexión a BD (tareas concretas)
1. Finalizar y probar los scripts SQL:
	- Revisar y ejecutar `sql/create_db.sql` en MySQL 8+.
	- Ejecutar `sql/seed_data.sql` y verificar que los `INSERT` correspondan a los ids creados (si importas desde cero, los ids 1 y 2 deberían existir).
2. Implementar `config/db.properties` con las credenciales de prueba (ejemplo mínimo):
```
jdbc.url=jdbc:mysql://localhost:3306/tfi_prog2
jdbc.user=root
jdbc.password=tu_password
```
3. Implementar `config/DatabaseConnection.java` con un método estático `getConnection()` que lea `config/db.properties` y retorne `java.sql.Connection`.
4. Implementar `dao/GenericDao<T>` (interfaz) y comenzar `dao/EmpleadoDaoJDBC` y `dao/LegajoDaoJDBC`:
	- Usar `PreparedStatement` en todas las consultas.
	- Permitir que los métodos acepten una `Connection` externa para soportar transacciones orquestadas por la capa Service.
5. Probar la conexión con un pequeño main o test que llame a `DatabaseConnection.getConnection()`.

Comandos sugeridos (PowerShell) para importar los scripts a una instancia MySQL local:
```powershell
mysql -u root -p < .\sql\create_db.sql
mysql -u root -p < .\sql\seed_data.sql
```

### Gonza — Services / Transacciones / AppMenu (tareas concretas)
1. Implementar `service/GenericService<T>` con métodos: `insertar`, `actualizar`, `eliminar`, `getById`, `getAll`.
2. Implementar `service/EmpleadoService` y `service/LegajoService` que:
	- Abran una conexión (usar `DatabaseConnection.getConnection()`), hagan `setAutoCommit(false)` y orquesten la creación/actualización compuesta (ej.: crear `Legajo`, asociarlo a `Empleado`, crear `Empleado`).
	- Ejecuten `commit()` si todo OK o `rollback()` ante excepción y siempre restablecer `autoCommit(true)`.
	- Validaciones: campos obligatorios (nombre, apellido, dni), formato de `dni`, unicidad (dni y nroLegajo), y regla 1→1 (no permitir más de un Legajo por Empleado).
3. Implementar `main/AppMenu.java` (consola) con opciones CRUD para `Empleado` y `Legajo`, búsqueda por DNI y manejo robusto de entradas inválidas.
4. Preparar una prueba/control que fuerce un fallo después de crear `Legajo` para demostrar rollback (para el video).

Consejos para la demo de rollback:
- En el service que crea Empleado+Legajo, lanzar una excepción artificial (por ejemplo: division por cero o throw new RuntimeException("simular fallo")) después de insertar `legajo` pero antes de `commit()` para demostrar que no quedan registros inconsistentes.

---

## Próximos pasos y coordinación
1. Fede: confirmar por aquí cuando los scripts se importen OK y subir `config/db.properties.example` y `config/DatabaseConnection.java` (o su esqueleto).
2. Gonza: avisar cuando comience el `EmpleadoService` para coordinar la firma de los DAOs (cómo pasar la `Connection`).
3. Yo (Leonel): empezaré a implementar `entities/Empleado.java` y `entities/Legajo.java` y puedo subirlos en breve.

Plazos sugeridos (flexible)
- UML y entities (Leonel): 2 días.
- Scripts y DatabaseConnection (Fede): 2 días.
- Services y AppMenu (Gonza): 4-6 días.

Si quieren, procedo ahora a crear los archivos base de Java para las entidades (`entities/Empleado.java`, `entities/Legajo.java`) y un `config/db.properties.example` y un esqueleto de `config/DatabaseConnection.java`. Indiquen si desean que los suba ya.

---

Si necesitás que lo deje aún más puntual (por ejemplo, con tickets/issues listos o con plantillas de commits y mensajes), lo preparo y lo subo.