# TFI - Programación 2
## Aplicación Java con relación 1→1 unidireccional + DAO + MySQL

### Descripción del Dominio

Este proyecto implementa un sistema de gestión de **Propiedades** y sus **Escrituras Notariales** asociadas, modelando una relación unidireccional 1→1 donde:
- **Propiedad (A)**: Representa una propiedad inmobiliaria con información catastral, dirección, superficie, destino y antigüedad.
- **EscrituraNotarial (B)**: Representa la escritura notarial asociada a una propiedad, conteniendo número de escritura, fecha, notaría, tomo, folio y observaciones.

La relación es **unidireccional**: solo `Propiedad` contiene una referencia a `EscrituraNotarial`, garantizando que cada propiedad puede tener como máximo una escritura asociada (1→1).

---

## Requisitos

### Software Necesario
- **Java**: Versión 21 (recomendado) o superior
- **MySQL**: Versión 8.0 o superior
- **MySQL Connector/J**: Driver JDBC para MySQL (incluido como dependencia)
- **IDE**: Cualquier IDE compatible con Java (IntelliJ IDEA, Eclipse, VS Code, etc.)

### Dependencias
El proyecto requiere el driver JDBC de MySQL. Si usas Maven, agrega a tu `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>
```

O descarga el JAR desde: https://dev.mysql.com/downloads/connector/j/

---

## Configuración de la Base de Datos

### 1. Crear el archivo de propiedades

Copia el archivo de ejemplo y completa con tus credenciales:
```bash
cp config/db.properties.example config/db.properties
```

Edita `config/db.properties`:
```properties
jdbc.url=jdbc:mysql://localhost:3306/tfi_prog2
jdbc.user=root
jdbc.password=tu_password
```

### 2. Crear la base de datos y tablas

Ejecuta el script SQL de creación:
```bash
mysql -u root -p < sql/create_db.sql
```

O desde MySQL Workbench/consola:
```sql
SOURCE sql/create_db.sql;
```

### 3. Cargar datos de prueba (opcional)

```bash
mysql -u root -p < sql/seed_data.sql
```

O desde MySQL:
```sql
SOURCE sql/seed_data.sql;
```

---

## Estructura del Proyecto

```
TFI-PROGRAMACION2/
├── config/
│   ├── DatabaseConnection.java      # Conexión a la BD
│   ├── db.properties.example        # Ejemplo de configuración
│   └── db.properties                # Configuración real (crear manualmente)
├── entities/
│   ├── Propiedad.java               # Entidad A (principal)
│   └── EscrituraNotarial.java       # Entidad B (secundaria)
├── dao/
│   ├── GenericDao.java               # Interfaz genérica DAO
│   ├── PropiedadDao.java             # DAO concreto para Propiedad
│   └── EscrituraNotarialDao.java    # DAO concreto para EscrituraNotarial
├── service/
│   ├── GenericService.java           # Interfaz genérica Service
│   ├── PropiedadService.java         # Service con transacciones para Propiedad
│   └── EscrituraNotarialService.java # Service con transacciones para EscrituraNotarial
├── main/
│   └── AppMenu.java                  # Menú de consola principal
└── sql/
    ├── create_db.sql                 # Script de creación de BD
    └── seed_data.sql                 # Datos de prueba
```

---

## Compilación y Ejecución

### Compilación Manual

```bash
# Compilar todas las clases
javac -cp ".:mysql-connector-j-8.0.33.jar" config/*.java entities/*.java dao/*.java service/*.java main/*.java

# O si tienes las clases en un directorio específico:
javac -cp ".:mysql-connector-j-8.0.33.jar" -d bin config/*.java entities/*.java dao/*.java service/*.java main/*.java
```

### Ejecución

```bash
# Ejecutar la aplicación
java -cp ".:mysql-connector-j-8.0.33.jar" main.AppMenu

# O si compilaste a un directorio:
java -cp ".:mysql-connector-j-8.0.33.jar:bin" main.AppMenu
```

### Con Maven

```bash
# Compilar
mvn compile

# Ejecutar
mvn exec:java -Dexec.mainClass="main.AppMenu"
```

---

## Uso de la Aplicación

Al ejecutar `AppMenu`, se mostrará un menú interactivo con las siguientes opciones:

