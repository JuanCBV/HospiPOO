package hilos;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Hilo para simular guardado de datos
 */
public class HiloGuardar extends Thread {
    private String mensaje;
    private Runnable accionAlFinalizar;

    public HiloGuardar(String mensaje, Runnable accionAlFinalizar) {
        this.mensaje = mensaje;
        this.accionAlFinalizar = accionAlFinalizar;
    }

    @Override
    public void run() {
        try {
            // Simular tiempo de guardado
            Thread.sleep(500);
            
            // Mostrar mensaje en el hilo de UI
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if (accionAlFinalizar != null) {
                    accionAlFinalizar.run();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
