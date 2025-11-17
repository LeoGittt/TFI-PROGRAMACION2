# GUION PARA VIDEO DEL TFI - PROYECTO PROPIEDAD → ESCRITURANOTARIAL

**Duración total:** 10-15 minutos  
**Formato:** Pantalla compartida (Leonel) + Voces de los integrantes agregadas después

---

## 🎬 ESTRUCTURA GENERAL

1. **Introducción y Presentación** (2 min) - Leonel
2. **Explicación de Entidades** (2-3 min) - Leonel
3. **Explicación de DAO y Base de Datos** (2-3 min) - Fede
4. **Explicación de Services y Transacciones** (2-3 min) - Gonza
5. **Demostración del Menú y Funcionalidades** (3-4 min) - Gonza
6. **Demo de Rollback** (2 min) - Gonza
7. **Cierre** (1 min) - Todos

---

## 📝 GUION DETALLADO PASO A PASO

---

### 🎤 PARTE 1: INTRODUCCIÓN Y PRESENTACIÓN (Leonel)
**Duración:** 2 minutos  
**Pantalla:** Explorador de Windows mostrando la carpeta del proyecto

#### PASOS A SEGUIR:

1. **[Abrir Explorador de Windows]**
   - Ir a: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2`
   - Mostrar la estructura de carpetas completa

2. **[Mostrar las carpetas principales]**
   - `config/` - Configuración de base de datos
   - `entities/` - Entidades del dominio
   - `dao/` - Capa de acceso a datos
   - `service/` - Capa de servicios
   - `main/` - Menú de consola
   - `sql/` - Scripts de base de datos

#### SCRIPT DE LEONEL:

"Hola, somos el equipo del Trabajo Final Integrador de Programación 2. Mi nombre es [Tu nombre], y junto con mis compañeros [Nombre de Fede], [Nombre de Gonza] y [Nombre del cuarto integrante], desarrollamos una aplicación Java que modela una relación 1→1 unidireccional entre Propiedad y EscrituraNotarial."

**[Cambiar a cámara - rostro visible]**

"En este video vamos a explicar la arquitectura del proyecto, mostrar cómo funciona la aplicación y demostrar las transacciones con rollback. Empecemos."

**[Volver a pantalla compartida]**

"El proyecto está organizado en capas siguiendo el patrón DAO y Service. Cada uno de nosotros trabajó en diferentes partes del proyecto."

---

### 🎤 PARTE 2: EXPLICACIÓN DE ENTIDADES (Leonel)
**Duración:** 2-3 minutos  
**Pantalla:** IDE abierto con los archivos de entidades

#### PASOS A SEGUIR:

1. **[Abrir tu IDE (IntelliJ, Eclipse, VS Code, etc.)]**
   - Abrir el proyecto: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2`

2. **[Abrir el archivo Propiedad.java]**
   - Ruta: `entities/Propiedad.java`
   - Hacer scroll para mostrar todo el código

3. **[Señalar línea por línea mientras explicas]**

#### SCRIPT DE LEONEL:

"Yo me encargué de desarrollar las entidades del dominio. Empecemos con la clase Propiedad."

**[Abrir: `entities/Propiedad.java`]**

"Este es el archivo Propiedad.java. Como pueden ver en la línea 6, es una clase pública en el paquete entities."

**[Señalar líneas 7-14]**

"En las líneas 7 a 14 están los atributos de la clase: id de tipo Long, eliminado de tipo Boolean para la baja lógica, padronCatastral que es un String único, direccion, superficieM2 que es un BigDecimal para tener precisión decimal, destino que es un enum, antiguedad, y lo más importante..."

**[Señalar línea 14 específicamente]**

"...en la línea 14, la referencia a EscrituraNotarial: `private EscrituraNotarial escrituraNotarial;`"

"Esta es la relación 1→1 unidireccional: solo Propiedad conoce a EscrituraNotarial, pero EscrituraNotarial no tiene referencia a Propiedad en el modelo de objetos."

