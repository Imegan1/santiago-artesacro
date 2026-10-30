<%--
  Vista "Mis pedidos" (HU-003): lista los encargos del cliente y su estado.
  Recibe del MisPedidosServlet: pedidos. El parámetro ?ok=1 muestra la confirmación.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, modelo.Pedido, util.Texto"%>
<%
    List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
    boolean enviado = "1".equals(request.getParameter("ok"));
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Mis pedidos | Santiago Artesacro</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/cabecera.jspf" %>

<main class="contenedor">
  <h1>Mis pedidos</h1>
  <% if (enviado) { %><p class="aviso ok">¡Encargo enviado! Santiago lo revisará y te contactará.</p><% } %>

  <% if (pedidos == null || pedidos.isEmpty()) { %>
    <p class="vacio">Todavía no tienes pedidos. <a class="enlace" href="<%= ctx %>/catalogo">Ver el catálogo</a></p>
  <% } else { %>
    <div class="tabla-scroll">
    <table class="tabla">
      <thead>
        <tr><th>N.º</th><th>Pieza</th><th>Detalle</th><th>Cant.</th><th>Fecha deseada</th><th>Estado</th><th>Pedido el</th></tr>
      </thead>
      <tbody>
      <% for (Pedido p : pedidos) { %>
        <tr>
          <td>#<%= p.getId() %></td>
          <td><%= p.getProducto() != null ? Texto.esc(p.getProducto()) : "Encargo personalizado" %></td>
          <td><%= Texto.esc(p.getDescripcion()) %></td>
          <td><%= p.getCantidad() %></td>
          <td><%= p.getFechaDeseada().isEmpty() ? "—" : Texto.esc(p.getFechaDeseada()) %></td>
          <td><span class="estado <%= Texto.esc(p.getEstado()) %>"><%= p.getEstadoTexto() %></span></td>
          <td><%= Texto.esc(p.getFechaPedido()) %></td>
        </tr>
      <% } %>
      </tbody>
    </table>
    </div>
  <% } %>
</main>
</body>
</html>
