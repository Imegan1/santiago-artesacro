-- 1. Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS santiago_artesacro;
USE santiago_artesacro;

-- 2. Crear la tabla para los usuarios del sistema
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(50) DEFAULT 'cliente',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Insertar un usuario de prueba (para cuando hagamos el test de ingreso)
INSERT INTO usuarios (nombre, correo, contrasena, rol) 
VALUES ('Alexander Gaviria', 'ejemplo@correo.com', 'admin123', 'administrador');