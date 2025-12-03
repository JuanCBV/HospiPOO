package vista;

import util.PdfGenerator;
import controlador.*;
import hilos.HiloGuardar;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel para gestionar historias clínicas (solo médicos)
 */
public class PanelHistorias extends JPanel {
    private ControladorHistoria ctrlHistoria;
    private ControladorPaciente ctrlPaciente;
    private ControladorOrden ctrlOrden;
    private ControladorUsuario ctrlUsuario;
    private Usuario usuario;
    private JComboBox<Paciente> cboPaciente;
    private JTextArea txtAntecedentes, txtObservaciones, txtTratamiento;
    private JTable tablaHistorias;
    private DefaultTableModel modeloTabla;

    public PanelHistorias(ControladorHistoria ctrlHistoria, ControladorPaciente ctrlPaciente,
                          ControladorOrden ctrlOrden, ControladorUsuario ctrlUsuario, Usuario usuario) {
        this.ctrlHistoria = ctrlHistoria;
        this.ctrlPaciente = ctrlPaciente;
        this.ctrlOrden = ctrlOrden;
        this.ctrlUsuario = ctrlUsuario;
        this.usuario = usuario;
        inicializarComponentes();
        cargarHistorias();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Nueva Historia Clínica"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Paciente:"), gbc);

        gbc.gridx = 1;
        cboPaciente = new JComboBox<>();
        for (Paciente p : ctrlPaciente.obtenerTodos()) {
            cboPaciente.addItem(p);
        }
        panelFormulario.add(cboPaciente, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Antecedentes:"), gbc);

        gbc.gridx = 1;
        txtAntecedentes = new JTextArea(3, 30);
        panelFormulario.add(new JScrollPane(txtAntecedentes), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        txtObservaciones = new JTextArea(3, 30);
        panelFormulario.add(new JScrollPane(txtObservaciones), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Tratamiento:"), gbc);

        gbc.gridx = 1;
        txtTratamiento = new JTextArea(3, 30);
        panelFormulario.add(new JScrollPane(txtTratamiento), gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnCrear = new JButton("Crear Historia");
        btnCrear.addActionListener(e -> crearHistoria());
        panelBotones.add(btnCrear);

        JButton btnCrearRemision = new JButton("Crear Remisión");
        btnCrearRemision.addActionListener(e -> crearRemision());
        panelBotones.add(btnCrearRemision);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla de historias
        String[] columnas = {"Consecutivo", "Paciente", "Fecha", "Médico", "Tiene Remisión"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaHistorias = new JTable(modeloTabla);
        add(new JScrollPane(tablaHistorias), BorderLayout.CENTER);
        
        JButton btnDescargarPDF = new JButton("Descargar Historia en PDF");
        btnDescargarPDF.addActionListener(e -> descargarHistoriaPDF());
        panelBotones.add(btnDescargarPDF);
    }

    private void crearHistoria() {
        Paciente paciente = (Paciente) cboPaciente.getSelectedItem();
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar que el paciente tenga orden de servicio
        if (!ctrlOrden.pacienteTieneOrden(paciente)) {
            JOptionPane.showMessageDialog(this, 
                "El paciente debe tener una orden de servicio previa para poder crear la historia clínica", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HistoriaClinica historia = ctrlHistoria.crearHistoria(
            paciente,
            txtAntecedentes.getText(),
            txtObservaciones.getText(),
            txtTratamiento.getText(),
            usuario.getNombre()
        );

        new HiloGuardar("Historia clínica #" + historia.getConsecutivo() + " creada exitosamente", () -> {
            limpiarFormulario();
            cargarHistorias();
        }).start();
    }

    private void crearRemision() {
        Paciente paciente = (Paciente) cboPaciente.getSelectedItem();
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear orden de remisión
        OrdenServicio remision = ctrlOrden.crearOrden(
            paciente, 
            usuario.getNombre(), 
            "Médico", 
            true
        );

        new HiloGuardar("Remisión #" + remision.getConsecutivo() + " creada exitosamente", null).start();
    }

    private void cargarHistorias() {
        modeloTabla.setRowCount(0);
        for (HistoriaClinica h : ctrlHistoria.obtenerTodas()) {
            modeloTabla.addRow(new Object[]{
                h.getConsecutivo(),
                h.getPaciente().getNombre(),
                h.getFechaFormateada(),
                h.getNombreMedico(),
                h.getOrdenAsociada() != null ? "Sí" : "No"
            });
        }
    }
    
    private void descargarHistoriaPDF() {
    int filaSeleccionada = tablaHistorias.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una historia clínica de la tabla",
                                      "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Obtener el consecutivo de la historia seleccionada
    int consecutivo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

    // Buscar la historia completa en el controlador
    HistoriaClinica historia = ctrlHistoria.buscarPorConsecutivo(consecutivo);
    if (historia == null) {
        JOptionPane.showMessageDialog(this, "No se encontró la historia clínica seleccionada",
                                      "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

       
    // Generar el PDF usando PdfGenerator
    PdfGenerator.generarHistoriaClinica(historia);
    }
    
    

    private void limpiarFormulario() {
        txtAntecedentes.setText("");
        txtObservaciones.setText("");
        txtTratamiento.setText("");
    }
}
