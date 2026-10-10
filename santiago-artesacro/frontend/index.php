<?php
// Llamamos al archivo de conexión
require 'conexion.php';
$mensaje = "";

// Si el usuario presiona el botón de INGRESAR...
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $correo = $_POST['correo'];
    $contrasena = $_POST['contrasena'];

    // Buscamos en la tabla si ese correo y contraseña coinciden
    $sql = "SELECT * FROM usuarios WHERE correo = '$correo' AND contrasena = '$contrasena'";
    $resultado = $conexion->query($sql);

    // Si encuentra 1 resultado, el usuario existe
    if ($resultado->num_rows > 0) {
        $fila = $resultado->fetch_assoc(); // Sacamos los datos del usuario
        // Mensaje limpio y profesional
        $mensaje = "<div class='exito'>Acceso concedido. Bienvenido(a) al panel de control, " . $fila['nombre'] . ".</div>";
    } else {
        $mensaje = "<div class='error'>Error: Correo o contraseña incorrectos.</div>";
    }
}
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Santiago Arte Sacro</title>
    <style>
        * { box-sizing: border-box; }
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
            background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
            display: flex; 
            justify-content: center; 
            align-items: center; 
            height: 100vh; 
            margin: 0; 
            color: #333;
        }
        .login-wrapper {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .login-container { 
            background-color: white; 
            padding: 40px; 
            border-radius: 12px; 
            box-shadow: 0 8px 20px rgba(0,0,0,0.15); 
            width: 350px; 
            text-align: center; 
        }
        .login-container h2 { 
            color: #2C3E50; 
            margin-bottom: 5px;
            font-size: 24px;
        }
        .login-container p {
            color: #7f8c8d;
            font-size: 14px;
            margin-bottom: 25px;
        }
        input { 
            width: 100%; 
            padding: 14px; 
            margin: 10px 0; 
            border: 1px solid #dcdde1; 
            border-radius: 6px; 
            background-color: #f5f6fa;
            transition: border-color 0.3s;
        }
        input:focus {
            outline: none;
            border-color: #3498db;
            background-color: #fff;
        }
        button { 
            width: 100%; 
            padding: 14px; 
            background-color: #2c3e50; 
            color: white; 
            border: none; 
            border-radius: 6px; 
            cursor: pointer; 
            font-weight: 600; 
            font-size: 15px; 
            margin-top: 20px;
            letter-spacing: 1px;
            transition: background-color 0.3s;
        }
        button:hover { 
            background-color: #1a252f; 
        }
        .exito { 
            color: #155724; 
            background-color: #d4edda; 
            padding: 12px; 
            border-radius: 6px; 
            margin-top: 20px; 
            border: 1px solid #c3e6cb;
            font-size: 14px;
        }
        .error { 
            color: #721c24; 
            background-color: #f8d7da; 
            padding: 12px; 
            border-radius: 6px; 
            margin-top: 20px; 
            border: 1px solid #f5c6cb;
            font-size: 14px;
        }
        .footer {
            margin-top: 30px;
            text-align: center;
            font-size: 12px;
            color: #7f8c8d;
        }
    </style>
</head>
<body>
    <div class="login-wrapper">
        <div class="login-container">
            <h2>Santiago Arte Sacro</h2>
            <p>Ingreso al sistema administrativo</p>
            
            <form method="POST" action="">
                <input type="email" name="correo" placeholder="Correo electrónico" required>
                <input type="password" name="contrasena" placeholder="Contraseña" required>
                <button type="submit">INICIAR SESIÓN</button>
            </form>
            
            <?php echo $mensaje; ?>
        </div>
        
        <div class="footer">
            Proyecto Académico - Ficha 3336098<br>
            Instructor: Luis Alejandro Moreno Rojas
        </div>
    </div>
</body>
</html>