/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospipoo;
import controlador.ControladorUsuario;
import vista.VentanaLogin;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 *
 * @author juanc
 */
public class HospiPOO {

    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer Look and Feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Crear controlador de usuarios y mostrar ventana de login
            ControladorUsuario ctrlUsuario = new ControladorUsuario();
            VentanaLogin ventanaLogin = new VentanaLogin(ctrlUsuario);
            ventanaLogin.setVisible(true);
        });
    }
}
