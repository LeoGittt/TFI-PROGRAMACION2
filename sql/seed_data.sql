-- seed_data.sql
-- Datos de prueba mínimos para Propiedad y Escritura Notarial

USE tfi_prog2;

INSERT INTO propiedad (eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad)
VALUES (FALSE, '050104000800002900030', 'Av. San Martin 523', 80,80, 'COM', 10);

INSERT INTO propiedad (eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad)
VALUES (FALSE, '052541000051512785456', 'Dorrego 78', 62,10, 'RES', 5);

-- Asociar Escrituras Notarial (usar los ids resultantes; si se importan manualmente, ajustar a los ids reales)
INSERT INTO escrituraNotarial (eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id)
VALUES (FALSE, '365', 12-08-2024, 'Maitena Silva', '3', '12', 'Sin observaciones', 1);

INSERT INTO escrituraNotarial (eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id)
VALUES (FALSE, '456', 04-05-2007, 'Emiliano Soto', '6', '5', 'Sin observaciones', 2);