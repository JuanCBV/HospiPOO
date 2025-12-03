package controlador;

import modelo.OrdenServicio;
import modelo.Paciente;
import modelo.Servicio;
import java.util.ArrayList;

/**
 * Controlador para gestionar órdenes de servicio
 */
public class ControladorOrden {
    private ArrayList<OrdenServicio> ordenes;
    private ArrayList<Servicio> serviciosDisponibles;
    private int contadorConsecutivo;

    public ControladorOrden() {
        this.ordenes = new ArrayList<>();
        this.serviciosDisponibles = new ArrayList<>();
        this.contadorConsecutivo = 1;
        cargarServiciosIniciales();
    }

    private void cargarServiciosIniciales() {
        serviciosDisponibles.add(new Servicio("SRV001", "Consulta General", "Consulta medicina general"));
        serviciosDisponibles.add(new Servicio("SRV002", "Cardiología", "Consulta especializada cardiología"));
        serviciosDisponibles.add(new Servicio("SRV003", "Laboratorio", "Exámenes de laboratorio"));
        serviciosDisponibles.add(new Servicio("SRV004", "Rayos X", "Radiografías"));
        serviciosDisponibles.add(new Servicio("SRV005", "Ecografía", "Estudios ecográficos"));
    }

    public OrdenServicio crearOrden(Paciente paciente, String nombreCreador, 
                                     String cargoCreador, boolean esRemision) {
        OrdenServicio orden = new OrdenServicio(contadorConsecutivo++, paciente, 
                                                 nombreCreador, cargoCreador, esRemision);
        ordenes.add(orden);
        return orden;
    }

    public boolean pacienteTieneOrden(Paciente paciente) {
        for (OrdenServicio orden : ordenes) {
            if (orden.getPaciente().getId() == paciente.getId()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<OrdenServicio> obtenerOrdenesPorPaciente(Paciente paciente) {
        ArrayList<OrdenServicio> resultado = new ArrayList<>();
        for (OrdenServicio orden : ordenes) {
            if (orden.getPaciente().getId() == paciente.getId()) {
                resultado.add(orden);
            }
        }
        return resultado;
    }

    public ArrayList<OrdenServicio> obtenerTodas() {
        return ordenes;
    }

    public ArrayList<Servicio> getServiciosDisponibles() {
        return serviciosDisponibles;
    }

    public int contarOrdenes() {
        return ordenes.size();
    }

    public int contarRemisiones() {
        int count = 0;
        for (OrdenServicio orden : ordenes) {
            if (orden.isEsRemision()) {
                count++;
            }
        }
        return count;
    }
}