**[Señalar líneas 16]**

"En la línea 16 tenemos el enum Destino con valores RES y COM."

**[Mostrar constructores - líneas 18-31]**

"En las líneas 18 a 31 tenemos los constructores: uno vacío y uno completo con todos los parámetros."

**[Mostrar getters y setters - líneas 33-49]**

"De la línea 33 a la 49 están todos los getters y setters para cada atributo."

**[Mostrar toString - líneas 51-63]**

"Y finalmente, de la línea 51 a la 63, el método toString que muestra todos los datos de forma legible."

**[Ahora abrir: `entities/EscrituraNotarial.java`]**

"Ahora veamos la clase EscrituraNotarial. Este es el archivo EscrituraNotarial.java."

**[Señalar líneas 7-16]**

"En las líneas 7 a 16 están los atributos: id, eliminado, nroEscritura, fecha que es LocalDate, notaria, tomo, folio, observaciones, y en la línea 16 hay un campo temporal propiedadId que se usa solo para la persistencia."

**[Importante: Señalar que NO hay atributo de tipo Propiedad]**

"Como pueden ver, NO hay un atributo de tipo Propiedad aquí. Esto garantiza la unidireccionalidad: EscrituraNotarial no conoce a Propiedad en el modelo de objetos."

**[Mostrar constructores y métodos]**

"Al igual que Propiedad, tiene constructores vacío y completo, todos los getters y setters, y un método toString."

---

### 🎤 PARTE 3: EXPLICACIÓN DE DAO Y BASE DE DATOS (Fede)
**Duración:** 2-3 minutos  
**Pantalla:** IDE mostrando DAOs y MySQL Workbench

#### PASOS A SEGUIR:

1. **[Abrir: `config/DatabaseConnection.java`]**

2. **[Abrir: `dao/GenericDao.java`]**

3. **[Abrir: `dao/PropiedadDao.java`]**

4. **[Abrir MySQL Workbench]**

5. **[Abrir: `sql/create_db.sql`]**

#### SCRIPT DE FEDE:

"Hola, soy [Nombre de Fede] y me encargué de la capa de acceso a datos y la configuración de la base de datos."

**[Abrir: `config/DatabaseConnection.java`]**

"Empecemos con DatabaseConnection. Este archivo está en la carpeta config."

**[Señalar líneas 10-19]**

"En las líneas 10 a 19 está el método getConnection. Este método lee las propiedades de conexión desde un archivo externo llamado db.properties."

**[Señalar línea 13]**

"En la línea 13, abre el archivo db.properties que está en la carpeta config."

**[Señalar líneas 15-17]**

"En las líneas 15 a 17, lee la URL, usuario y contraseña del archivo de propiedades."

**[Señalar línea 18]**

"Y en la línea 18, retorna una conexión usando DriverManager.getConnection."

**[Abrir: `dao/GenericDao.java`]**

"Ahora veamos la interfaz GenericDao. Este archivo está en la carpeta dao."

**[Señalar líneas 8-12]**

"De la línea 8 a la 12 están los métodos CRUD estándar: crear, leer, leerTodos, actualizar y eliminar."

**[Señalar líneas 15-19]**

"Y de la línea 15 a la 19 están los mismos métodos pero que aceptan una Connection externa como parámetro. Estos métodos permiten que los DAOs participen en transacciones."

**[Abrir: `dao/PropiedadDao.java`]**

"Ahora veamos la implementación concreta. Este es PropiedadDao.java."

**[Señalar líneas 10-12]**

"En las líneas 10 a 12, el método crear sin Connection llama al método crear con Connection, pasándole null."

**[Señalar líneas 14-20]**

"En las líneas 14 a 20, si no recibí una conexión externa, creo una nueva. Si recibí una, uso esa."

**[Señalar líneas 22-23]**

"En las líneas 22 a 23 está la consulta SQL usando PreparedStatement. Esto previene SQL injection."

