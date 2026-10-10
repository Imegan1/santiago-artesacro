package modelo;

public class Usuario {
    private int id;
    private String nombres;
    private String correo;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con datos
    public Usuario(int id, String nombres, String correo) {
        this.id = id;
        this.nombres = nombres;
        this.correo = correo;
    }

    // Getters y Setters (Estándar obligatorio del SENA)
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}