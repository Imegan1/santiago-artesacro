package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Categoria;
import modelo.Producto;
import util.Conexion;

/**
 * Acceso a datos del catálogo: productos y categorías (HU-002).
 *
 * @author Alexander Gaviria Cossío
 */
public class ProductoDao {

    /**
     * Lista las obras del catálogo, de la más reciente a la más antigua.
     *
     * @param categoriaId identificador de la categoría para filtrar;
     *                    null para traer todas las obras
     * @return lista de productos (vacía si no hay ninguno)
     * @throws SQLException si falla la consulta
     */
    public List<Producto> listar(Integer categoriaId) throws SQLException {
        String sql = "SELECT p.id, p.nombre, p.descripcion, p.precio, p.medidas, p.material, "
                + "p.imagen, p.disponible, c.nombre AS categoria "
                + "FROM productos p LEFT JOIN categorias c ON c.id = p.categoria_id"
                + (categoriaId != null ? " WHERE p.categoria_id = ?" : "")
                + " ORDER BY p.id DESC";

        List<Producto> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            if (categoriaId != null) {
                ps.setInt(1, categoriaId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(aProducto(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Lista todas las categorías ordenadas por nombre.
     *
     * @return lista de categorías
     * @throws SQLException si falla la consulta
     */
    public List<Categoria> listarCategorias() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT id, nombre FROM categorias ORDER BY nombre")) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return lista;
    }

    /** Convierte la fila actual del ResultSet en un objeto Producto. */
    private Producto aProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setMedidas(rs.getString("medidas"));
        producto.setMaterial(rs.getString("material"));
        producto.setImagen(rs.getString("imagen"));
        producto.setDisponible(rs.getBoolean("disponible"));
        producto.setCategoria(rs.getString("categoria"));
        return producto;
    }
}
