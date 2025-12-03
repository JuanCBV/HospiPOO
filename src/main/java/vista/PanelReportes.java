package vista;

import controlador.*;
import hilos.HiloReporte;
import javax.swing.*;
import java.awt.*;

/**
 * Panel para generar reportes (solo facturadores)
 */
public class PanelReportes extends JPanel {
    private ControladorPaciente ctrlPaciente;
    private ControladorOrden ctrlOrden;
    private ControladorHistoria ctrlHistoria;
    private ControladorUsuario ctrlUsuario;
    private JTextArea areaReporte;

    public PanelReportes(ControladorPaciente ctrlPaciente, ControladorOrden ctrlOrden,
                         ControladorHistoria ctrlHistoria, ControladorUsuario ctrlUsuario) {
        this.ctrlPaciente = ctrlPaciente;
        this.ctrlOrden = ctrlOrden;
        this.ctrlHistoria = ctrlHistoria;
        this.ctrlUsuario = ctrlUsuario;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBorder(BorderFactory.createTitledBorder("Generar Reportes"));

        JButton btnGenerarReporte = new JButton("📊 Generar Reporte Completo");
        btnGenerarReporte.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerarReporte.addActionListener(e -> generarReporte());
        panelBotones.add(btnGenerarReporte);

        JButton btnLimpiar = new JButton("🗑️ Limpiar");
        btnLimpiar.addActionListener(e -> areaReporte.setText(""));
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.NORTH);

        // Área de reporte
        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaReporte.setBackground(new Color(245, 245, 245));
        add(new JScrollPane(areaReporte), BorderLayout.CENTER);
    }

    private void generarReporte() {
        // Usar hilo para generar el reporte sin bloquear la interfaz
        HiloReporte hiloReporte = new HiloReporte(
            ctrlPaciente, ctrlOrden, ctrlHistoria, ctrlUsuario, areaReporte
        );
        hiloReporte.start();
    }
}
