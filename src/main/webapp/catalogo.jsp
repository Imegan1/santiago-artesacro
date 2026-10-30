<%--
  Vista del catálogo de obras (HU-002).
  Recibe del CatalogoServlet: productos, categorias y catSeleccionada.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, modelo.Producto, modelo.Categoria, util.Texto"%>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    Integer catSel = (Integer) request.getAttribute("catSeleccionada");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Catálogo de arte sacro artesanal hecho a mano: imágenes religiosas, crucifijos, rosarios y restauración.">
<title>Catálogo | Santiago Artesacro</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/cabecera.jspf" %>

<main class="contenedor">
  <h1>Catálogo</h1>
  <p class="sub">Piezas de arte sacro hechas a mano.</p>

  <%-- Filtros por categoría --%>
  <div class="filtros">
    <a class="chip <%= catSel == null ? "activo" : "" %>" href="<%= ctx %>/catalogo">Todas</a>
    <% if (categorias != null) { for (Categoria c : categorias) { %>
      <a class="chip <%= (catSel != null && catSel == c.getId()) ? "activo" : "" %>"
         href="<%= ctx %>/catalogo?cat=<%= c.getId() %>"><%= Texto.esc(c.getNombre()) %></a>
    <% } } %>
  </div>

  <% if (productos == null || productos.isEmpty()) { %>
    <p class="vacio">Por ahora no hay piezas en esta categoría.</p>
  <% } else { %>
    <section class="rejilla">
      <% for (Producto p : productos) { %>
        <article class="tarjeta">
          <%-- Foto de la obra o un marcador si aún no tiene imagen --%>
          <% if (p.getImagen() != null && !p.getImagen().isEmpty()) { %>
            <img src="<%= ctx %>/<%= Texto.esc(p.getImagen()) %>" alt="<%= Texto.esc(p.getNombre()) %>" loading="lazy">
          <% } else { %>
            <div class="sin-foto">Foto próximamente</div>
          <% } %>
          <div class="cuerpo">
            <span class="cat"><%= Texto.esc(p.getCategoria()) %></span>
            <h2><%= Texto.esc(p.getNombre()) %></h2>
            <p class="desc"><%= Texto.esc(p.getDescripcion()) %></p>
            <ul class="datos">
              <% if (p.getMedidas() != null) { %><li>Medidas: <%= Texto.esc(p.getMedidas()) %></li><% } %>
              <% if (p.getMaterial() != null) { %><li>Material: <%= Texto.esc(p.getMaterial()) %></li><% } %>
            </ul>
            <div class="pie">
              <span class="precio"><%= p.getPrecioTexto() %></span>
              <% if (p.isDisponible()) { %>
                <a class="boton chico" href="<%= ctx %>/encargo?producto=<%= p.getId() %>">Encargar</a>
              <% } else { %><span class="agotado">Agotado</span><% } %>
            </div>
          </div>
        </article>
      <% } %>
    </section>
  <% } %>
</main>

<footer class="pie-pagina">© Santiago Artesacro · Arte sacro hecho a mano</footer>

</body>
</html>
