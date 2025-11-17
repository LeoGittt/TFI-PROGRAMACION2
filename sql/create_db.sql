
-- create_db.sql
-- Script para crear la base de datos y las tablas para el dominio Propiedad → EscrituraNotarial

CREATE DATABASE IF NOT EXISTS tfi_prog2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tfi_prog2;

-- Tabla propiedad (A)
CREATE TABLE IF NOT EXISTS propiedad (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  padronCatastral VARCHAR(30) NOT NULL UNIQUE,
  direccion VARCHAR(150) NOT NULL,
  superficieM2 DECIMAL(10,2) NOT NULL,
  destino ENUM('RES','COM'),
  antiguedad INT
) ENGINE=InnoDB;

-- Tabla escrituraNotarial (B) con FK única a propiedad.id para garantizar 1->1
CREATE TABLE IF NOT EXISTS escrituraNotarial (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  nroEscritura VARCHAR(30) UNIQUE,
  fecha DATE NOT NULL,
  notaria VARCHAR(120),
  tomo VARCHAR(10),
  folio VARCHAR(10),
  observaciones VARCHAR(255),
  propiedad_id BIGINT NOT NULL,
  CONSTRAINT fk_escritura_propiedad FOREIGN KEY (propiedad_id) REFERENCES propiedad(id) ON DELETE CASCADE,
  CONSTRAINT uq_escritura_propiedad UNIQUE (propiedad_id)
) ENGINE=InnoDB;

-- Índices útiles
CREATE INDEX idx_propiedad_padron ON propiedad(padronCatastral);
CREATE INDEX idx_escritura_nro ON escrituraNotarial(nroEscritura);
