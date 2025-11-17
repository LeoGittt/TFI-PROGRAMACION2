# Guía de Instalación - Proyecto TFI

## 📋 Requisitos Previos

Necesitas instalar 3 cosas:
1. Java JDK (versión 21 o superior)
2. MySQL (versión 8.0 o superior)
3. MySQL Connector/J (driver JDBC)

---

## 1️⃣ INSTALAR JAVA JDK

### Opción A: Descarga Manual (Recomendado)

1. **Ir a la página de descarga:**
   - https://www.oracle.com/java/technologies/downloads/
   - O directamente: https://adoptium.net/ (OpenJDK - gratuito)

2. **Descargar Java 21:**
   - En Oracle: Buscar "Java 21" → Windows → x64 Installer
   - En Adoptium: Seleccionar "Temurin 21" → Windows → x64 → .msi

3. **Instalar:**
   - Ejecutar el instalador
   - Seguir las instrucciones (Next, Next, Install)
   - ✅ Marcar la opción "Add to PATH" si aparece

4. **Verificar instalación:**
   - Abrir PowerShell o CMD
   - Escribir: `java -version`
   - Debe mostrar algo como: `openjdk version "21.0.x"` o `java version "21.x.x"`

### Opción B: Con Chocolatey (si lo tienes instalado)

```powershell
choco install openjdk21
```

---

## 2️⃣ INSTALAR MYSQL

### Opción A: MySQL Installer (Recomendado para Windows)

1. **Descargar MySQL Installer:**
   - Ir a: https://dev.mysql.com/downloads/installer/
   - Descargar "MySQL Installer for Windows" (archivo .msi, ~400MB)

2. **Instalar:**
   - Ejecutar el instalador
   - Seleccionar "Developer Default" o "Server only"
   - Seguir el asistente:
     - ✅ Configurar puerto: 3306 (por defecto)
     - ✅ Crear usuario root con contraseña (¡ANOTA LA CONTRASEÑA!)
     - ✅ Configurar como servicio de Windows

3. **Verificar instalación:**
   - Abrir PowerShell
   - Escribir: `mysql --version`
   - Debe mostrar la versión de MySQL

4. **Probar conexión:**
   ```powershell
   mysql -u root -p
   ```
   - Ingresar la contraseña que configuraste
   - Si funciona, escribir `exit;` para salir

### Opción B: XAMPP (Más fácil, incluye MySQL)

1. **Descargar XAMPP:**
   - https://www.apachefriends.org/
   - Descargar versión para Windows

2. **Instalar:**
   - Ejecutar instalador
   - Instalar solo MySQL (o todo XAMPP)
   - Por defecto, usuario: `root`, contraseña: (vacía) o puedes configurarla

3. **Iniciar MySQL:**
   - Abrir XAMPP Control Panel
   - Hacer clic en "Start" junto a MySQL

---

## 3️⃣ DESCARGAR MYSQL CONNECTOR/J

1. **Ir a la página de descarga:**
   - https://dev.mysql.com/downloads/connector/j/
   - O directamente: https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-8.0.33.zip

2. **Descargar:**
   - Seleccionar "Platform Independent"
   - Descargar el archivo ZIP

