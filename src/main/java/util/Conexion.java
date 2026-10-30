package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fábrica de conexiones a la base de datos MySQL del proyecto.
 *
 * <p>Los datos de conexión se leen de variables de entorno (DB_URL, DB_USER,
 * DB_PASSWORD) para poder publicar la aplicación sin cambiar el código.
 * Si no existen, se usan los valores del entorno local (XAMPP).</p>
 *
 * @author Alexander Gaviria Cossío
 */
public final class Conexion {

    /** URL JDBC de la base de datos (zona horaria de Colombia y UTF-8). */
    private static final String URL = leer("DB_URL",
            "jdbc:mysql://localhost:3306/santiago_artesacro"
            + "?useSSL=false&allowPublicKeyRetrieval=true"
            + "&serverTimezone=America/Bogota&characterEncoding=UTF-8");

    /** Usuario de la base de datos (en XAMPP, por defecto, "root"). */
    private static final String USUARIO = leer("DB_USER", "root");

    /** Contraseña de la base de datos (en XAMPP, por defecto, vacía). */
    private static final String CLAVE = leer("DB_PASSWORD", "");

    /** Clase utilitaria: no debe instanciarse. */
    private Conexion() {
    }

    /**
     * Lee una variable de entorno.
     *
     * @param nombre     nombre de la variable de entorno
     * @param porDefecto valor a usar si la variable no existe o está vacía
     * @return el valor de la variable o el valor por defecto
     */
    private static String leer(String nombre, String porDefecto) {
        String valor = System.getenv(nombre);
        return (valor == null || valor.isEmpty()) ? porDefecto : valor;
    }

    /**
     * Abre una conexión nueva a la base de datos.
     * Quien la reciba debe cerrarla (se recomienda try-with-resources).
     *
     * @return conexión abierta a MySQL
     * @throws SQLException si no se puede conectar o falta el driver
     */
    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Falta el driver de MySQL en WEB-INF/lib", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
