-- create_db.sql
-- Script para crear la base de datos y las tablas para el dominio Empleado → Legajo

CREATE DATABASE IF NOT EXISTS tfi_prog2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tfi_prog2;

-- Tabla empleado (A)
CREATE TABLE IF NOT EXISTS empleado (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  nombre VARCHAR(80) NOT NULL,
  apellido VARCHAR(80) NOT NULL,
  dni VARCHAR(15) NOT NULL UNIQUE,
  email VARCHAR(120),
  fechaIngreso DATE,
  area VARCHAR(50)
) ENGINE=InnoDB;

-- Tabla legajo (B) con FK única a empleado.id para garantizar 1->1
CREATE TABLE IF NOT EXISTS legajo (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  eliminado BOOLEAN NOT NULL DEFAULT FALSE,
  nroLegajo VARCHAR(20) NOT NULL UNIQUE,
  categoria VARCHAR(30),
  estado ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  fechaAlta DATE,
  observaciones VARCHAR(255),
  a_id BIGINT NOT NULL,
  CONSTRAINT fk_legajo_a FOREIGN KEY (a_id) REFERENCES empleado(id) ON DELETE CASCADE,
  CONSTRAINT uq_legajo_a UNIQUE (a_id)
) ENGINE=InnoDB;

-- Índices útiles
CREATE INDEX idx_empleado_dni ON empleado(dni);
CREATE INDEX idx_legajo_nro ON legajo(nroLegajo);
