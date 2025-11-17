# TFI - Programación 2

## Sistema de Gestión de Propiedades y Escrituras Notariales

Aplicación Java que implementa un sistema de gestión de **Propiedades** y sus **Escrituras Notariales** asociadas, modelando una relación unidireccional 1→1 donde cada propiedad puede tener como máximo una escritura asociada.

---

## 📋 Requisitos

### Software Necesario
- **Java JDK**: Versión 17 o superior (recomendado 21)
- **MySQL**: Versión 8.0 o superior
- **MySQL Connector/J**: Driver JDBC incluido en `lib/` (versión 9.5.0)

---

## 🚀 Instalación Rápida

### 1. Configurar Base de Datos

1. **Crear archivo de configuración:**
   - Copiar `config/db.properties.example` a `config/db.properties`
   - Editar `config/db.properties` con tus credenciales:
   ```properties
   jdbc.url=jdbc:mysql://localhost:3306/tfi_prog2
   jdbc.user=root
   jdbc.password=TU_CONTRASEÑA
   ```

2. **Crear la base de datos:**
   - Ejecutar `crear_bd.bat` (Windows)
   - O manualmente: `mysql -u root -p < sql\create_db.sql`

3. **Cargar datos de prueba (opcional):**
   ```sql
   mysql -u root -p < sql\seed_data.sql
   ```

### 2. Compilar y Ejecutar

**Opción A: Usando scripts batch (Windows)**
```bash
# Compilar
compilar.bat

# Ejecutar
ejecutar.bat
```

**Opción B: Manualmente**
```bash
# Compilar
javac -cp ".;lib\mysql-connector-j-9.5.0.jar" config\*.java entities\*.java dao\*.java service\*.java main\*.java

# Ejecutar
java -cp ".;lib\mysql-connector-j-9.5.0.jar" main.AppMenu
```

**Opción C: Con Maven**
```bash
# Compilar
mvn compile

# Ejecutar
mvn exec:java -Dexec.mainClass="main.AppMenu"
```

---

## 📁 Estructura del Proyecto

```
TFI-PROGRAMACION2/
├── config/
│   ├── DatabaseConnection.java      # Gestión de conexión a BD
│   └── db.properties                 # Configuración (crear desde .example)
├── entities/
│   ├── Propiedad.java                # Entidad principal (A)
│   └── EscrituraNotarial.java        # Entidad secundaria (B)
├── dao/
│   ├── GenericDao.java               # Interfaz genérica DAO
│   ├── PropiedadDao.java             # Interfaz DAO Propiedad
│   ├── PropiedadDaoJdbc.java         # Implementación JDBC
│   ├── EscrituraNotarialDao.java     # Interfaz DAO Escritura
│   └── EscrituraNotarialDaoJdbc.java # Implementación JDBC
├── service/
│   ├── GenericService.java           # Interfaz genérica Service
│   ├── PropiedadService.java         # Lógica de negocio Propiedad
│   └── EscrituraNotarialService.java # Lógica de negocio Escritura
├── main/
│   └── AppMenu.java                  # Menú de consola principal
├── sql/
│   ├── create_db.sql                 # Script creación BD y tablas
│   └── seed_data.sql                 # Datos de prueba
└── lib/
    └── mysql-connector-j-9.5.0.jar   # Driver JDBC MySQL
```

---

## 🎯 Funcionalidades

### Menú Principal
1. **Gestión de Propiedades**: CRUD completo
2. **Gestión de Escrituras Notariales**: CRUD completo
3. **Búsquedas**: Por padrón catastral y número de escritura
0. **Salir**

### Propiedades
- ✅ Crear propiedad (con opción de crear escritura asociada)
- ✅ Listar todas las propiedades
- ✅ Buscar por ID
- ✅ Buscar por padrón catastral
- ✅ Actualizar propiedad
- ✅ Eliminar lógicamente (baja lógica)

### Escrituras Notariales
- ✅ Crear escritura (asociada a una propiedad)
- ✅ Listar todas las escrituras
- ✅ Buscar por ID
- ✅ Buscar por número de escritura
- ✅ Actualizar escritura
- ✅ Eliminar lógicamente (baja lógica)

---

## 🔒 Validaciones Implementadas

### Propiedad
- Padrón catastral obligatorio y único
- Dirección obligatoria
- Superficie en m² obligatoria y mayor a cero
- Antigüedad no negativa
- **Regla 1→1**: No permite más de una escritura por propiedad

### EscrituraNotarial
- Fecha obligatoria
- Número de escritura único (si se proporciona)
- **Regla 1→1**: No permite crear otra escritura para una propiedad que ya tiene una

---

## 🏗️ Arquitectura

### Capas
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
- Clave foránea única (`propiedad_id`) en la tabla `escrituraNotarial`
- Constraint `UNIQUE` en `propiedad_id` para garantizar la unicidad
- `ON DELETE CASCADE` para mantener integridad referencial

---

## 🛠️ Solución de Problemas

### Error: "java no se reconoce como comando"
- **Solución**: Java no está en el PATH
  - Agregar la ruta de Java al PATH (ej: `C:\Program Files\Java\jdk-21\bin`)

### Error: "mysql no se reconoce como comando"
- **Solución**: MySQL no está en el PATH
  - Agregar: `C:\Program Files\MySQL\MySQL Server 8.0\bin`
  - O usar la ruta completa en los comandos

### Error: "Access denied for user 'root'@'localhost'"
- **Solución**: Contraseña incorrecta en `db.properties`
  - Verificar que la contraseña en `config/db.properties` sea correcta

### Error: "Unknown database 'tfi_prog2'"
- **Solución**: La base de datos no existe
  - Ejecutar: `mysql -u root -p < sql\create_db.sql`

### Error: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
- **Solución**: El JAR no está en el classpath
  - Verificar que `lib/mysql-connector-j-9.5.0.jar` exista
  - Verificar la ruta del JAR en los comandos de compilación/ejecución

---

## 📝 Características Técnicas

- **PreparedStatement**: Todas las consultas usan PreparedStatement para prevenir SQL injection
- **DAOs con Connection externa**: Los DAOs aceptan `Connection` externa para participar en transacciones
- **Manejo robusto de errores**: Mensajes claros al usuario
- **Validaciones de negocio**: Implementadas en la capa Service

---

## 👥 Integrantes

- **Leonel**: Entities (Propiedad, EscrituraNotarial)
- **Federico**: DAO (GenericDao, PropiedadDao, EscrituraNotarialDao) y DatabaseConnection
- **Gonzalo**: Service (GenericService, PropiedadService, EscrituraNotarialService) y AppMenu

---

## 📄 Licencia

Este proyecto es parte del Trabajo Final Integrador de la materia Programación 2.
