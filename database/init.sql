-- ==========================================================
--  SANTIAGO ARTESACRO - Base de datos (versión corregida)
--  Motor: MySQL / MariaDB (XAMPP o hosting)
--  Cómo usarlo: phpMyAdmin -> pestaña "Importar" -> elegir este archivo
-- ==========================================================

CREATE DATABASE IF NOT EXISTS santiago_artesacro
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE santiago_artesacro;

-- ----------------------------------------------------------
-- 1. USUARIOS (clientes y administradores)
--    Los campos coinciden con el formulario de registro.
--    "contrasena" guarda el HASH, nunca la clave real.
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    nombres          VARCHAR(100) NOT NULL,
    apellidos        VARCHAR(100) NOT NULL,
    cedula           VARCHAR(20)  NOT NULL UNIQUE,
    fecha_nacimiento DATE         NOT NULL,
    correo           VARCHAR(120) NOT NULL UNIQUE,
    telefono         VARCHAR(20)  NULL,
    contrasena       VARCHAR(255) NOT NULL,
    rol              ENUM('cliente','administrador') NOT NULL DEFAULT 'cliente',
    fecha_registro   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ----------------------------------------------------------
-- 2. CATEGORIAS del catálogo
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS categorias (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(80) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- ----------------------------------------------------------
-- 3. PRODUCTOS (piezas del catálogo)
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS productos (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id  INT NULL,
    nombre        VARCHAR(120) NOT NULL,
    descripcion   TEXT NULL,
    precio        DECIMAL(10,2) NULL,          -- NULL = "consultar precio"
    medidas       VARCHAR(80)  NULL,
    material      VARCHAR(80)  NULL,
    imagen        VARCHAR(255) NULL,           -- ruta, ej: img/productos/cristo.jpg
    disponible    BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creado  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ----------------------------------------------------------
-- 4. PEDIDOS / ENCARGOS
--    Si producto_id es NULL, el cliente pidió algo personalizado
--    y lo describe en "descripcion_encargo".
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS pedidos (
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id           INT NOT NULL,
    producto_id          INT NULL,
    tipo                 ENUM('catalogo','personalizado') NOT NULL DEFAULT 'catalogo',
    descripcion_encargo  TEXT NULL,
    cantidad             INT NOT NULL DEFAULT 1,
    fecha_deseada        DATE NULL,
    estado               ENUM('recibido','en_proceso','listo','entregado','cancelado')
                         NOT NULL DEFAULT 'recibido',
    notas_admin          TEXT NULL,
    fecha_pedido         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id)  REFERENCES usuarios(id)  ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ----------------------------------------------------------
-- 5. DATOS DE EJEMPLO (cámbialos por los productos reales)
-- ----------------------------------------------------------
INSERT IGNORE INTO categorias (id, nombre) VALUES
    (1, 'Imágenes religiosas'),
    (2, 'Crucifijos'),
    (3, 'Rosarios y detalles'),
    (4, 'Restauración');

INSERT INTO productos (categoria_id, nombre, descripcion, precio, medidas, material, imagen) VALUES
    (1, 'Virgen del Carmen (ejemplo)',  'Talla artesanal en madera, acabado a mano.', 350000, '40 cm', 'Madera de cedro', NULL),
    (2, 'Crucifijo de pared (ejemplo)', 'Crucifijo tallado, listo para colgar.',      120000, '30 cm', 'Madera',          NULL),
    (3, 'Rosario artesanal (ejemplo)',  'Rosario hecho a mano con cuentas de madera.', 35000, '50 cm', 'Madera y metal',   NULL),
    (4, 'Restauración de imagen',       'Servicio de restauración. Se cotiza según la pieza.', NULL, NULL, NULL, NULL);

-- NOTA: el usuario administrador NO se crea aquí con contraseña en texto.
-- Para crear uno: registrarse normalmente en la página y luego ejecutar:
--   UPDATE usuarios SET rol = 'administrador' WHERE correo = 'tu_correo@ejemplo.com';
