package controlador;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductoDao;

/**
 * Controlador del catálogo de obras (HU-002, caso de uso "Visualizar catálogo").
 * Responde a GET /catalogo y acepta el parámetro opcional "cat" para filtrar
 * por categoría. Prepara los datos y los entrega a la vista catalogo.jsp.
 *
 * @author Alexander Gaviria Cossío
 */
@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer categoria = leerCategoria(req.getParameter("cat"));

        try {
            ProductoDao dao = new ProductoDao();
            req.setAttribute("productos", dao.listar(categoria));
            req.setAttribute("categorias", dao.listarCategorias());
            req.setAttribute("catSeleccionada", categoria);
            req.getRequestDispatcher("/catalogo.jsp").forward(req, resp);
        } catch (SQLException e) {
            log("Error cargando el catalogo", e);
            resp.sendError(500, "No se pudo cargar el catalogo");
        }
    }

    /** Convierte el parámetro "cat" a número; si no es válido, no filtra. */
    private static Integer leerCategoria(String parametro) {
        if (parametro == null || parametro.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(parametro);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