**[Señalar líneas 25-30]**

"De la línea 25 a la 30, establezco los parámetros del PreparedStatement usando los métodos set correspondientes."

**[Señalar líneas 32-37]**

"En las líneas 32 a 37, ejecuto la consulta y obtengo el ID generado automáticamente."

**[Abrir MySQL Workbench]**

"Ahora veamos cómo está estructurada la base de datos."

**[Mostrar la base de datos tfi_prog2 en el panel izquierdo]**

"Esta es la base de datos tfi_prog2 que creamos."

**[Expandir Tables y mostrar las dos tablas]**

"Tiene dos tablas: propiedad y escrituranotarial."

**[Clic derecho en propiedad → Table Inspector o Show Table Data]**

"La tabla propiedad tiene las columnas: id, eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad."

**[Clic derecho en escrituranotarial → Table Inspector]**

"La tabla escrituranotarial tiene: id, eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, y lo más importante..."

**[Señalar la columna propiedad_id]**

"...propiedad_id, que es la clave foránea hacia la tabla propiedad."

**[Abrir: `sql/create_db.sql`]**

"Veamos el script de creación. Este archivo está en la carpeta sql."

**[Señalar líneas 9-17]**

"De la línea 9 a la 17 está la creación de la tabla propiedad. No tiene ninguna clave foránea."

**[Señalar líneas 21-33]**

"De la línea 21 a la 33 está la creación de la tabla escrituranotarial."

**[Señalar línea 30]**

"En la línea 30 está la columna propiedad_id de tipo BIGINT NOT NULL."

**[Señalar líneas 31-32]**

"Y en las líneas 31 y 32 están los constraints: uno de clave foránea que referencia a propiedad.id con ON DELETE CASCADE, y otro UNIQUE que garantiza que cada propiedad tenga como máximo una escritura."

"Este constraint UNIQUE es clave para implementar la relación 1→1 a nivel de base de datos."

---

### 🎤 PARTE 4: EXPLICACIÓN DE SERVICES Y TRANSACCIONES (Gonza)
**Duración:** 2-3 minutos  
**Pantalla:** IDE mostrando Services

#### PASOS A SEGUIR:

1. **[Abrir: `service/PropiedadService.java`]**

2. **[Hacer scroll hasta el método insertar]**

#### SCRIPT DE GONZA:

"Hola, soy [Nombre de Gonza] y desarrollé la capa de servicios y el menú de consola."

**[Abrir: `service/PropiedadService.java`]**

"Este es PropiedadService.java, está en la carpeta service."

**[Señalar líneas 15-18]**

"En las líneas 15 a 18, el constructor inicializa los DAOs que va a usar."

**[Hacer scroll hasta el método insertar - línea 21]**

"Ahora veamos el método insertar, que es donde se manejan las transacciones."

**[Señalar línea 22]**

"En la línea 22, primero valido la propiedad llamando al método validarPropiedad."

**[Señalar líneas 24-27]**

"En las líneas 24 a 27, verifico que no exista otra propiedad con el mismo padrón catastral. Esto garantiza la unicidad."

**[Señalar línea 30]**

"En la línea 30, obtengo una conexión llamando a DatabaseConnection.getConnection()."

**[Señalar línea 31]**

"En la línea 31, inicio una transacción con setAutoCommit(false). Esto significa que todas las operaciones que haga después formarán parte de una sola transacción."

**[Señalar línea 34]**

"En la línea 34, creo la propiedad usando el DAO y le paso la conexión compartida."

**[Señalar líneas 36-37]**

"En las líneas 36 a 37, si la propiedad tiene una escritura asociada, entro en este bloque."

**[Señalar líneas 40-43]**

"En las líneas 40 a 43, verifico que la propiedad no tenga ya una escritura. Esto es la regla 1→1: no puede haber más de una escritura por propiedad."

**[Señalar líneas 45-46]**

