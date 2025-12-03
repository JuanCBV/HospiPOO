package controlador;

import modelo.HistoriaClinica;
import modelo.Paciente;
import java.util.ArrayList;

/**
 * Controlador para gestionar historias clínicas
 */
public class ControladorHistoria {
    private ArrayList<HistoriaClinica> historias;
    private int contadorConsecutivo;
    
    public HistoriaClinica buscarPorConsecutivo(int consecutivo) {
    for (HistoriaClinica h : obtenerTodas()) { // o tu lista interna de historias
        if (h.getConsecutivo() == consecutivo) {
            return h;
        }
    }
    return null;
    }
    public ControladorHistoria() {
        this.historias = new ArrayList<>();
        this.contadorConsecutivo = 1;
    }

    public HistoriaClinica crearHistoria(Paciente paciente, String antecedentes, 
                                          String observaciones, String tratamiento, 
                                          String nombreMedico) {
        HistoriaClinica historia = new HistoriaClinica(contadorConsecutivo++, paciente, 
                                                        antecedentes, observaciones, 
                                                        tratamiento, nombreMedico);
        historias.add(historia);
        return historia;
    }

    public ArrayList<HistoriaClinica> obtenerHistoriasPorPaciente(Paciente paciente) {
        ArrayList<HistoriaClinica> resultado = new ArrayList<>();
        for (HistoriaClinica historia : historias) {
            if (historia.getPaciente().getId() == paciente.getId()) {
                resultado.add(historia);
            }
        }
        return resultado;
    }

    public ArrayList<HistoriaClinica> obtenerHistoriasPorMedico(String nombreMedico) {
        ArrayList<HistoriaClinica> resultado = new ArrayList<>();
        for (HistoriaClinica historia : historias) {
            if (historia.getNombreMedico().equals(nombreMedico)) {
                resultado.add(historia);
            }
        }
        return resultado;
    }

    public ArrayList<HistoriaClinica> obtenerTodas() {
        return historias;
    }

    public int contarHistorias() {
        return historias.size();
    }
}
