package vista;

import controlador.ControladorPaciente;
import hilos.HiloGuardar;
import modelo.Paciente;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel para gestionar pacientes
 */
public class PanelPacientes extends JPanel {
    private ControladorPaciente ctrlPaciente;
    private Usuario usuario;
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtDocumento, txtTelefono, txtDireccion, txtFechaNac;

    public PanelPacientes(ControladorPaciente ctrlPaciente, Usuario usuario) {
        this.ctrlPaciente = ctrlPaciente;
        this.usuario = usuario;
        inicializarComponentes();
        cargarPacientes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Paciente"));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Documento:"));
        txtDocumento = new JTextField();
        panelFormulario.add(txtDocumento);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Fecha Nacimiento:"));
        txtFechaNac = new JTextField();
        panelFormulario.add(txtFechaNac);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        if (usuario.esFacturador()) {
            JButton btnRegistrar = new JButton("Registrar Paciente");
            btnRegistrar.addActionListener(e -> registrarPaciente());
            panelBotones.add(btnRegistrar);

            JButton btnModificar = new JButton("Modificar Paciente");
            btnModificar.addActionListener(e -> modificarPaciente());
            panelBotones.add(btnModificar);
        }

        panelFormulario.add(panelBotones);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla de pacientes
        String[] columnas = {"ID", "Nombre", "Documento", "Teléfono", "Dirección", "Fecha Nac."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPacientes = new JTable(modeloTabla);
        tablaPacientes.getSelectionModel().addListSelectionListener(e -> seleccionarPaciente());
        add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);
    }

    private void registrarPaciente() {
        if (txtNombre.getText().isEmpty() || txtDocumento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y Documento son obligatorios", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ctrlPaciente.registrarPaciente(
            txtNombre.getText(),
            txtDocumento.getText(),
            txtTelefono.getText(),
            txtDireccion.getText(),
            txtFechaNac.getText()
        );

        // Usar hilo para guardar
        new HiloGuardar("Paciente registrado exitosamente", () -> {
            limpiarFormulario();
            cargarPacientes();
        }).start();
    }

    private void modificarPaciente() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", 
                                          "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String documento = (String) modeloTabla.getValueAt(fila, 2);
        Paciente paciente = ctrlPaciente.buscarPorDocumento(documento);
        
        if (paciente != null) {
            ctrlPaciente.modificarPaciente(paciente, 
                txtNombre.getText(), txtTelefono.getText(), txtDireccion.getText());
            
            new HiloGuardar("Paciente modificado exitosamente", () -> {
                limpiarFormulario();
                cargarPacientes();
            }).start();
        }
    }

    private void seleccionarPaciente() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
            txtDocumento.setText((String) modeloTabla.getValueAt(fila, 2));
            txtTelefono.setText((String) modeloTabla.getValueAt(fila, 3));
            txtDireccion.setText((String) modeloTabla.getValueAt(fila, 4));
            txtFechaNac.setText((String) modeloTabla.getValueAt(fila, 5));
        }
    }

    private void cargarPacientes() {
        modeloTabla.setRowCount(0);
        for (Paciente p : ctrlPaciente.obtenerTodos()) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getDocumento(), 
                p.getTelefono(), p.getDireccion(), p.getFechaNacimiento()
            });
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtFechaNac.setText("");
    }
}
