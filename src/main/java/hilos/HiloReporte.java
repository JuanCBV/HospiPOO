package hilos;

import controlador.*;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Hilo para generar reportes sin bloquear la interfaz
 */
public class HiloReporte extends Thread {
    private ControladorPaciente ctrlPaciente;
    private ControladorOrden ctrlOrden;
    private ControladorHistoria ctrlHistoria;
    private ControladorUsuario ctrlUsuario;
    private JTextArea areaReporte;

    public HiloReporte(ControladorPaciente ctrlPaciente, ControladorOrden ctrlOrden,
                       ControladorHistoria ctrlHistoria, ControladorUsuario ctrlUsuario,
                       JTextArea areaReporte) {
        this.ctrlPaciente = ctrlPaciente;
        this.ctrlOrden = ctrlOrden;
        this.ctrlHistoria = ctrlHistoria;
        this.ctrlUsuario = ctrlUsuario;
        this.areaReporte = areaReporte;
    }

    @Override
    public void run() {
        // Mostrar mensaje de carga
        actualizarUI("Generando reporte...\n\n");
        
        try {
            // Simular tiempo de procesamiento
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("========================================\n");
        reporte.append("    REPORTE HOSPITAL HOSPIPOO\n");
        reporte.append("========================================\n\n");

        // Total de pacientes
        reporte.append("📊 ESTADÍSTICAS GENERALES\n");
        reporte.append("----------------------------------------\n");
        reporte.append("Total de pacientes registrados: ").append(ctrlPaciente.contarPacientes()).append("\n");
        reporte.append("Total de órdenes de servicio: ").append(ctrlOrden.contarOrdenes()).append("\n");
        reporte.append("Total de historias clínicas: ").append(ctrlHistoria.contarHistorias()).append("\n");
        reporte.append("Total de remisiones: ").append(ctrlOrden.contarRemisiones()).append("\n\n");

        // Consultas por médico
        reporte.append("👨‍⚕️ CONSULTAS POR MÉDICO\n");
        reporte.append("----------------------------------------\n");
        for (var medico : ctrlUsuario.obtenerMedicos()) {
            int consultas = ctrlHistoria.obtenerHistoriasPorMedico(medico.getNombre()).size();
            reporte.append(medico.getNombre());
            reporte.append(" (").append(medico.getEspecialidad()).append("): ");
            reporte.append(consultas).append(" consultas\n");
        }
        reporte.append("\n");

        // Lista de pacientes
        reporte.append("👥 PACIENTES REGISTRADOS\n");
        reporte.append("----------------------------------------\n");
        for (var paciente : ctrlPaciente.obtenerTodos()) {
            reporte.append("• ").append(paciente.getNombre());
            reporte.append(" - Doc: ").append(paciente.getDocumento()).append("\n");
        }

        reporte.append("\n========================================\n");
        reporte.append("        FIN DEL REPORTE\n");
        reporte.append("========================================\n");

        // Actualizar UI en el hilo de eventos de Swing
        actualizarUI(reporte.toString());
    }

    private void actualizarUI(String texto) {
        SwingUtilities.invokeLater(() -> {
            areaReporte.setText(texto);
        });
    }
}
