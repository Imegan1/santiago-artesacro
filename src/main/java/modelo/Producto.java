package modelo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Obra o servicio del catálogo de arte sacro (HU-002).
 *
 * @author Alexander Gaviria Cossío
 */
public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private String medidas;
    private String material;
    private String imagen;
    private String categoria;
    private BigDecimal precio;
    private boolean disponible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    /** @return ruta relativa de la imagen (ej. img/productos/cristo.jpg) o null */
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /** @return precio en pesos colombianos o null si se cotiza según la pieza */
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Devuelve el precio listo para mostrar, con separador de miles
     * (por ejemplo "$350.000"), o "Consultar precio" si no tiene precio fijo.
     *
     * @return texto del precio
     */
    public String getPrecioTexto() {
        if (precio == null) {
            return "Consultar precio";
        }
        DecimalFormat formato = new DecimalFormat("#,##0",
                new DecimalFormatSymbols(Locale.forLanguageTag("es-CO")));
        return "$" + formato.format(precio);
    }
}
