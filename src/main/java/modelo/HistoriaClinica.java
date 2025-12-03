package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una Historia Clínica
 */
public class HistoriaClinica {
    private int consecutivo;
    private LocalDateTime fechaHora;
    private Paciente paciente;
    private String antecedentes;
    private String observaciones;
    private String tratamiento;
    private String nombreMedico;
    private OrdenServicio ordenAsociada;

    public HistoriaClinica(int consecutivo, Paciente paciente, String antecedentes, 
                           String observaciones, String tratamiento, String nombreMedico) {
        this.consecutivo = consecutivo;
        this.fechaHora = LocalDateTime.now();
        this.paciente = paciente;
        this.antecedentes = antecedentes;
        this.observaciones = observaciones;
        this.tratamiento = tratamiento;
        this.nombreMedico = nombreMedico;
    }

    // Getters
    public int getConsecutivo() { return consecutivo; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Paciente getPaciente() { return paciente; }
    public String getAntecedentes() { return antecedentes; }
    public String getObservaciones() { return observaciones; }
    public String getTratamiento() { return tratamiento; }
    public String getNombreMedico() { return nombreMedico; }
    public OrdenServicio getOrdenAsociada() { return ordenAsociada; }
    
    public void setOrdenAsociada(OrdenServicio orden) { 
        this.ordenAsociada = orden; 
    }

    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaHora.format(formatter);
    }

    @Override
    public String toString() {
        return "Historia #" + consecutivo + " - " + paciente.getNombre() + " - " + getFechaFormateada();
    }
}