3. **Extraer el JAR:**
   - Extraer el ZIP
   - Buscar el archivo: `mysql-connector-j-8.0.33.jar` (o versión similar)
   - Copiar este archivo a una carpeta fácil de recordar, por ejemplo:
     - `C:\mysql-connector\mysql-connector-j-8.0.33.jar`
     - O dentro de tu proyecto: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\lib\mysql-connector-j-8.0.33.jar`

---

## 4️⃣ CONFIGURAR EL PROYECTO

### 4.1. Crear archivo de configuración de base de datos

1. **Ir a la carpeta del proyecto:**
   ```
   C:\Users\lg606\Desktop\TFI-PROGRAMACION2\config\
   ```

2. **Copiar el archivo de ejemplo:**
   - Copiar `db.properties.example`
   - Pegar en la misma carpeta
   - Renombrar a `db.properties` (sin .example)

3. **Editar `db.properties`:**
   - Abrir con Bloc de notas o cualquier editor
   - Modificar con tus datos:
   ```properties
   jdbc.url=jdbc:mysql://localhost:3306/tfi_prog2
   jdbc.user=root
   jdbc.password=TU_CONTRASEÑA_DE_MYSQL
   ```
   - Guardar el archivo

### 4.2. Crear la base de datos

#### OPCIÓN A: Usando MySQL Workbench (RECOMENDADO - Más fácil)

1. **Abrir MySQL Workbench:**
   - Buscar "MySQL Workbench" en el menú de Windows
   - Conectarte a tu servidor MySQL (usuario root y tu contraseña)

2. **Abrir los archivos SQL:**
   - En MySQL Workbench: File → Open SQL Script
   - Navegar a: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\sql\create_db.sql`
   - Abrir el archivo

3. **Ejecutar el script:**
   - Verás el contenido del script en el editor
   - Hacer clic en el botón "Execute" (⚡) o presionar `Ctrl+Shift+Enter`
   - Esperar a que termine (debe mostrar "Success")

4. **Cargar datos de prueba (opcional):**
   - File → Open SQL Script
   - Abrir: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2\sql\seed_data.sql`
   - Ejecutar con el botón "Execute" (⚡)

5. **Verificar que se creó:**
   - En el panel izquierdo, hacer clic en el ícono de "Refresh" (🔄) junto a "SCHEMAS"
   - Deberías ver la base de datos `tfi_prog2` en la lista
   - Expandirla y ver las tablas: `propiedad` y `escrituraNotarial`

#### OPCIÓN B: Desde PowerShell/CMD (Alternativa)

1. **Abrir PowerShell en la carpeta del proyecto:**
   - Navegar a: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2`

2. **Ejecutar el script de creación:**
   ```powershell
   mysql -u root -p < sql\create_db.sql
   ```
   - Ingresar tu contraseña de MySQL cuando lo pida

3. **Cargar datos de prueba (opcional):**
   ```powershell
   mysql -u root -p < sql\seed_data.sql
   ```
   - Ingresar tu contraseña de MySQL

---

## 5️⃣ COMPILAR Y EJECUTAR

### Opción A: Desde PowerShell/CMD (Línea de comandos)

1. **Abrir PowerShell en la carpeta del proyecto:**
   ```powershell
   cd C:\Users\lg606\Desktop\TFI-PROGRAMACION2
   ```

2. **Compilar todas las clases:**
   
   **Si pusiste el JAR en una carpeta `lib` dentro del proyecto:**
   ```powershell
   javac -cp ".;lib\mysql-connector-j-8.0.33.jar" config\*.java entities\*.java dao\*.java service\*.java main\*.java
   ```
   
   **Si pusiste el JAR en otra ubicación (ej: C:\mysql-connector\):**
   ```powershell
   javac -cp ".;C:\mysql-connector\mysql-connector-j-8.0.33.jar" config\*.java entities\*.java dao\*.java service\*.java main\*.java
   ```

3. **Ejecutar la aplicación:**
   
   **Si pusiste el JAR en `lib`:**
   ```powershell
   java -cp ".;lib\mysql-connector-j-8.0.33.jar" main.AppMenu
   ```
   
   **Si pusiste el JAR en otra ubicación:**
   ```powershell
   java -cp ".;C:\mysql-connector\mysql-connector-j-8.0.33.jar" main.AppMenu
   ```

### Opción B: Con un IDE (IntelliJ IDEA, Eclipse, VS Code)

#### IntelliJ IDEA:

1. **Abrir el proyecto:**
   - File → Open → Seleccionar la carpeta `TFI-PROGRAMACION2`

