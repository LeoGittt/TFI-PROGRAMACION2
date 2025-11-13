-- seed_data.sql
-- Datos de prueba mínimos para Empleado y Legajo

USE tfi_prog2;

INSERT INTO empleado (eliminado, nombre, apellido, dni, email, fechaIngreso, area)
VALUES (FALSE, 'Juan', 'Pérez', '12345678', 'juan.perez@example.com', '2020-03-15', 'Desarrollo');

INSERT INTO empleado (eliminado, nombre, apellido, dni, email, fechaIngreso, area)
VALUES (FALSE, 'María', 'Gómez', '87654321', 'maria.gomez@example.com', '2021-07-01', 'Soporte');

-- Asociar legajos (usar los ids resultantes; si se importan manualmente, ajustar a los ids reales)
INSERT INTO legajo (eliminado, nroLegajo, categoria, estado, fechaAlta, observaciones, a_id)
VALUES (FALSE, 'LJ-0001', 'Senior', 'ACTIVO', '2020-03-16', 'Legajo inicial', 1);

INSERT INTO legajo (eliminado, nroLegajo, categoria, estado, fechaAlta, observaciones, a_id)
VALUES (FALSE, 'LJ-0002', 'Junior', 'ACTIVO', '2021-07-02', 'Legajo inicial', 2);
