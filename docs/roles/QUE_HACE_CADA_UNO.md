# ¿Qué hace cada uno? (Resumen ultra claro)

| Integrante | Rol principal | Tareas concretas | Entregables/checkpoints |
|------------|--------------|------------------|------------------------|
| Leonel     | Diseño, UML, Entities | - Ya realizó: elección de dominio, scripts SQL base, organización de roles, README, plan de trabajo.<br>- Próximo: UML y entities Java. | - UML (`docs/uml/empleado_legajo.png`)<br>- `entities/Empleado.java`<br>- `entities/Legajo.java` |
| Fede       | Base de datos, DAOs, conexión | 1. Probar y ajustar los scripts SQL (`sql/create_db.sql`, `sql/seed_data.sql`).<br>2. Crear `config/db.properties` con los datos de conexión.<br>3. Implementar `config/DatabaseConnection.java`.<br>4. Crear interfaz `dao/GenericDao<T>`. <br>5. Crear DAOs JDBC: `dao/EmpleadoDaoJDBC`, `dao/LegajoDaoJDBC`.<br>6. Probar conexión y DAOs con un test o main. | - Scripts SQL ejecutados sin error.<br>- Archivo `config/db.properties`.<br>- Clase `DatabaseConnection.java`.<br>- Interfaz y DAOs concretos.<br>- Test de conexión funcionando. |
| Gonza      | Services, transacciones, menú | 1. Crear `service/GenericService<T>`. <br>2. Crear `service/EmpleadoService` y `service/LegajoService` (con transacciones y validaciones).<br>3. Implementar `main/AppMenu.java` (CRUD, búsquedas, manejo de errores).<br>4. Preparar demo de rollback (simular error en transacción).<br>5. Probar todo el flujo end-to-end. | - Clases de service.<br>- Menú de consola funcional.<br>- Prueba de rollback documentada.<br>- Capturas para el informe/video. |

---

## Tareas de Fede (detalladas y con ejemplos)

1. **Probar scripts SQL**
   - Ejecutar en MySQL local:
     ```powershell
     mysql -u root -p < .\sql\create_db.sql
     mysql -u root -p < .\sql\seed_data.sql
     ```
   - Si hay error, corregir y avisar.
2. **Crear archivo de propiedades de conexión**
   - `config/db.properties` ejemplo:
     ```
     jdbc.url=jdbc:mysql://localhost:3306/tfi_prog2
     jdbc.user=root
     jdbc.password=tu_password
     ```
3. **Implementar DatabaseConnection**
   - Clase Java con método estático `getConnection()` que lea el archivo de propiedades y devuelva una `Connection`.
4. **Crear DAOs**
   - Interfaz `GenericDao<T>` con métodos CRUD.
   - Clases `EmpleadoDaoJDBC` y `LegajoDaoJDBC` usando `PreparedStatement` y aceptando `Connection` externa.
5. **Test de conexión**
   - Un pequeño `main` o test que imprima "Conexión OK" si se conecta bien.

**¿Cómo saber si Fede terminó?**
- Puede ejecutar los scripts sin error.
- Puede conectarse a la base desde Java.
- Los DAOs compilan y devuelven datos de prueba.

---

## Tareas de Gonza (detalladas y con ejemplos)

1. **Crear servicios (Service)**
   - `GenericService<T>` con métodos CRUD.
   - `EmpleadoService` y `LegajoService` que usen transacciones (`setAutoCommit(false)`, `commit()`, `rollback()`).
   - Validar campos obligatorios y unicidad.
2. **Implementar menú de consola**
   - Clase `AppMenu.java` con opciones para crear, leer, actualizar, eliminar (lógico) y buscar por DNI.
   - Manejar entradas inválidas y mostrar mensajes claros.
3. **Demo de rollback**
   - En el flujo de alta, simular un error después de crear Legajo y antes de commit para mostrar que se revierte todo.
4. **Pruebas end-to-end**
   - Probar todo el flujo desde consola y documentar con capturas.

**¿Cómo saber si Gonza terminó?**
- Puede crear, leer, actualizar y eliminar empleados y legajos desde el menú.
- Si fuerza un error en una transacción, la base no queda inconsistente.
- Tiene capturas y ejemplos para el informe y el video.

---

**Si alguno tiene dudas, revisar los archivos `docs/roles/tasks_fede.md` y `docs/roles/tasks_gonza.md` para más detalle.**