"En las líneas 45 a 46, valido la escritura."

**[Señalar líneas 48-52]**

"En las líneas 48 a 52, verifico que el número de escritura sea único si se proporcionó."

**[Señalar líneas 55-56]**

"En las líneas 55 a 56, asocio la escritura con la propiedad estableciendo el propiedadId, y luego la creo usando el DAO."

**[Señalar línea 61]**

"En la línea 61, si todo salió bien, hago commit() para confirmar todos los cambios en la base de datos."

**[Señalar líneas 62-66]**

"En las líneas 62 a 66, si ocurrió cualquier error, entro al bloque catch y hago rollback() para deshacer todos los cambios."

**[Señalar líneas 67-72]**

"En las líneas 67 a 72, en el bloque finally, siempre restablezco autoCommit a true y cierro la conexión, incluso si hubo un error."

"Esta estructura garantiza que si algo falla, no queden datos inconsistentes en la base de datos."

---

### 🎤 PARTE 5: DEMOSTRACIÓN DEL MENÚ Y FUNCIONALIDADES (Gonza)
**Duración:** 3-4 minutos  
**Pantalla:** Terminal/PowerShell ejecutando la aplicación

#### PASOS A SEGUIR:

1. **[Abrir PowerShell o CMD]**
   - Navegar a: `cd C:\Users\lg606\Desktop\TFI-PROGRAMACION2`

2. **[Ejecutar la aplicación]**
   - Comando: `java -cp ".;lib\mysql-connector-j-9.5.0.jar" main.AppMenu`
   - O doble clic en `ejecutar.bat`

#### SCRIPT DE GONZA:

"Ahora voy a demostrar cómo funciona la aplicación en ejecución."

**[Mostrar PowerShell/CMD con el comando]**

"Primero ejecuto la aplicación desde la línea de comandos."

**[Mostrar el menú principal apareciendo]**

"Este es el menú principal. Tiene tres opciones principales: gestión de propiedades, gestión de escrituras notariales, y búsquedas."

**[Escribir: 1 y presionar Enter]**

"Voy a la gestión de propiedades."

**[Mostrar el submenú]**

"Este submenú tiene opciones para crear, listar, buscar, actualizar y eliminar propiedades."

**[Escribir: 2 y presionar Enter]**

"Primero, listo las propiedades existentes."

**[Mostrar el listado]**

"Como pueden ver, hay 3 propiedades de prueba que cargamos. Cada una muestra su ID, padrón catastral, dirección, superficie, destino y antigüedad."

**[Presionar Enter para continuar]**

**[Escribir: 3 y presionar Enter]**

"Ahora busco una propiedad por ID."

**[Escribir: 1 y presionar Enter]**

"Busco la propiedad con ID 1."

**[Mostrar los detalles completos]**

"Como pueden ver, muestra todos los detalles de la propiedad, y abajo muestra la escritura notarial asociada si tiene una. Esta propiedad tiene una escritura asociada con número ESC-001-2024."

**[Presionar Enter, escribir: 0 para volver al menú principal]**

**[Escribir: 3 y presionar Enter]**

"Ahora pruebo las búsquedas."

**[Escribir: 1 y presionar Enter]**

"Busco por padrón catastral."

**[Escribir: PC-001-2024 y presionar Enter]**

"Busco la propiedad con padrón PC-001-2024."

**[Mostrar los resultados]**

"Encuentra la propiedad correctamente y muestra todos sus detalles, incluyendo la escritura asociada."

**[Presionar Enter, escribir: 0 dos veces para volver al menú principal]**

**[Escribir: 1, luego 1]**

"Ahora voy a crear una nueva propiedad para demostrar el flujo completo."

**[Mostrar las preguntas del menú una por una]**

"El sistema me pide el padrón catastral. Escribo: PC-004-2024"

"Ahora la dirección. Escribo: Av. Santa Fe 2000, CABA"

"La superficie en m². Escribo: 120.50"

"El destino. Escribo: RES"

