package modelo;

/**
 * Clase que representa un Usuario del sistema
 */
public class Usuario {
    private int id;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String tipoUsuario; // "FACTURADOR" o "MEDICO"
    private String especialidad; // Solo para médicos

    public Usuario(int id, String nombre, String usuario, String contrasena, String tipoUsuario, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.especialidad = especialidad;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public boolean esMedico() {
        return "MEDICO".equals(tipoUsuario);
    }

    public boolean esFacturador() {
        return "FACTURADOR".equals(tipoUsuario);
    }

    @Override
    public String toString() {
        return nombre + " (" + tipoUsuario + ")";
    }
}
