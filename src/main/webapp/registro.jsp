<%--
  Vista del registro de usuarios. Recibe del RegistroServlet: errores y v
  (valores escritos, para no obligar al usuario a escribirlos de nuevo).
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, util.Texto"%>
<%
    List<String> errores = (List<String>) request.getAttribute("errores");
    Map<String, String> v = (Map<String, String>) request.getAttribute("v");
    if (v == null) {
        v = new HashMap<String, String>();
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Crear cuenta | Santiago Artesacro</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/cabecera.jspf" %>

<main class="contenedor angosto">
  <h1>Crear cuenta</h1>
  <p class="sub">Con tu cuenta podrás hacer encargos y ver su estado.</p>

  <% if (errores != null && !errores.isEmpty()) { %>
    <div class="aviso error"><ul>
      <% for (String e : errores) { %><li><%= Texto.esc(e) %></li><% } %>
    </ul></div>
  <% } %>

  <form class="formulario" method="post" action="<%= ctx %>/registro">
    <label>Nombres
      <input type="text" name="nombres" maxlength="100" required autocomplete="given-name"
             value="<%= Texto.esc(v.getOrDefault("nombres", "")) %>">
    </label>
    <label>Apellidos
      <input type="text" name="apellidos" maxlength="100" required autocomplete="family-name"
             value="<%= Texto.esc(v.getOrDefault("apellidos", "")) %>">
    </label>
    <label>Cédula
      <input type="text" name="cedula" inputmode="numeric" maxlength="15" required
             value="<%= Texto.esc(v.getOrDefault("cedula", "")) %>">
    </label>
    <label>Fecha de nacimiento
      <input type="date" name="fecha_nacimiento" required autocomplete="bday"
             value="<%= Texto.esc(v.getOrDefault("fecha_nacimiento", "")) %>">
    </label>
    <label>Correo
      <input type="email" name="correo" maxlength="120" required autocomplete="email"
             value="<%= Texto.esc(v.getOrDefault("correo", "")) %>">
    </label>
    <label>Teléfono o WhatsApp (opcional)
      <input type="tel" name="telefono" maxlength="15" autocomplete="tel"
             value="<%= Texto.esc(v.getOrDefault("telefono", "")) %>">
    </label>
    <label>Contraseña (mínimo 8 caracteres)
      <input type="password" name="contrasena" minlength="8" required autocomplete="new-password">
    </label>
    <label>Repite la contraseña
      <input type="password" name="confirmar" minlength="8" required autocomplete="new-password">
    </label>
    <button type="submit" class="boton">Crear cuenta</button>
  </form>
  <p>¿Ya tienes cuenta? <a class="enlace" href="<%= ctx %>/login">Ingresa aquí</a></p>
</main>
</body>
</html>
