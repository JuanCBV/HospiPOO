package vista;

import controlador.*;
import hilos.HiloGuardar;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel para gestionar órdenes de servicio
 */
public class PanelOrdenes extends JPanel {
    private ControladorOrden ctrlOrden;
    private ControladorPaciente ctrlPaciente;
    private ControladorUsuario ctrlUsuario;
    private Usuario usuario;
    private JComboBox<Paciente> cboPaciente;
    private JList<Servicio> listaServicios;
    private JCheckBox chkEsRemision;
    private JTable tablaOrdenes;
    private DefaultTableModel modeloTabla;

    public PanelOrdenes(ControladorOrden ctrlOrden, ControladorPaciente ctrlPaciente,
                        ControladorUsuario ctrlUsuario, Usuario usuario) {
        this.ctrlOrden = ctrlOrden;
        this.ctrlPaciente = ctrlPaciente;
        this.ctrlUsuario = ctrlUsuario;
        this.usuario = usuario;
        inicializarComponentes();
        cargarOrdenes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior - Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Nueva Orden de Servicio"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Paciente:"), gbc);

        gbc.gridx = 1;
        cboPaciente = new JComboBox<>();
        for (Paciente p : ctrlPaciente.obtenerTodos()) {
            cboPaciente.addItem(p);
        }
        panelFormulario.add(cboPaciente, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Servicios:"), gbc);

        gbc.gridx = 1;
        DefaultListModel<Servicio> modeloLista = new DefaultListModel<>();
        for (Servicio s : ctrlOrden.getServiciosDisponibles()) {
            modeloLista.addElement(s);
        }
        listaServicios = new JList<>(modeloLista);
        listaServicios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollServicios = new JScrollPane(listaServicios);
        scrollServicios.setPreferredSize(new Dimension(250, 100));
        panelFormulario.add(scrollServicios, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        chkEsRemision = new JCheckBox("Es Remisión");
        panelFormulario.add(chkEsRemision, gbc);

        gbc.gridx = 1;
        JButton btnCrear = new JButton("Crear Orden");
        btnCrear.addActionListener(e -> crearOrden());
        panelFormulario.add(btnCrear, gbc);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla de órdenes
        String[] columnas = {"Consecutivo", "Paciente", "Fecha", "Creador", "Es Remisión"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaOrdenes = new JTable(modeloTabla);
        add(new JScrollPane(tablaOrdenes), BorderLayout.CENTER);
    }

    private void crearOrden() {
        Paciente paciente = (Paciente) cboPaciente.getSelectedItem();
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (listaServicios.getSelectedValuesList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un servicio", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cargo = usuario.esMedico() ? "Médico" : "Facturador";
        OrdenServicio orden = ctrlOrden.crearOrden(
            paciente, 
            usuario.getNombre(), 
            cargo, 
            chkEsRemision.isSelected()
        );

        for (Servicio s : listaServicios.getSelectedValuesList()) {
            orden.agregarServicio(s);
        }

        new HiloGuardar("Orden de servicio #" + orden.getConsecutivo() + " creada exitosamente", () -> {
            cargarOrdenes();
            listaServicios.clearSelection();
            chkEsRemision.setSelected(false);
        }).start();
    }

    private void cargarOrdenes() {
        modeloTabla.setRowCount(0);
        for (OrdenServicio o : ctrlOrden.obtenerTodas()) {
            modeloTabla.addRow(new Object[]{
                o.getConsecutivo(),
                o.getPaciente().getNombre(),
                o.getFechaFormateada(),
                o.getNombreCreador(),
                o.isEsRemision() ? "Sí" : "No"
            });
        }
    }
}
