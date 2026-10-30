package modelo;

/**
 * Categoría en la que se agrupan las obras del catálogo
 * (por ejemplo: crucifijos, imágenes religiosas, rosarios).
 *
 * @author Alexander Gaviria Cossío
 */
public class Categoria {

    private final int id;
    private final String nombre;

    /**
     * Crea una categoría.
     *
     * @param id     identificador en la base de datos
     * @param nombre nombre que se muestra al visitante
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
