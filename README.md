# TFI-PROGRAMACION2

Proyecto final integrador — Dominio: Propiedad → EscrituraNotarial (1→1 unidireccional)

Resumen rápido
- Lenguaje: Java
- Persistencia: JDBC + MySQL
- Patrones: DAO + Service

Instrucciones para levantar el proyecto localmente

1) Configurar conexión a DB
	- Copiar `config/db.properties.example` a `config/db.properties` y completar las credenciales:

```
jdbc.url=jdbc:mysql://localhost:3306/tfi_prog2
jdbc.user=root
jdbc.password=tu_password
```

2) Crear la base de datos y datos de prueba
	- Ejecutar los scripts SQL (asegúrate de tener `mysql` en PATH):

```powershell
mysql -u TU_USUARIO -p < 'C:\Users\usuario\Desktop\TFI-PROGRAMACION2\sql\create_db.sql'
mysql -u TU_USUARIO -p < 'C:\Users\usuario\Desktop\TFI-PROGRAMACION2\sql\seed_data.sql'
```

3) Compilar y ejecutar

Opción A — Compilar con javac (manual)

```powershell
Set-Location -Path 'C:\Users\usuario\Desktop\TFI-PROGRAMACION2'
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
cmd /c "javac -d out @sources.txt"
java -cp "out;lib/*" main.TestConexion
java -cp "out;lib/*" main.AppMenu
```

Opción B — Compilar con Maven (recomendado)

Si preferís que Maven gestione la dependencia del driver y la compilación, hay un `pom.xml` en la raíz. Ejecutá:

```powershell
mvn -v
mvn clean package
# Ejecutar TestConexion usando el classpath generado por Maven (o usar el exec plugin)
java -cp "target/classes;lib/*" main.TestConexion

# Generar JAR ejecutable 'fat' con dependencias (maven-shade)
mvn clean package
# El plugin Shade adjunta un JAR con sufijo '-shaded', por ejemplo:
# target/tfi-programacion2-1.0-SNAPSHOT-shaded.jar
java -jar target/tfi-programacion2-1.0-SNAPSHOT-shaded.jar
```

Nota: si no estás familiarizado con el comando anterior para obtener el classpath runtime, simplemente podés ejecutar el jar del conector MySQL junto con `target/classes`:

```powershell
java -cp "target/classes;lib/*" main.TestConexion
```

Ejecutar con un script (Windows)

He añadido `run-app.bat` en la raíz del repo. El script intentará ejecutar el JAR sombreado si ya existe; si no existe, ejecutará `mvn clean package` y luego intentará ejecutar el JAR generado.

```powershell
.\run-app.bat
```

Notas y diseño
- La relación 1→1 se implementó con `propiedad.escritura_id` (FK a `escritura_notarial(id)`) y una restricción UNIQUE sobre `escritura_id` para garantizar que una escritura pertenezca a una sola propiedad.
- Los DAOs (`dao/PropiedadDaoJdbc`, `dao/EscrituraNotarialDaoJdbc`) implementan `dao/GenericDao` y ofrecen sobrecargas que aceptan una `Connection` externa; los `Service` orquestan transacciones abriendo una Connection, seteando `autoCommit(false)` y pasando la misma Connection a los DAOs.

Pendientes / recomendaciones
- Completar UML, informe PDF y grabar el video de presentación.
- Añadir tests automatizados (JUnit) para operaciones transaccionales y rollback.


