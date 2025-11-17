@echo off
REM Script para compilar (si es necesario) y ejecutar el JAR sombreado
SETLOCAL

REM Ir a la carpeta del script (raíz del repo)
cd /d %~dp0

IF EXIST target\tfi-programacion2-1.0-SNAPSHOT-shaded.jar (
  echo Ejecutando JAR sombreado...
  java -jar target\tfi-programacion2-1.0-SNAPSHOT-shaded.jar
  goto :EOF
)

echo No se encontró el JAR sombreado. Ejecutando 'mvn clean package' (requiere Maven)...
mvn clean package
IF %ERRORLEVEL% NEQ 0 (
  echo Maven falló. Por favor revisá los errores y volvé a intentar.
  goto :EOF
)

IF EXIST target\tfi-programacion2-1.0-SNAPSHOT-shaded.jar (
  echo Ejecutando JAR sombreado generado...
  java -jar target\tfi-programacion2-1.0-SNAPSHOT-shaded.jar
) ELSE (
  echo No se pudo generar el JAR sombreado. Revisá 'target/' para más detalles.
)

ENDLOCAL
