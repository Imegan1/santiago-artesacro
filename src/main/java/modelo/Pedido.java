package modelo;

/**
 * Encargo realizado por un cliente: de una pieza del catálogo o personalizado.
 * Es una vista de lectura pensada para mostrar en la página "Mis pedidos".
 *
 * @author Alexander Gaviria Cossío
 */
public class Pedido {

    private int id;
    private int cantidad;
    private String producto;
    private String tipo;
    private String descripcion;
    private String fechaDeseada;
    private String estado;
    private String fechaPedido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /** @return nombre de la pieza del catálogo o null si es un encargo personalizado */
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    /** @return "catalogo" o "personalizado" */
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return fecha deseada (AAAA-MM-DD) o cadena vacía si no se indicó */
    public String getFechaDeseada() {
        return fechaDeseada;
    }

    public void setFechaDeseada(String fechaDeseada) {
        this.fechaDeseada = fechaDeseada;
    }

    /** @return estado interno: recibido, en_proceso, listo, entregado o cancelado */
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    /**
     * Devuelve el estado en un texto legible para el cliente.
     *
     * @return estado con formato amigable (por ejemplo "En proceso")
     */
    public String getEstadoTexto() {
        switch (estado) {
            case "recibido":
                return "Recibido";
            case "en_proceso":
                return "En proceso";
            case "listo":
                return "Listo";
            case "entregado":
                return "Entregado";
            case "cancelado":
                return "Cancelado";
            default:
                return estado;
        }
    }
}
