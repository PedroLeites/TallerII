package controlador;

import modelo.ManejadoraDeDatos;
import modelo.Prestamo;
import modelo.Usuario;
import vista.DatosVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class DatosControlador implements ActionListener{
	private DatosVista vista;
	private ManejadoraDeDatos manejadora;
	
	public DatosControlador(DatosVista v) {
		this.manejadora = new ManejadoraDeDatos();
		this.vista = v;
		this.vista.setControlador(this); //Botón, cuando te hagan clic, llamá al método actionPerformed() de este controlador
	}
	
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 vista.limpiarTabla();
		 try {
			 
			 for (Usuario usuario : manejadora.getUsuarios().getUsuarios().values()) {
		            for (Prestamo prestamo : usuario.getPrestamos().obtenerTodos()) {
		                vista.agregarFila(new Object[]{
		                    prestamo.getFechaPrestamo(),
		                    prestamo.getFechaDevolucionPrevista(),
		                    prestamo.getDiasRetraso(),
		                    usuario.getId(),
		                    usuario.getNombreCompleto(),
		                    usuario.getCorreo(),
		                    prestamo.getTituloLibro(),
		                    prestamo.getIdLibro()
		                });
		            }
		        }
			 
			 /*for (int i = 0; i < prestamosVencidos.largo(); i++) {
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
				}*/
			 
			 JOptionPane.showMessageDialog(vista, "Datos cargados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		 
		 } catch (Exception ex) {
			 JOptionPane.showMessageDialog(vista, "Error al leer archivo: " + ex.getMessage());
	     }
	 }
	
}