"La antigüedad. Escribo: 7"

"Y pregunta si quiero crear una escritura asociada. Escribo: S"

**[Mostrar las preguntas de la escritura]**

"Ahora me pide los datos de la escritura."

"Número de escritura: ESC-004-2024"

"Fecha: 2024-01-20"

"Notaría: Notaría Pública N° 4"

"Tomo: T-2024"

"Folio: F-004"

"Observaciones: (presiono Enter para dejarlo vacío)"

**[Mostrar el mensaje de éxito]**

"¡Perfecto! Muestra que la propiedad se creó exitosamente, y la escritura notarial asociada también se creó correctamente."

"Esto demuestra que la relación 1→1 funciona correctamente y que la transacción se ejecutó de forma atómica: o se crean ambas entidades o no se crea ninguna. Si hubiera ocurrido un error en cualquier momento, el rollback habría deshecho todos los cambios."

**[Escribir: 0 para volver al menú de propiedades, luego 2 para listar]**

"Si listo las propiedades de nuevo, puedo ver la nueva propiedad creada."

**[Mostrar el listado actualizado]**

"Ahora hay 4 propiedades en total, incluyendo la que acabamos de crear con su escritura asociada."

---

### 🎤 PARTE 6: DEMO DE ROLLBACK (Gonza)
**Duración:** 2 minutos  
**Pantalla:** IDE modificando código y luego ejecutando

#### PASOS A SEGUIR:

1. **[Abrir: `service/PropiedadService.java` en el IDE]**

2. **[Ir a la línea 56 (después de crear la escritura, antes del commit)]**

3. **[Agregar código temporal]**

4. **[Recompilar]**

5. **[Ejecutar y mostrar el error]**

6. **[Verificar en MySQL Workbench que no se creó nada]**

7. **[Quitar el código temporal]**

#### SCRIPT DE GONZA:

"Ahora voy a demostrar cómo funciona el rollback ante un error."

**[Abrir: `service/PropiedadService.java`]**

"Voy a modificar temporalmente el código para simular un error."

**[Ir a la línea 55-56, dentro del bloque if que valida nroEscritura]**

"Voy a agregar una línea que lance una excepción justo después de buscar si existe una escritura con el mismo número, pero antes de crear la escritura."

**[Buscar estas líneas (alrededor de línea 55-56):**
```java
if (escritura.getNroEscritura() != null && !escritura.getNroEscritura().trim().isEmpty()) {
    EscrituraNotarial escrituraConMismoNumero = escrituraDao.buscarPorNumero(escritura.getNroEscritura(), conn);
```
**]**

**[Agregar DESPUÉS de la línea que busca escrituraConMismoNumero, y COMENTAR las líneas siguientes:**
```java
// Simulación de error para demo de rollback
throw new RuntimeException("Error simulado para demostrar rollback");

// Código comentado para la demo (no se ejecuta por el throw anterior)
// if (escrituraConMismoNumero != null) {
//     throw new IllegalArgumentException("Ya existe una escritura con el número: " + escritura.getNroEscritura());
// }
```
**]**

**IMPORTANTE:** Comentar las líneas del `if (escrituraConMismoNumero != null)` porque después del `throw` son código inalcanzable y causarán error de compilación.

"Agregué una excepción que se lanzará siempre que intentemos crear una propiedad con escritura."

**[Guardar el archivo]**

"Guardo el archivo."

**[Abrir PowerShell]**

"Ahora recompilo la aplicación."

**[Ejecutar: `javac -cp ".;lib\mysql-connector-j-9.5.0.jar" service\PropiedadService.java`]**

"O simplemente ejecuto compilar.bat."

**[Ejecutar la aplicación de nuevo]**

"Ejecuto la aplicación."

**[Ir al menú: 1, luego 1]**

"Voy a intentar crear otra propiedad con escritura."