2. **Agregar el JAR como librería:**
   - File → Project Structure (Ctrl+Alt+Shift+S)
   - Libraries → + → Java
   - Seleccionar el archivo `mysql-connector-j-8.0.33.jar`
   - Aplicar

3. **Compilar:**
   - Build → Build Project (Ctrl+F9)

4. **Ejecutar:**
   - Click derecho en `main/AppMenu.java` → Run 'AppMenu.main()'

#### Eclipse:

1. **Importar proyecto:**
   - File → Import → General → Existing Projects into Workspace
   - Seleccionar la carpeta del proyecto

2. **Agregar JAR:**
   - Click derecho en el proyecto → Properties
   - Java Build Path → Libraries → Add External JARs
   - Seleccionar `mysql-connector-j-8.0.33.jar`

3. **Ejecutar:**
   - Click derecho en `AppMenu.java` → Run As → Java Application

#### VS Code:

1. **Instalar extensiones:**
   - Extension Pack for Java (Microsoft)

2. **Abrir la carpeta del proyecto**

3. **Agregar JAR al classpath:**
   - Crear carpeta `lib` en el proyecto
   - Copiar `mysql-connector-j-8.0.33.jar` ahí
   - VS Code lo detectará automáticamente

4. **Ejecutar:**
   - Abrir `main/AppMenu.java`
   - Click en "Run" arriba del método `main()`

---

## 🔧 SOLUCIÓN DE PROBLEMAS COMUNES

### Error: "java no se reconoce como comando"
- **Solución:** Java no está en el PATH
  - Buscar "Variables de entorno" en Windows
  - Agregar la ruta de Java al PATH (ej: `C:\Program Files\Java\jdk-21\bin`)

### Error: "mysql no se reconoce como comando"
- **Solución:** MySQL no está en el PATH
  - Agregar al PATH: `C:\Program Files\MySQL\MySQL Server 8.0\bin`
  - O usar la ruta completa: `"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"`

### Error: "Access denied for user 'root'@'localhost'"
- **Solución:** Contraseña incorrecta en `db.properties`
  - Verificar que la contraseña en `config/db.properties` sea correcta
  - Probar conectarse manualmente: `mysql -u root -p`

### Error: "Unknown database 'tfi_prog2'"
- **Solución:** La base de datos no existe
  - Ejecutar: `mysql -u root -p < sql\create_db.sql`

### Error: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
- **Solución:** El JAR no está en el classpath
  - Verificar la ruta del JAR en el comando `-cp`
  - Asegurarse de que el archivo existe

### Error al compilar: "package does not exist"
- **Solución:** Compilar desde la raíz del proyecto
  - Asegurarse de estar en: `C:\Users\lg606\Desktop\TFI-PROGRAMACION2`
  - Usar rutas relativas como en los ejemplos

---

## ✅ VERIFICACIÓN FINAL

Para verificar que todo está instalado correctamente:

1. **Java:**
   ```powershell
   java -version
   javac -version
   ```

2. **MySQL:**
   ```powershell
   mysql --version
   mysql -u root -p
   ```

3. **Base de datos creada:**
   ```sql
   SHOW DATABASES;
   USE tfi_prog2;
   SHOW TABLES;
   ```

4. **Aplicación:**
   - Si todo está bien, al ejecutar deberías ver el menú principal

---

## 📝 RESUMEN RÁPIDO

1. ✅ Instalar Java JDK 21
2. ✅ Instalar MySQL 8.0
3. ✅ Descargar MySQL Connector/J
4. ✅ Crear `config/db.properties` con tus credenciales
5. ✅ Ejecutar `sql/create_db.sql` para crear la BD
6. ✅ Compilar: `javac -cp ".;ruta\al\jar" ...`
7. ✅ Ejecutar: `java -cp ".;ruta\al\jar" main.AppMenu`

---

**¿Necesitas ayuda con algún paso específico?** Revisa la sección de solución de problemas o pregunta directamente.

