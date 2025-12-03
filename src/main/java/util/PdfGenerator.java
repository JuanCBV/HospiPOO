/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import modelo.HistoriaClinica;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileOutputStream;

public class PdfGenerator {

    // Genera un PDF completo de la historia clínica
    public static void generarHistoriaClinica(HistoriaClinica historia) {
        try {
            // Diálogo "Guardar como"
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("HistoriaClinica_" + historia.getPaciente().getDocumento() + ".pdf"));
            int opcion = fileChooser.showSaveDialog(null);

            if (opcion == JFileChooser.APPROVE_OPTION) {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

                Document documento = new Document();
                PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));

                documento.open();

                // Encabezado institucional
                documento.add(new Paragraph("HOSPITAL SAN JUAN"));
                documento.add(new Paragraph("HISTORIA CLÍNICA"));
                documento.add(new Paragraph("--------------------------------------------------"));

                // Datos generales
                documento.add(new Paragraph("Consecutivo: " + historia.getConsecutivo()));
                documento.add(new Paragraph("Fecha: " + historia.getFechaFormateada()));
                documento.add(new Paragraph("Paciente: " + historia.getPaciente().getNombre()));
                documento.add(new Paragraph("Documento: " + historia.getPaciente().getDocumento()));
                documento.add(new Paragraph("Teléfono: " + historia.getPaciente().getTelefono()));
                documento.add(new Paragraph("Dirección: " + historia.getPaciente().getDireccion()));
                documento.add(new Paragraph("Fecha de nacimiento: " + historia.getPaciente().getFechaNacimiento()));

                documento.add(new Paragraph("--------------------------------------------------"));

                // Información clínica
                documento.add(new Paragraph("Antecedentes: " + historia.getAntecedentes()));
                documento.add(new Paragraph("Observaciones: " + historia.getObservaciones()));
                documento.add(new Paragraph("Tratamiento: " + historia.getTratamiento()));
                documento.add(new Paragraph("Médico responsable: " + historia.getNombreMedico()));

                // Orden asociada (si existe)
                if (historia.getOrdenAsociada() != null) {
                    documento.add(new Paragraph("Orden asociada: " + historia.getOrdenAsociada().toString()));
                }

                documento.close();
                System.out.println("PDF generado en: " + rutaArchivo);
            } else {
                System.out.println("Guardado cancelado por el usuario.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}