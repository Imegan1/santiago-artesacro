<%--
  Vista del formulario de encargo (HU-003). Está dentro de WEB-INF para que
  solo se pueda abrir a través de EncargoServlet (que exige sesión iniciada).
  Recibe: productoId, productoNombre, errores y v (valores escritos).
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, util.Texto"%>
<%
    List<String> errores = (List<String>) request.getAttribute("errores");
    Map<String, String> v = (Map<String, String>) request.getAttribute("v");
    Integer productoId = (Integer) request.getAttribute("productoId");
    String productoNombre = (String) request.getAttribute("productoNombre");
    if (v == null) {
        v = new HashMap<String, String>();
    }
    String cantidad = (v.get("cantidad") == null || v.get("cantidad").isEmpty()) ? "1" : v.get("cantidad");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Hacer un encargo | Santiago Artesacro</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/cabecera.jspf" %>

<main class="contenedor angosto">
  <h1>Hacer un encargo</h1>
  <% if (productoId != null) { %>
    <p class="sub">Pieza elegida: <strong><%= Texto.esc(productoNombre) %></strong></p>
  <% } else { %>
    <p class="sub">Cuéntanos qué pieza quieres que Santiago haga para ti.</p>
  <% } %>

  <% if (errores != null && !errores.isEmpty()) { %>
    <div class="aviso error"><ul>
      <% for (String e : errores) { %><li><%= Texto.esc(e) %></li><% } %>
    </ul></div>
  <% } %>

  <form class="formulario" method="post" action="<%= ctx %>/encargo">
    <%-- Si viene de una pieza del catálogo, se envía su identificador --%>
    <% if (productoId != null) { %><input type="hidden" name="producto" value="<%= productoId %>"><% } %>

    <label><%= productoId != null ? "Notas para Santiago (opcional)" : "Describe la pieza que quieres" %>
      <textarea name="descripcion" maxlength="1000" <%= productoId == null ? "required" : "" %>
        placeholder="<%= productoId == null ? "Ej: Una imagen de San Judas en madera, de 40 cm, con acabado oscuro" : "Ej: Me gustaría un acabado más claro" %>"><%= Texto.esc(v.getOrDefault("descripcion", "")) %></textarea>
    </label>
    <label>Cantidad
      <input type="number" name="cantidad" min="1" max="50" value="<%= Texto.esc(cantidad) %>" required>
    </label>
    <label>Fecha en que la necesitas (opcional)
      <input type="date" name="fecha_deseada" value="<%= Texto.esc(v.getOrDefault("fecha_deseada", "")) %>">
    </label>
    <button type="submit" class="boton">Enviar encargo</button>
  </form>
  <p><a class="enlace" href="<%= ctx %>/catalogo">Volver al catálogo</a></p>
</main>
</body>
</html>
