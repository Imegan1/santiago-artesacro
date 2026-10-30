package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import modelo.Usuario;
import util.Clave;
import util.Conexion;

/**
 * Acceso a datos de la tabla "usuarios": registro y autenticación.
 * Todas las consultas usan PreparedStatement para evitar la inyección SQL.
 *
 * @author Alexander Gaviria Cossío
 */
public class UsuarioDao {

    /**
     * Comprueba si ya existe un usuario con ese correo.
     *
     * @param correo correo a buscar
     * @return true si ya está registrado
     * @throws SQLException si falla la consulta
     */
    public boolean existeCorreo(String correo) throws SQLException {
        return existe("SELECT 1 FROM usuarios WHERE correo = ?", correo);
    }

    /**
     * Comprueba si ya existe un usuario con esa cédula.
     *
     * @param cedula cédula a buscar
     * @return true si ya está registrada
     * @throws SQLException si falla la consulta
     */
    public boolean existeCedula(String cedula) throws SQLException {
        return existe("SELECT 1 FROM usuarios WHERE cedula = ?", cedula);
    }

    /** Ejecuta una consulta con un solo parámetro y dice si devolvió filas. */
    private boolean existe(String sql, String valor) throws SQLException {
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Registra un usuario nuevo con rol de cliente. La contraseña se guarda
     * cifrada, nunca en texto plano.
     *
     * @param nombres    nombres del usuario
     * @param apellidos  apellidos del usuario
     * @param cedula     número de cédula (único)
     * @param nacimiento fecha de nacimiento
     * @param correo     correo electrónico (único)
     * @param telefono   teléfono o WhatsApp; cadena vacía si no lo indicó
     * @param clave      contraseña en texto plano (se cifra aquí)
     * @throws SQLException si falla la inserción (por ejemplo, correo repetido)
     */
    public void crear(String nombres, String apellidos, String cedula, LocalDate nacimiento,
            String correo, String telefono, String clave) throws SQLException {
        String sql = "INSERT INTO usuarios (nombres, apellidos, cedula, fecha_nacimiento, "
                + "correo, telefono, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setString(3, cedula);
            ps.setDate(4, Date.valueOf(nacimiento));
            ps.setString(5, correo);
            ps.setString(6, telefono.isEmpty() ? null : telefono);
            ps.setString(7, Clave.hash(clave));
            ps.executeUpdate();
        }
    }

    /**
     * Valida las credenciales de un usuario.
     *
     * @param correo correo escrito en el formulario
     * @param clave  contraseña escrita en el formulario
     * @return el usuario si las credenciales son correctas; null si no lo son
     * @throws SQLException si falla la consulta
     */
    public Usuario autenticar(String correo, String clave) throws SQLException {
        String sql = "SELECT id, nombres, apellidos, correo, rol, contrasena "
                + "FROM usuarios WHERE correo = ?";
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && Clave.verificar(clave, rs.getString("contrasena"))) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setRol(rs.getString("rol"));
                    return usuario;
                }
            }
        }
        return null;
    }
}
