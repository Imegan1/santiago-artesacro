 package principal;

import dao.UsuarioDao;
import modelo.Usuario;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UsuarioDao dao = new UsuarioDao();

        // Prueba de Inserción
        // Usuario nuevo = new Usuario();
        // nuevo.setNombres("Alexander Gaviria");
        // nuevo.setCorreo("alexandergaviria566@gmail.com");
        // dao.insertar(nuevo);

        // Prueba de Consulta (Listar)
        System.out.println("--- LISTA DE USUARIOS EN LA BASE DE DATOS ---");
        List<Usuario> usuarios = dao.listar();
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() + " | Nombre: " + u.getNombres() + " | Correo: " + u.getCorreo());
        }
    }
}