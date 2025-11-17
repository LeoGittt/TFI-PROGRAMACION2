@echo off
REM Script para ejecutar el proyecto TFI
REM Ajusta la ruta del JAR según donde lo hayas puesto

echo ========================================
echo Ejecutando aplicacion TFI...
echo ========================================
echo.

REM OPCION 1: Si pusiste el JAR en una carpeta "lib" dentro del proyecto
java -cp ".;lib\mysql-connector-j-9.5.0.jar" main.AppMenu

REM OPCION 2: Si pusiste el JAR en otra ubicacion, descomenta y ajusta la ruta:
REM java -cp ".;C:\mysql-connector\mysql-connector-j-9.5.0.jar" main.AppMenu

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ========================================
    echo ERROR al ejecutar!
    echo Verifica:
    echo 1. Que hayas compilado primero (ejecutar compilar.bat)
    echo 2. Que MySQL este corriendo
    echo 3. Que config/db.properties tenga las credenciales correctas
    echo 4. Que la base de datos este creada
    echo ========================================
    pause
    exit /b 1
)

pause

