-- create_db.sql
-- Script para crear la base de datos y las tablas para el dominio Propiedad → EscrituraNotarial

CREATE DATABASE IF NOT EXISTS tfi_prog2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tfi_prog2;

-- Tabla escritura_notarial (B)
CREATE TABLE IF NOT EXISTS escritura_notarial (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  nro_escritura VARCHAR(30) UNIQUE,
  fecha DATE NOT NULL,
  notaria VARCHAR(120),
  tomo VARCHAR(10),
  folio VARCHAR(10),
  observaciones VARCHAR(255),
  -- propiedad_id se elimina en esta alternativa: la relación 1->1 se modela desde propiedad.escritura_id
  -- Si se prefiere PK compartida, este diseño debería adaptarse.
) ENGINE=InnoDB;

-- Tabla propiedad (A) con FK a escritura_notarial (escritura_id) y constraint UNIQUE
CREATE TABLE IF NOT EXISTS propiedad (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  padron_catastral VARCHAR(30) NOT NULL UNIQUE,
  direccion VARCHAR(150) NOT NULL,
  superficie_m2 DECIMAL(10,2) NOT NULL,
  destino ENUM('RES','COM'),
  antiguedad INT,
  escritura_id BIGINT,
  CONSTRAINT fk_propiedad_escritura FOREIGN KEY (escritura_id) REFERENCES escritura_notarial(id) ON DELETE SET NULL,
  CONSTRAINT uq_propiedad_escritura UNIQUE (escritura_id)
) ENGINE=InnoDB;

-- Tabla empleado y legajo (para soportar seed_data.sql que incluye estos datos)
CREATE TABLE IF NOT EXISTS empleado (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  nombre VARCHAR(80) NOT NULL,
  apellido VARCHAR(80) NOT NULL,
  dni VARCHAR(15) NOT NULL UNIQUE,
  email VARCHAR(120),
  fecha_ingreso DATE,
  area VARCHAR(50)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS legajo (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  nro_legajo VARCHAR(20) UNIQUE,
  categoria VARCHAR(30),
  estado ENUM('ACTIVO','INACTIVO') NOT NULL,
  fecha_alta DATE,
  observaciones VARCHAR(255),
  empleado_id BIGINT,
  CONSTRAINT fk_legajo_empleado FOREIGN KEY (empleado_id) REFERENCES empleado(id) ON DELETE CASCADE,
  CONSTRAINT uq_legajo_empleado UNIQUE (empleado_id)
) ENGINE=InnoDB;

-- Índices útiles
CREATE INDEX idx_propiedad_padron ON propiedad(padron_catastral);
CREATE INDEX idx_escritura_nro ON escritura_notarial(nro_escritura);
