package controlador;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsuarioDao;
import modelo.Usuario;

/**
 * Controlador del inicio de sesión.
 * GET /login muestra el formulario; POST /login valida las credenciales y,
 * si son correctas, crea la sesión del usuario.
 *
 * @author Alexander Gaviria Cossío
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Minutos de inactividad antes de que la sesión caduque. */
    private static final int MINUTOS_SESION = 30;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String correo = req.getParameter("correo") == null
                ? "" : req.getParameter("correo").trim().toLowerCase();
        String clave = req.getParameter("contrasena") == null
                ? "" : req.getParameter("contrasena");

        try {
            Usuario usuario = new UsuarioDao().autenticar(correo, clave);
            if (usuario == null) {
                // Mensaje genérico: no revela si el correo existe o no
                req.setAttribute("error", "Correo o contraseña incorrectos.");
                req.setAttribute("correo", correo);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
            // Se crea una sesión nueva en cada ingreso (evita reutilizar una vieja)
            HttpSession anterior = req.getSession(false);
            if (anterior != null) {
                anterior.invalidate();
            }
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuario", usuario);
            sesion.setMaxInactiveInterval(MINUTOS_SESION * 60);
            resp.sendRedirect(req.getContextPath() + "/catalogo");
        } catch (SQLException e) {
            log("Error en login", e);
            resp.sendError(500, "No se pudo iniciar sesión");
        }
    }
}
