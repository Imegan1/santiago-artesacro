<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuarios - Santiago Arte Sacro</title>
</head>
<body>
    <h2>Formulario de Registro (Método POST)</h2>
    <!-- El action apunta a nuestro Servlet -->
    <form action="UsuarioServlet" method="POST">
        <label for="nombres">Nombres:</label><br>
        <input type="text" id="nombres" name="nombres" required><br><br>
        
        <label for="correo">Correo Electrónico:</label><br>
        <input type="email" id="correo" name="correo" required><br><br>
        
        <button type="submit">Guardar Usuario</button>
    </form>

    <br>
    <hr>
    
    <h2>Consultar Listado (Método GET)</h2>
    <a href="UsuarioServlet">Ver lista de usuarios desde el Servlet</a>
</body>
</html>