**[Ingresar datos de prueba:**
- Padrón: PC-005-2024
- Dirección: Calle Falsa 123
- Superficie: 100
- Destino: RES
- Antigüedad: 5
- ¿Crear escritura? S
- Nro Escritura: ESC-005-2024
- Fecha: 2024-02-01
- (resto opcional)
**]**

**[Mostrar el error cuando aparezca]**

"Como pueden ver, se lanzó la excepción que simulamos. El sistema muestra el mensaje de error."

**[Abrir MySQL Workbench]**

"Ahora voy a verificar en la base de datos que NO se creó ni la propiedad ni la escritura."

**[Ejecutar: SELECT COUNT(*) FROM propiedad WHERE eliminado = FALSE]**

"Voy a contar las propiedades activas."

**[Mostrar el resultado]**

"El conteo sigue siendo 4, no 5. Esto significa que aunque se intentó crear la propiedad y la escritura, al ocurrir el error antes del commit, toda la transacción se revirtió mediante rollback."

**[Ejecutar: SELECT * FROM propiedad WHERE padronCatastral = 'PC-005-2024']**

"Y si busco la propiedad que intenté crear, no existe."

**[Mostrar que no hay resultados]**

"No hay resultados. Esto demuestra que el rollback funcionó correctamente: no quedó ningún registro inconsistente en la base de datos."

**[Volver al IDE]**

"Ahora voy a quitar este código de simulación para dejar la aplicación funcionando correctamente."

**[Eliminar las líneas que agregamos]**

"Elimino las líneas de simulación."

**[Guardar y recompilar]**

"Guardo y recompilo para dejar todo funcionando."

---

### 🎤 PARTE 7: CIERRE (Todos)
**Duración:** 1 minuto  
**Pantalla:** Cámara o resumen del proyecto

#### SCRIPT (cada uno dice una frase):

**[Mostrar cámara o pantalla con resumen]**

**Leonel:** "En resumen, implementamos una aplicación completa con arquitectura por capas, relación 1→1 unidireccional, transacciones con commit y rollback, y un menú funcional."

**Fede:** "El proyecto cumple con todos los requerimientos del TFI: uso de PreparedStatement, DAOs que aceptan Connection externa, y una estructura de base de datos que garantiza la integridad referencial."

**Gonza:** "Las validaciones y reglas de negocio están implementadas, incluyendo la garantía de que cada propiedad tenga como máximo una escritura asociada."

**[Cuarto integrante]:** "El proyecto está completo, funcional y listo para ser evaluado."

**Todos juntos:** "Gracias por ver nuestro video. ¡Hasta luego!"

---

## 📋 CHECKLIST ANTES DE GRABAR

