package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Clase que representa una Orden de Servicio
 */
public class OrdenServicio {
    private int consecutivo;
    private LocalDateTime fechaHora;
    private Paciente paciente;
    private ArrayList<Servicio> servicios;
    private String nombreCreador;
    private String cargoCreador;
    private boolean esRemision;

    public OrdenServicio(int consecutivo, Paciente paciente, String nombreCreador, String cargoCreador, boolean esRemision) {
        this.consecutivo = consecutivo;
        this.fechaHora = LocalDateTime.now();
        this.paciente = paciente;
        this.servicios = new ArrayList<>();
        this.nombreCreador = nombreCreador;
        this.cargoCreador = cargoCreador;
        this.esRemision = esRemision;
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    // Getters
    public int getConsecutivo() { return consecutivo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Paciente getPaciente() { return paciente; }
    public ArrayList<Servicio> getServicios() { return servicios; }
    public String getNombreCreador() { return nombreCreador; }
    public String getCargoCreador() { return cargoCreador; }
    public boolean isEsRemision() { return esRemision; }

    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaHora.format(formatter);
    }

    @Override
    public String toString() {
        return "Orden #" + consecutivo + " - " + paciente.getNombre() + " - " + getFechaFormateada();
    }
}
