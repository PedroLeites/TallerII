package controlador;

import modelo.Prestamo;
import modelo.ColeccionPrestamos;
import persistencia.ImportadorExcel;
import vista.PrincipalVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class PrincipalControlador implements ActionListener{
	private PrincipalVista vista;
	private ImportadorExcel importador;
	private ColeccionPrestamos prestamosVencidos; // Para almacenar los préstamos
	
	public PrincipalControlador(PrincipalVista v) {
		importador = new ImportadorExcel();
		this.vista = v;
		vista.setControlador(this); //Botón, cuando te hagan clic, llamá al método actionPerformed() de este controlador
	}
	
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 vista.limpiarTabla();
		 try {
			 prestamosVencidos = importador.importarDeExcel("datos.xls");
			 
			 if(prestamosVencidos.vacia()) {
	                JOptionPane.showMessageDialog(vista, "No se encontraron préstamos vencidos en el archivo.", "Información", JOptionPane.INFORMATION_MESSAGE);
	                return;
	         }
			 
			 for (int i = 0; i < prestamosVencidos.largo(); i++) {
				    Prestamo prestamo = prestamosVencidos.obtenerTodos().get(i);
				    vista.agregarFila(new Object[]{
				        prestamo.getFechaPrestamo(),
				        prestamo.getFechaDevolucionPrevista(),
				        prestamo.getDiasRetraso(),
				        prestamo.getEstudiante().getCI(),
				        prestamo.getEstudiante().getNombre(),
				        prestamo.getEstudiante().getEmail(),
				        prestamo.getLibro().getTitulo(),
				        prestamo.getLibro().getExplCote(),
				        prestamo.getLibro().getExplCb()
				    });
				}
			 
			 JOptionPane.showMessageDialog(vista, "Datos cargados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		 
		 } catch (Exception ex) {
			 JOptionPane.showMessageDialog(vista, "Error al leer Excel: " + ex.getMessage());
	     }
	 }
	
}