### Para Leonel (grabación de pantalla):
- [ ] Tener el proyecto abierto en el IDE en: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2`
- [ ] Tener MySQL Workbench abierto y conectado
- [ ] Tener la aplicación compilada (ejecutar `compilar.bat` antes)
- [ ] Tener PowerShell/CMD abierto en la carpeta del proyecto
- [ ] Preparar datos de ejemplo para las demostraciones
- [ ] Configurar cámara para la presentación inicial (2 minutos)
- [ ] Verificar que la pantalla se vea bien (tamaño de fuente 100-125%)
- [ ] Tener estos archivos listos para abrir:
  - `entities/Propiedad.java`
  - `entities/EscrituraNotarial.java`
  - `config/DatabaseConnection.java`
  - `dao/GenericDao.java`
  - `dao/PropiedadDao.java`
  - `service/PropiedadService.java`
  - `sql/create_db.sql`

### Para Fede (voz):
- [ ] Revisar el código de estos archivos antes de grabar:
  - `config/DatabaseConnection.java`
  - `dao/GenericDao.java`
  - `dao/PropiedadDao.java`
  - `dao/EscrituraNotarialDao.java`
  - `sql/create_db.sql`
- [ ] Tener MySQL Workbench abierto para mostrar la estructura de BD
- [ ] Practicar la explicación de los métodos más importantes

### Para Gonza (voz):
- [ ] Revisar el código de estos archivos antes de grabar:
  - `service/GenericService.java`
  - `service/PropiedadService.java`
  - `service/EscrituraNotarialService.java`
  - `main/AppMenu.java`
- [ ] Preparar la demo de rollback (saber exactamente qué líneas modificar)
- [ ] Tener datos de prueba preparados para las demostraciones
- [ ] Practicar el flujo del menú

---

## 🎥 CONSEJOS PARA LA GRABACIÓN

1. **Calidad de audio:**
   - Usar un micrófono decente (no el del celular si es posible)
   - Grabar en un lugar silencioso
   - Probar el audio antes de grabar

2. **Velocidad:**
   - Hablar claro y pausado
   - No hablar muy rápido
   - Hacer pausas naturales

3. **Pantalla:**
   - Usar zoom al 100% o 125% para que el código se vea bien
   - Asegurarse de que los números de línea sean visibles
   - No hacer scroll muy rápido

4. **Errores:**
   - Si cometen un error, pueden pausar y continuar después
   - Pueden hacer múltiples tomas y elegir la mejor
   - Es recomendable hacer una grabación de prueba primero

5. **Edición:**
   - Pueden editar después para agregar transiciones, textos, etc.
   - Coordinar quién va a editar el video final

---

## ⏱️ DISTRIBUCIÓN DE TIEMPOS

- **Introducción:** 2 min
- **Entidades:** 2-3 min
- **DAO y BD:** 2-3 min
- **Services:** 2-3 min
- **Demo menú:** 3-4 min
- **Demo rollback:** 2 min
- **Cierre:** 1 min
- **Total: 14-16 minutos** (dentro del rango de 10-15 min, con un poco de margen)

---

## 📁 ARCHIVOS QUE DEBES TENER ABIERTOS/ACCESIBLES

### Para la grabación de pantalla (Leonel):
1. `entities/Propiedad.java`
2. `entities/EscrituraNotarial.java`
3. `config/DatabaseConnection.java`
4. `dao/GenericDao.java`
5. `dao/PropiedadDao.java`
6. `service/PropiedadService.java`
7. `sql/create_db.sql`
8. MySQL Workbench (conectado a la BD)
9. PowerShell/CMD (para ejecutar la aplicación)

### Rutas completas de archivos importantes:
- Entidades:
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\entities\Propiedad.java`
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\entities\EscrituraNotarial.java`

- Config:
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\config\DatabaseConnection.java`
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\config\db.properties`

- DAO:
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\dao\GenericDao.java`
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\dao\PropiedadDao.java`
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\dao\EscrituraNotarialDao.java`

- Service:
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\service\GenericService.java`
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\service\PropiedadService.java`
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\service\EscrituraNotarialService.java`

- Main:
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\main\AppMenu.java`

- SQL:
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\sql\create_db.sql`
  - `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\sql\seed_data.sql`

---

## 🎬 ORDEN DE GRABACIÓN SUGERIDO

1. **Leonel graba la pantalla completa** (toda la sesión, ~15 minutos)
   - Abre todos los archivos necesarios
   - Sigue el guion paso a paso
   - Ejecuta la aplicación cuando corresponda
   - Hace la demo de rollback

2. **Fede graba su voz** (solo su parte, ~3 minutos)
   - Lee su script mientras ve el video de Leonel
   - Puede hacer múltiples tomas

3. **Gonza graba su voz** (solo su parte, ~9 minutos)
   - Lee su script mientras ve el video de Leonel
   - Puede hacer múltiples tomas

4. **Cuarto integrante graba su voz** (solo su parte del cierre, ~10 segundos)

5. **Edición final:**
   - Sincronizar las voces con la pantalla
   - Agregar presentación con rostros visibles al inicio
   - Agregar transiciones si quieren
   - Verificar que todo esté sincronizado

---

**¡Éxitos con la grabación! 🎬**
