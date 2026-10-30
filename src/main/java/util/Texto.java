package util;

/**
 * Utilidades para el manejo seguro de texto en las páginas JSP.
 *
 * @author Alexander Gaviria Cossío
 */
public final class Texto {

    /** Clase utilitaria: no debe instanciarse. */
    private Texto() {
    }

    /**
     * Escapa los caracteres especiales de HTML para evitar la inyección de
     * código (XSS) al mostrar en pantalla texto escrito por los usuarios.
     *
     * @param texto texto original (puede ser null)
     * @return texto seguro para insertar en HTML; cadena vacía si era null
     */
    public static String esc(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
