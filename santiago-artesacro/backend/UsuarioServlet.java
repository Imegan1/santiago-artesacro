package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDao;
import modelo.Usuario;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Método que maneja peticiones GET (por ejemplo, listar usuarios o cargar vistas)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Respuesta desde el Servlet por GET</h2>");
        
        UsuarioDao dao = new UsuarioDao();
        List<Usuario> lista = dao.listar();
        
        out.println("<ul>");
        for(Usuario u : lista) {
            out.println("<li>ID: " + u.getId() + " - Nombre: " + u.getNombres() + " - Correo: " + u.getCorreo() + "</li>");
        }
        out.println("</ul>");
        out.println("</body></html>");
    }

    // Método que maneja peticiones POST (por ejemplo, cuando envías un formulario de registro)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recibimos los parámetros enviados desde el formulario HTML/JSP
        String nombres = request.getParameter("nombres");
        String correo = request.getParameter("correo");
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setCorreo(correo);
        
        UsuarioDao dao = new UsuarioDao();
        dao.insertar(nuevoUsuario);
        
        // Redireccionamos o respondemos al usuario
        response.getWriter().println("¡Usuario registrado exitosamente desde el Servlet via POST!");
    }
}
