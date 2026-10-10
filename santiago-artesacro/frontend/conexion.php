<?php
// Datos de conexión por defecto en XAMPP
$servidor = "localhost";
$usuario = "root";
$password = ""; // En XAMPP la contraseña viene vacía por defecto
$basedatos = "santiago_artesacro";

// Crear la conexión
$conexion = new mysqli($servidor, $usuario, $password, $basedatos);

// Verificar si hay errores
if ($conexion->connect_error) {
    die("Error de conexión, revisa XAMPP: " . $conexion->connect_error);
}
?>