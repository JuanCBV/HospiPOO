package vista;

import controlador.*;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema
 */
public class VentanaPrincipal extends JFrame {
    private ControladorUsuario ctrlUsuario;
    private ControladorPaciente ctrlPaciente;
    private ControladorOrden ctrlOrden;
    private ControladorHistoria ctrlHistoria;
    private JTabbedPane tabbedPane;

    public VentanaPrincipal(ControladorUsuario ctrlUsuario) {
        this.ctrlUsuario = ctrlUsuario;
        this.ctrlPaciente = new ControladorPaciente();
        this.ctrlOrden = new ControladorOrden();
        this.ctrlHistoria = new ControladorHistoria();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        Usuario usuario = ctrlUsuario.getUsuarioActual();
        setTitle("HospiPoo - " + usuario.getNombre() + " (" + usuario.getTipoUsuario() + ")");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuArchivo.add(itemCerrarSesion);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        // Pestañas según tipo de usuario
        tabbedPane = new JTabbedPane();

        // Pestaña de Pacientes (ambos usuarios)
        tabbedPane.addTab("👥 Pacientes", new PanelPacientes(ctrlPaciente, usuario));

        // Pestaña de Órdenes (ambos usuarios)
        tabbedPane.addTab("📋 Órdenes de Servicio", 
                          new PanelOrdenes(ctrlOrden, ctrlPaciente, ctrlUsuario, usuario));

        // Pestaña de Historias Clínicas (solo médicos)
        if (usuario.esMedico()) {
            tabbedPane.addTab("📝 Historias Clínicas", 
                              new PanelHistorias(ctrlHistoria, ctrlPaciente, ctrlOrden, ctrlUsuario, usuario));
        }

        // Pestaña de Reportes (solo facturadores)
        if (usuario.esFacturador()) {
            tabbedPane.addTab("📊 Reportes", 
                              new PanelReportes(ctrlPaciente, ctrlOrden, ctrlHistoria, ctrlUsuario));
        }

        add(tabbedPane, BorderLayout.CENTER);

        // Panel inferior con información del usuario
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInfo.add(new JLabel("Usuario activo: " + usuario.getNombre()));
        add(panelInfo, BorderLayout.SOUTH);
    }

    private void cerrarSesion() {
        ctrlUsuario.cerrarSesion();
        dispose();
        new VentanaLogin(ctrlUsuario).setVisible(true);
    }
}
