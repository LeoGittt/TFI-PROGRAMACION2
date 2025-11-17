-- seed_data.sql
-- Datos de prueba mínimos combinados para la base de datos

USE tfi_prog2;

-- Bloque: Propiedad y Escritura Notarial (desde la versión de la rama)
-- Bloque: Propiedad y Escritura Notarial
INSERT INTO propiedad (eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad)
VALUES (FALSE, '050104000800002900030', 'Av. San Martin 523', 80, 'COM', 10);

INSERT INTO propiedad (eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad)
VALUES (FALSE, '052541000051512785456', 'Dorrego 78', 62, 'RES', 5);

-- Asociar Escrituras Notarial (usar los ids resultantes; si se importan manualmente, ajustar a los ids reales)
INSERT INTO escritura_notarial (eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones)
VALUES (FALSE, '365', '2024-08-12', 'Maitena Silva', '3', '12', 'Sin observaciones');

INSERT INTO escritura_notarial (eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones)
VALUES (FALSE, '456', '2007-05-04', 'Emiliano Soto', '6', '5', 'Sin observaciones');

-- Luego asociar escrituras a propiedades (ejemplo: asignar escritura 1 a propiedad 1)
UPDATE propiedad SET escritura_id = 1 WHERE id = 1;
UPDATE propiedad SET escritura_id = 2 WHERE id = 2;

-- Bloque: Empleado y Legajo (para pruebas mínimas)
INSERT INTO empleado (eliminado, nombre, apellido, dni, email, fecha_ingreso, area)
VALUES (FALSE, 'Juan', 'Pérez', '12345678', 'juan.perez@example.com', '2020-03-15', 'Desarrollo');

INSERT INTO empleado (eliminado, nombre, apellido, dni, email, fecha_ingreso, area)
VALUES (FALSE, 'María', 'Gómez', '87654321', 'maria.gomez@example.com', '2021-07-01', 'Soporte');

-- Asociar legajos a empleados
INSERT INTO legajo (eliminado, nro_legajo, categoria, estado, fecha_alta, observaciones, empleado_id)
VALUES (FALSE, 'LJ-0001', 'Senior', 'ACTIVO', '2020-03-16', 'Legajo inicial', 1);

INSERT INTO legajo (eliminado, nro_legajo, categoria, estado, fecha_alta, observaciones, empleado_id)
VALUES (FALSE, 'LJ-0002', 'Junior', 'ACTIVO', '2021-07-02', 'Legajo inicial', 2);
