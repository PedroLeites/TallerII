package controlador;

import modelo.Estudiante;
import modelo.ColeccionEstudiantes;
import persistencia.ImportadorExcel;
import vista.PrincipalVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.Iterator;

public class PrincipalControlador implements ActionListener{
	private PrincipalVista vista;
	private ImportadorExcel importador;
	
	public PrincipalControlador(PrincipalVista v) {
		importador = new ImportadorExcel();
		this.vista = v;
		vista.setControlador(this);
	}
	
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 vista.limpiarTabla();
		 try {
			 ColeccionEstudiantes colec = importador.importarDeExcel("morosos.xlsx");
			 Iterator<Estudiante> it = colec.obtenerTodos().iterator();
			 while (it.hasNext()) {
			     Estudiante est = it.next();
			     vista.agregarFila(new Object[]{
			         est.getCI(),
			         est.getNombre(),
			         est.getEmail(),
			         est.getLibro(),
			         est.getFechaVencimiento()
			     });
			 }
			 //colec.obtenerTodos().forEach(est -> vista.agregarFila(new Object[]{est.getCI(), est.getNombre(), est.getEmail(), est.getLibro(), est.getFechaVencimiento()}));
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(vista, "Error al leer Excel: " + ex.getMessage());
	        }
	    }
	
}
