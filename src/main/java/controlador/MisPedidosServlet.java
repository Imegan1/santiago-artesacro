package controlador;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PedidoDao;
import modelo.Usuario;

/**
 * Controlador de "Mis pedidos" (HU-003): muestra al cliente sus encargos y
 * el estado de cada uno. Requiere sesión iniciada.
 *
 * @author Alexander Gaviria Cossío
 */
@WebServlet("/mis-pedidos")
public class MisPedidosServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        Usuario usuario = (sesion == null) ? null : (Usuario) sesion.getAttribute("usuario");
        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            req.setAttribute("pedidos", new PedidoDao().listarPorUsuario(usuario.getId()));
            req.getRequestDispatcher("/WEB-INF/vistas/mis-pedidos.jsp").forward(req, resp);
        } catch (SQLException e) {
            log("Error listando pedidos", e);
            resp.sendError(500, "No se pudieron cargar tus pedidos");
        }
    }
}
