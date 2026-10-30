package modelo;

/**
 * Representa a una persona registrada en el sistema (cliente o administrador).
 * No guarda la contraseña: solo los datos que se necesitan durante la sesión.
 *
 * @author Alexander Gaviria Cossío
 */
public class Usuario {

    /** Rol que da acceso a la administración del sitio. */
    public static final String ROL_ADMINISTRADOR = "administrador";

    private int id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String rol;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Indica si el usuario tiene el rol de administrador.
     *
     * @return true si es administrador
     */
    public boolean esAdmin() {
        return ROL_ADMINISTRADOR.equals(rol);
    }
}
