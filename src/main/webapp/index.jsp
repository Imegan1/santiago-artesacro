<%--
  Página de inicio: presenta al artista y dirige al catálogo y a los encargos.
  Nota: reemplazar NUMERO_DE_SANTIAGO por el WhatsApp real (con 57, sin espacios).
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Santiago Artesacro: arte sacro hecho a mano. Imágenes religiosas, crucifijos, rosarios y restauración. Haz tu encargo en línea.">
<title>Santiago Artesacro | Arte sacro hecho a mano</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
<%@ include file="/WEB-INF/cabecera.jspf" %>

<section class="portada">
  <div class="portada-texto">
    <h1>Arte sacro hecho a mano</h1>
    <p>Imágenes religiosas, crucifijos, rosarios y restauración de piezas, elaborados con dedicación por Santiago.</p>
    <div class="portada-botones">
      <a class="boton" href="<%= ctx %>/catalogo">Ver el catálogo</a>
      <a class="boton claro" href="<%= ctx %>/encargo">Hacer un encargo</a>
    </div>
  </div>
</section>

<main class="contenedor">
  <section class="bloques">
    <article class="bloque">
      <h2>Piezas del catálogo</h2>
      <p>Mira las obras disponibles con sus medidas, materiales y precios.</p>
      <a class="enlace" href="<%= ctx %>/catalogo">Explorar →</a>
    </article>
    <article class="bloque">
      <h2>Encargos personalizados</h2>
      <p>¿Tienes una imagen o una medida en mente? Cuéntanos y te lo hacemos a tu gusto.</p>
      <a class="enlace" href="<%= ctx %>/encargo">Pedir una pieza →</a>
    </article>
    <article class="bloque">
      <h2>Restauración</h2>
      <p>Devolvemos la vida a imágenes y tallas con cuidado. Se cotiza según la pieza.</p>
      <a class="enlace" href="<%= ctx %>/encargo">Consultar →</a>
    </article>
  </section>

  <section class="contacto">
    <h2>Contacto</h2>
    <p>¿Dudas antes de encargar? Escríbele directamente a Santiago.</p>
    <a class="boton" href="https://wa.me/57NUMERO_DE_SANTIAGO" target="_blank" rel="noopener">Escribir por WhatsApp</a>
  </section>
</main>

<footer class="pie-pagina">© Santiago Artesacro · Arte sacro hecho a mano</footer>
</body>
</html>
