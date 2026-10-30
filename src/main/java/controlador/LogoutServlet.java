package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controlador del cierre de sesión: GET /logout destruye la sesión y
 * lleva al usuario de nuevo al catálogo.
 *
 * @author Alexander Gaviria Cossío
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        if (sesion != null) {
            sesion.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/catalogo");
    }
}
