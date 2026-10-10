package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //'santiago_artesacro' 
   private static final String URL = "jdbc:mysql://localhost:3306/santiago_arte_sacro_db";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // 

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conexion;
    }
}