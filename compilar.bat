@echo off
REM Script para compilar el proyecto TFI
REM Ajusta la ruta del JAR según donde lo hayas puesto

echo ========================================
echo Compilando proyecto TFI...
echo ========================================

REM OPCION 1: Si pusiste el JAR en una carpeta "lib" dentro del proyecto
javac -cp ".;lib\mysql-connector-j-9.5.0.jar" config\*.java entities\*.java dao\*.java service\*.java main\*.java

REM OPCION 2: Si pusiste el JAR en otra ubicacion, descomenta y ajusta la ruta:
REM javac -cp ".;C:\mysql-connector\mysql-connector-j-9.5.0.jar" config\*.java entities\*.java dao\*.java service\*.java main\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Compilacion exitosa!
    echo ========================================
) else (
    echo.
    echo ========================================
    echo ERROR en la compilacion!
    echo Verifica:
    echo 1. Que Java este instalado (java -version)
    echo 2. Que el JAR de MySQL este en la ruta correcta
    echo 3. Que estes en la carpeta raiz del proyecto
    echo ========================================
    pause
    exit /b 1
)

pause

