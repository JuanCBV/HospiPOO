package vista;

import controlador.ControladorUsuario;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana de inicio de sesión
 */
public class VentanaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private ControladorUsuario ctrlUsuario;

    public VentanaLogin(ControladorUsuario ctrlUsuario) {
        this.ctrlUsuario = ctrlUsuario;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("HospiPoo - Iniciar Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(0, 102, 153));
        JLabel lblTitulo = new JLabel("🏥 Hospital HospiPoo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        panelCentral.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtContrasena = new JPasswordField(15);
        panelCentral.add(txtContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(0, 153, 76));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.addActionListener(e -> iniciarSesion());
        panelCentral.add(btnIngresar, gbc);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con información
        JPanel panelInfo = new JPanel();
        panelInfo.add(new JLabel("Usuarios: medico1, medico2, facturador1 | Contraseña: 1234"));
        add(panelInfo, BorderLayout.SOUTH);
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        Usuario u = ctrlUsuario.iniciarSesion(usuario, contrasena);
        if (u != null) {
            dispose();
            new VentanaPrincipal(ctrlUsuario).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
