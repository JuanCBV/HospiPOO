package controlador;

import modelo.Paciente;
import java.util.ArrayList;

/**
 * Controlador para gestionar pacientes
 */
public class ControladorPaciente {
    private ArrayList<Paciente> pacientes;
    private int contadorId;

    public ControladorPaciente() {
        this.pacientes = new ArrayList<>();
        this.contadorId = 1;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        registrarPaciente("Juan Pérez", "123456789", "3001234567", "Calle 123", "01/01/1990");
        registrarPaciente("María García", "987654321", "3007654321", "Carrera 456", "15/05/1985");
    }

    public Paciente registrarPaciente(String nombre, String documento, String telefono, 
                                       String direccion, String fechaNacimiento) {
        Paciente paciente = new Paciente(contadorId++, nombre, documento, telefono, direccion, fechaNacimiento);
        pacientes.add(paciente);
        return paciente;
    }

    public void modificarPaciente(Paciente paciente, String nombre, String telefono, String direccion) {
        paciente.setNombre(nombre);
        paciente.setTelefono(telefono);
        paciente.setDireccion(direccion);
    }

    public Paciente buscarPorDocumento(String documento) {
        for (Paciente p : pacientes) {
            if (p.getDocumento().equals(documento)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Paciente> obtenerTodos() {
        return pacientes;
    }

    public int contarPacientes() {
        return pacientes.size();
    }
}
