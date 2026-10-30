package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Pedido;
import util.Conexion;

/**
 * Acceso a datos de la tabla "pedidos": encargos de los clientes (HU-003).
 *
 * @author Alexander Gaviria Cossío
 */
public class PedidoDao {

    /**
     * Busca el nombre de un producto que esté disponible.
     *
     * @param id identificador del producto
     * @return nombre del producto; null si no existe o no está disponible
     * @throws SQLException si falla la consulta
     */
    public String nombreProducto(int id) throws SQLException {
        String sql = "SELECT nombre FROM productos WHERE id = ? AND disponible = TRUE";
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("nombre") : null;
            }
        }
    }

    /**
     * Guarda un encargo nuevo con estado inicial "recibido".
     *
     * @param usuarioId     cliente que hace el encargo
     * @param productoId    pieza del catálogo; null si es un encargo personalizado
     * @param descripcion   descripción o notas del cliente (puede ser vacía)
     * @param cantidad      número de piezas (entre 1 y 50)
     * @param fechaDeseada  fecha en que el cliente necesita la pieza; null si no indicó
     * @throws SQLException si falla la inserción
     */
    public void crear(int usuarioId, Integer productoId, String descripcion,
            int cantidad, LocalDate fechaDeseada) throws SQLException {
        String tipo = (productoId == null) ? "personalizado" : "catalogo";
        String sql = "INSERT INTO pedidos (usuario_id, producto_id, tipo, descripcion_encargo, "
                + "cantidad, fecha_deseada) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            if (productoId == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, productoId);
            }
            ps.setString(3, tipo);
            ps.setString(4, descripcion.isEmpty() ? null : descripcion);
            ps.setInt(5, cantidad);
            if (fechaDeseada == null) {
                ps.setNull(6, Types.DATE);
            } else {
                ps.setDate(6, Date.valueOf(fechaDeseada));
            }
            ps.executeUpdate();
        }
    }

    /**
     * Lista los pedidos de un cliente, del más reciente al más antiguo.
     *
     * @param usuarioId identificador del cliente
     * @return lista de pedidos (vacía si no tiene)
     * @throws SQLException si falla la consulta
     */
    public List<Pedido> listarPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT p.id, p.tipo, p.descripcion_encargo, p.cantidad, p.fecha_deseada, "
                + "p.estado, p.fecha_pedido, pr.nombre AS producto "
                + "FROM pedidos p LEFT JOIN productos pr ON pr.id = p.producto_id "
                + "WHERE p.usuario_id = ? ORDER BY p.id DESC";
        List<Pedido> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setTipo(rs.getString("tipo"));
                    pedido.setProducto(rs.getString("producto"));
                    pedido.setDescripcion(rs.getString("descripcion_encargo"));
                    pedido.setCantidad(rs.getInt("cantidad"));
                    Date fechaDeseada = rs.getDate("fecha_deseada");
                    pedido.setFechaDeseada(fechaDeseada == null ? "" : fechaDeseada.toString());
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setFechaPedido(rs.getTimestamp("fecha_pedido")
                            .toLocalDateTime().toLocalDate().toString());
                    lista.add(pedido);
                }
            }
        }
        return lista;
    }
}
