@echo off
REM Script para crear la base de datos
REM NOTA: Si tienes MySQL Workbench, es mas facil usarlo directamente
REM Abre MySQL Workbench y ejecuta los archivos sql/create_db.sql y sql/seed_data.sql

echo ========================================
echo Creando base de datos tfi_prog2...
echo ========================================
echo.
echo NOTA: Si tienes MySQL Workbench, es mas facil:
echo 1. Abrir MySQL Workbench
echo 2. File -^> Open SQL Script
echo 3. Abrir sql\create_db.sql y ejecutarlo
echo 4. Abrir sql\seed_data.sql y ejecutarlo
echo.
echo ¿Deseas continuar desde aqui? (S/N)
set /p continuar=
if /i NOT "%continuar%"=="S" (
    echo.
    echo Usa MySQL Workbench para crear la base de datos.
    pause
    exit /b 0
)

echo.
REM Crear base de datos y tablas
echo Ejecutando create_db.sql...
mysql -u root -p < sql\create_db.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Base de datos creada exitosamente!
    echo.
    echo ¿Deseas cargar datos de prueba? (S/N)
    set /p respuesta=
    if /i "%respuesta%"=="S" (
        echo.
        echo Cargando datos de prueba...
        mysql -u root -p < sql\seed_data.sql
        echo.
        echo Datos de prueba cargados!
    )
) else (
    echo.
    echo ERROR al crear la base de datos!
    echo.
    echo RECOMENDACION: Usa MySQL Workbench:
    echo 1. Abrir MySQL Workbench
    echo 2. File -^> Open SQL Script
    echo 3. Abrir sql\create_db.sql y ejecutarlo (boton Execute)
    echo 4. Abrir sql\seed_data.sql y ejecutarlo
    echo.
    echo O verifica:
    echo 1. Que MySQL este instalado y corriendo
    echo 2. Que el usuario y contraseña sean correctos
    echo 3. Que tengas permisos para crear bases de datos
)

echo.
pause

