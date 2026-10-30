package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PedidoDao;
import modelo.Usuario;

/**
 * Controlador de encargos (HU-003: solicitar una pieza al artista).
 * GET /encargo muestra el formulario (opcionalmente con una pieza del catálogo
 * elegida con ?producto=ID); POST /encargo valida y guarda el pedido.
 * Solo pueden usarlo usuarios con sesión iniciada.
 *
 * @author Alexander Gaviria Cossío
 */
@WebServlet("/encargo")
public class EncargoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int CANTIDAD_MAXIMA = 50;
    private static final int DESCRIPCION_MAXIMA = 1000;
    private static final int DESCRIPCION_MINIMA_PERSONALIZADO = 10;

    /** Acceso a datos de pedidos (no se serializa junto con el servlet). */
    private final transient PedidoDao dao = new PedidoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (usuarioEnSesion(req) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        try {
            mostrarFormulario(req, resp, aEntero(req.getParameter("producto")),
                    new ArrayList<String>(), new HashMap<String, String>());
        } catch (SQLException e) {
            responderError(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Usuario usuario = usuarioEnSesion(req);
        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer productoId = aEntero(req.getParameter("producto"));
        String descripcion = limpiar(req.getParameter("descripcion"));
        String cantidadTexto = limpiar(req.getParameter("cantidad"));
        String fechaTexto = limpiar(req.getParameter("fecha_deseada"));

        // Valores que se devuelven al formulario si hay errores
        Map<String, String> valores = new HashMap<>();
        valores.put("descripcion", descripcion);
        valores.put("cantidad", cantidadTexto);
        valores.put("fecha_deseada", fechaTexto);

        // Validación en el servidor
        List<String> errores = new ArrayList<>();
        if (descripcion.length() > DESCRIPCION_MAXIMA) {
            errores.add("La descripción es muy larga (máximo " + DESCRIPCION_MAXIMA + " caracteres).");
        }

        int cantidad = 1;
        try {
            cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad < 1 || cantidad > CANTIDAD_MAXIMA) {
                errores.add("La cantidad debe estar entre 1 y " + CANTIDAD_MAXIMA + ".");
            }
        } catch (NumberFormatException e) {
            errores.add("La cantidad no es válida.");
        }

        LocalDate fecha = null;
        if (!fechaTexto.isEmpty()) {
            try {
                fecha = LocalDate.parse(fechaTexto);
                if (fecha.isBefore(LocalDate.now())) {
                    errores.add("La fecha deseada no puede ser anterior a hoy.");
                }
            } catch (DateTimeParseException e) {
                errores.add("La fecha deseada no es válida.");
            }
        }

        try {
            // Si la pieza ya no existe o no está disponible, se trata como personalizado
            if (productoId != null && dao.nombreProducto(productoId) == null) {
                productoId = null;
            }
            if (productoId == null && descripcion.length() < DESCRIPCION_MINIMA_PERSONALIZADO) {
                errores.add("Describe la pieza que quieres (mínimo "
                        + DESCRIPCION_MINIMA_PERSONALIZADO + " caracteres).");
            }

            if (errores.isEmpty()) {
                dao.crear(usuario.getId(), productoId, descripcion, cantidad, fecha);
                resp.sendRedirect(req.getContextPath() + "/mis-pedidos?ok=1");
                return;
            }
            mostrarFormulario(req, resp, productoId, errores, valores);
        } catch (SQLException e) {
            responderError(resp, e);
        }
    }

    /** Entrega a la vista los datos necesarios para dibujar el formulario. */
    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp,
            Integer productoId, List<String> errores, Map<String, String> valores)
            throws SQLException, ServletException, IOException {
        String nombre = (productoId == null) ? null : dao.nombreProducto(productoId);
        Integer idValido = (nombre == null) ? null : productoId;
        req.setAttribute("productoId", idValido);
        req.setAttribute("productoNombre", nombre);
        req.setAttribute("errores", errores);
        req.setAttribute("v", valores);
        req.getRequestDispatcher("/WEB-INF/vistas/encargo.jsp").forward(req, resp);
    }

    /** Registra el error técnico en el log y responde con un mensaje genérico. */
    private void responderError(HttpServletResponse resp, SQLException e) throws IOException {
        log("Error en encargos", e);
        resp.sendError(500, "No se pudo procesar el encargo");
    }

    /** Obtiene el usuario de la sesión actual; null si no ha iniciado sesión. */
    private static Usuario usuarioEnSesion(HttpServletRequest req) {
        HttpSession sesion = req.getSession(false);
        return (sesion == null) ? null : (Usuario) sesion.getAttribute("usuario");
    }

    /** Convierte texto a entero; devuelve null si está vacío o no es un número. */
    private static Integer aEntero(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Quita espacios al inicio y al final; devuelve "" si el valor es null. */
    private static String limpiar(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
