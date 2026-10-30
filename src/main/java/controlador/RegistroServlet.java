package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDao;

/**
 * Controlador del registro de usuarios.
 * GET /registro muestra el formulario; POST /registro valida los datos
 * en el servidor y crea la cuenta (la contraseña se guarda cifrada).
 *
 * @author Alexander Gaviria Cossío
 */
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Correo con formato básico: algo@dominio.ext */
    private static final Pattern CORREO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    /** Cédula: solo dígitos, entre 5 y 15. */
    private static final Pattern CEDULA = Pattern.compile("^\\d{5,15}$");

    /** Teléfono: dígitos, espacios o "+", entre 7 y 15 caracteres. */
    private static final Pattern TELEFONO = Pattern.compile("^[0-9+ ]{7,15}$");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nombres = limpiar(req.getParameter("nombres"));
        String apellidos = limpiar(req.getParameter("apellidos"));
        String cedula = limpiar(req.getParameter("cedula"));
        String fecha = limpiar(req.getParameter("fecha_nacimiento"));
        String correo = limpiar(req.getParameter("correo")).toLowerCase();
        String telefono = limpiar(req.getParameter("telefono"));
        String clave = valor(req.getParameter("contrasena"));
        String confirmar = valor(req.getParameter("confirmar"));

        // Valores que se devuelven al formulario si hay errores (sin las contraseñas)
        Map<String, String> valores = new HashMap<>();
        valores.put("nombres", nombres);
        valores.put("apellidos", apellidos);
        valores.put("cedula", cedula);
        valores.put("fecha_nacimiento", fecha);
        valores.put("correo", correo);
        valores.put("telefono", telefono);

        // Validación en el servidor (no se confía solo en la del navegador)
        List<String> errores = new ArrayList<>();
        if (nombres.length() < 2 || nombres.length() > 100) {
            errores.add("Escribe tus nombres.");
        }
        if (apellidos.length() < 2 || apellidos.length() > 100) {
            errores.add("Escribe tus apellidos.");
        }
        if (!CEDULA.matcher(cedula).matches()) {
            errores.add("La cédula debe tener solo números (entre 5 y 15 dígitos).");
        }
        if (!CORREO.matcher(correo).matches() || correo.length() > 120) {
            errores.add("El correo no es válido.");
        }
        if (!telefono.isEmpty() && !TELEFONO.matcher(telefono).matches()) {
            errores.add("El teléfono no es válido.");
        }
        if (clave.length() < 8) {
            errores.add("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!clave.equals(confirmar)) {
            errores.add("Las contraseñas no coinciden.");
        }

        LocalDate nacimiento = null;
        try {
            nacimiento = LocalDate.parse(fecha);
            if (nacimiento.isAfter(LocalDate.now()) || nacimiento.getYear() < 1900) {
                errores.add("La fecha de nacimiento no es válida.");
            }
        } catch (DateTimeParseException e) {
            errores.add("Escribe una fecha de nacimiento válida.");
        }

        try {
            UsuarioDao dao = new UsuarioDao();
            if (errores.isEmpty()) {
                if (dao.existeCorreo(correo)) {
                    errores.add("Ese correo ya está registrado.");
                }
                if (dao.existeCedula(cedula)) {
                    errores.add("Esa cédula ya está registrada.");
                }
            }
            if (errores.isEmpty()) {
                dao.crear(nombres, apellidos, cedula, nacimiento, correo, telefono, clave);
                resp.sendRedirect(req.getContextPath() + "/login?registro=ok");
                return;
            }
        } catch (SQLException e) {
            // 1062 = valor duplicado (otro usuario se registró al mismo tiempo)
            if (e.getErrorCode() == 1062) {
                errores.add("Ese correo o cédula ya está registrado.");
            } else {
                log("Error registrando usuario", e);
                resp.sendError(500, "No se pudo completar el registro");
                return;
            }
        }

        req.setAttribute("errores", errores);
        req.setAttribute("v", valores);
        req.getRequestDispatcher("/registro.jsp").forward(req, resp);
    }

    /** Quita espacios al inicio y al final; devuelve "" si el valor es null. */
    private static String limpiar(String texto) {
        return texto == null ? "" : texto.trim();
    }

    /** Devuelve el texto tal cual (sin recortar); "" si es null. Para contraseñas. */
    private static String valor(String texto) {
        return texto == null ? "" : texto;
    }
}
