package controlador;

import modelo.Usuario;
import java.util.ArrayList;

/**
 * Controlador para gestionar usuarios (médicos y facturadores)
 */
public class ControladorUsuario {
    private ArrayList<Usuario> usuarios;
    private int contadorId;
    private Usuario usuarioActual;

    public ControladorUsuario() {
        this.usuarios = new ArrayList<>();
        this.contadorId = 1;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Crear usuarios de ejemplo
        registrarUsuario("Dr. Carlos Rodríguez", "medico1", "1234", "MEDICO", "Medicina General");
        registrarUsuario("Dra. Ana López", "medico2", "1234", "MEDICO", "Cardiología");
        registrarUsuario("Pedro Martínez", "facturador1", "1234", "FACTURADOR", "");
    }

    public Usuario registrarUsuario(String nombre, String usuario, String contrasena, 
                                     String tipoUsuario, String especialidad) {
        Usuario u = new Usuario(contadorId++, nombre, usuario, contrasena, tipoUsuario, especialidad);
        usuarios.add(u);
        return u;
    }

    public Usuario iniciarSesion(String usuario, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getContrasena().equals(contrasena)) {
                this.usuarioActual = u;
                return u;
            }
        }
        return null;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public ArrayList<Usuario> obtenerMedicos() {
        ArrayList<Usuario> medicos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.esMedico()) {
                medicos.add(u);
            }
        }
        return medicos;
    }

    public void modificarUsuario(Usuario usuario, String nombre, String especialidad) {
        usuario.setNombre(nombre);
        if (usuario.esMedico()) {
            usuario.setEspecialidad(especialidad);
        }
    }

    public ArrayList<Usuario> obtenerTodos() {
        return usuarios;
    }
}
