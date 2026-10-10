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

    // Maneja peticiones GET (Listar usuarios)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Listado de Usuarios desde el Servlet (GET)</h2>");
        
        UsuarioDao dao = new UsuarioDao();
        List<Usuario> lista = dao.listar();
        
        out.println("<ul>");
        for(Usuario u : lista) {
            out.println("<li>ID: " + u.getId() + " - Nombre: " + u.getNombres() + " - Correo: " + u.getCorreo() + "</li>");
        }
        out.println("</ul>");
        out.println("<br><a href='index.html'>Volver</a>");
        out.println("</body></html>");
    }

    // Maneja peticiones POST (Registrar usuario desde el formulario)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String nombres = request.getParameter("nombres");
        String correo = request.getParameter("correo");
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setCorreo(correo);
        
        UsuarioDao dao = new UsuarioDao();
        dao.insertar(nuevoUsuario);
        
        PrintWriter out = response.getWriter();
        out.println("<h3>¡Usuario registrado exitosamente mediante POST!</h3>");
        out.println("<br><a href='UsuarioServlet'>Ver lista de usuarios (GET)</a>");
    }
}