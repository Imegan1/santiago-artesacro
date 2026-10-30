<%--
  Vista del inicio de sesión. Recibe del LoginServlet: error y correo.
  El parámetro ?registro=ok muestra el aviso de registro exitoso.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Texto"%>
<%
    String error = (String) request.getAttribute("error");
    String correoPrevio = (String) request.getAttribute("correo");
    if (correoPrevio == null) {
        correoPrevio = "";
    }
    boolean recienRegistrado = "ok".equals(request.getParameter("registro"));
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ingresar | Santiago Artesacro</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/cabecera.jspf" %>

<main class="contenedor angosto">
  <h1>Ingresar</h1>
  <% if (recienRegistrado) { %><p class="aviso ok">Registro exitoso. Ya puedes ingresar.</p><% } %>
  <% if (error != null) { %><p class="aviso error"><%= Texto.esc(error) %></p><% } %>

  <form class="formulario" method="post" action="<%= ctx %>/login">
    <label>Correo
      <input type="email" name="correo" value="<%= Texto.esc(correoPrevio) %>" required autocomplete="email">
    </label>
    <label>Contraseña
      <input type="password" name="contrasena" required autocomplete="current-password">
    </label>
    <button type="submit" class="boton">Ingresar</button>
  </form>
  <p>¿No tienes cuenta? <a class="enlace" href="<%= ctx %>/registro">Regístrate aquí</a></p>
</main>
</body>
</html>