### Menú Principal
1. **Gestión de Propiedades**: CRUD completo de propiedades
2. **Gestión de Escrituras Notariales**: CRUD completo de escrituras
3. **Búsquedas**: Búsqueda por padrón catastral y número de escritura
0. **Salir**

### Funcionalidades

#### Propiedades
- ✅ Crear propiedad (con opción de crear escritura asociada)
- ✅ Listar todas las propiedades
- ✅ Buscar por ID
- ✅ Buscar por padrón catastral
- ✅ Actualizar propiedad
- ✅ Eliminar lógicamente (baja lógica)

#### Escrituras Notariales
- ✅ Crear escritura (asociada a una propiedad)
- ✅ Listar todas las escrituras
- ✅ Buscar por ID
- ✅ Buscar por número de escritura
- ✅ Actualizar escritura
- ✅ Eliminar lógicamente (baja lógica)

### Validaciones Implementadas

- **Propiedad**:
  - Padrón catastral obligatorio y único
  - Dirección obligatoria
  - Superficie en m² obligatoria y mayor a cero
  - Antigüedad no negativa
  - Regla 1→1: no permite más de una escritura por propiedad

- **EscrituraNotarial**:
  - Fecha obligatoria
  - Número de escritura único (si se proporciona)
  - Regla 1→1: no permite crear otra escritura para una propiedad que ya tiene una

---

## Características Técnicas

### Arquitectura por Capas

1. **Entities**: Modelo de dominio con relación 1→1 unidireccional
2. **DAO**: Acceso a datos con JDBC y PreparedStatement
3. **Service**: Lógica de negocio y orquestación de transacciones
4. **Main**: Interfaz de usuario (consola)

### Transacciones

Los Services implementan transacciones completas:
- `setAutoCommit(false)` al inicio
- `commit()` si todas las operaciones son exitosas
- `rollback()` ante cualquier error
- Restablecimiento de `autoCommit(true)` y cierre de recursos

### Baja Lógica

Todas las entidades implementan el campo `eliminado` (BOOLEAN) para realizar bajas lógicas en lugar de eliminaciones físicas.

### Relación 1→1 en Base de Datos

La relación se implementa mediante:
- Clave foránea única (`propiedad_id`) en la tabla `escrituraNotarial`
- Constraint `UNIQUE` en `propiedad_id` para garantizar la unicidad
- `ON DELETE CASCADE` para mantener integridad referencial

---

## Datos de Prueba

El script `seed_data.sql` incluye 3 propiedades de ejemplo con sus escrituras asociadas:

1. **Propiedad COM**: Av. Corrientes 1234 (150.50 m²)
2. **Propiedad RES**: Calle San Martín 567 (85.25 m²)
3. **Propiedad RES**: Av. Libertador 890 (200.00 m²)

---

## Credenciales de Prueba

Para usar los datos de prueba, asegúrate de que tu `db.properties` tenga:
- Usuario: `root` (o el usuario que uses)
- Password: Tu contraseña de MySQL
- Base de datos: `tfi_prog2`

---

## Video Demostrativo

[Enlace al video de demostración - agregar cuando esté disponible]

El video incluye:
- Presentación de los integrantes
- Demostración del flujo CRUD completo
- Explicación de la relación 1→1 funcionando
- Demostración de transacciones y rollback ante errores

---

## Integrantes

- **Leonel**: Entities (Propiedad, EscrituraNotarial)
- **Fede**: DAO (GenericDao, PropiedadDao, EscrituraNotarialDao) y DatabaseConnection
- **Gonza**: Service (GenericService, PropiedadService, EscrituraNotarialService) y AppMenu
- **[Cuarto integrante]**: [Rol asignado]

---

## Notas Adicionales

- El proyecto usa `PreparedStatement` en todas las consultas para prevenir SQL injection
- Los DAOs aceptan `Connection` externa para participar en transacciones
- El menú convierte entradas a mayúsculas donde corresponde (padrón catastral, destino, etc.)
- Manejo robusto de errores con mensajes claros al usuario

---

## Mejoras Futuras

- Implementar búsqueda avanzada con múltiples criterios
- Exportar reportes a PDF/Excel
- Interfaz gráfica (Swing/JavaFX)
- Autenticación de usuarios
- Logging con Log4j o similar

---

## Licencia

Este proyecto es parte del Trabajo Final Integrador de la materia Programación 2.
