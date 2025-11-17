-- seed_data.sql
-- Datos de prueba para Propiedad → EscrituraNotarial

USE tfi_prog2;

-- Insertar propiedades
INSERT INTO propiedad (eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad)
VALUES (FALSE, 'PC-001-2024', 'Av. Corrientes 1234, CABA', 150.50, 'COM', 10);

INSERT INTO propiedad (eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad)
VALUES (FALSE, 'PC-002-2024', 'Calle San Martín 567, CABA', 85.25, 'RES', 5);

INSERT INTO propiedad (eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad)
VALUES (FALSE, 'PC-003-2024', 'Av. Libertador 890, CABA', 200.00, 'RES', 15);

-- Insertar escrituras notariales asociadas (relación 1→1)
-- Nota: Los IDs de propiedad se obtienen de los INSERT anteriores
-- Si se ejecutan en orden, los IDs serán 1, 2, 3 respectivamente

INSERT INTO escrituraNotarial (eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id)
VALUES (FALSE, 'ESC-001-2024', '2024-01-15', 'Notaría Pública N° 1', 'T-2024', 'F-001', 'Escritura inicial de la propiedad comercial', 1);

INSERT INTO escrituraNotarial (eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id)
VALUES (FALSE, 'ESC-002-2024', '2024-02-20', 'Notaría Pública N° 2', 'T-2024', 'F-002', 'Escritura de propiedad residencial', 2);

INSERT INTO escrituraNotarial (eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id)
VALUES (FALSE, 'ESC-003-2024', '2024-03-10', 'Notaría Pública N° 3', 'T-2024', 'F-003', 'Escritura de propiedad residencial con ampliación', 3);
