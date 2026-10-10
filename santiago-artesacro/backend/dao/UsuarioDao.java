package dao;

import conexion.Conexion;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    // 1. INSERTAR (Create)
    public void insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombres, correo) VALUES (?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getCorreo());
            ps.executeUpdate();
            System.out.println("¡Usuario registrado con éxito!");
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    // 2. CONSULTAR / LISTAR (Read)
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombres(rs.getString("nombres"));
                u.setCorreo(rs.getString("correo"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // 3. ACTUALIZAR (Update)
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombres = ?, correo = ? WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getCorreo());
            ps.setInt(3, usuario.getId());
            ps.executeUpdate();
            System.out.println("¡Usuario actualizado con éxito!");
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    // 4. ELIMINAR (Delete)
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("¡Usuario eliminado con éxito!");
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}