package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class DatosVista extends JFrame {
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar = new JButton("Cargar datos");
	
	public DatosVista() {
		super("Datos de préstamos vencidos");
		
		// Columnas de la tabla para mostrar los datos de los préstamos
        modeloTabla = new DefaultTableModel(new String[] {
        		"Fecha Préstamo", "Fecha Devolución Prevista", "Días de Retraso",
                "ID Usuario", "Nombre Completo", "Correo Electrónico",
                "Título del Libro", "ID del Libro"
        }, 0);
        tabla = new JTable(modeloTabla);
        
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);
        this.add(btnCargar, BorderLayout.SOUTH);
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centrar la ventana
	}
	
	//Este método asocia un ActionListener al botón btnCargar.
	public void setControlador(ActionListener al) { 
		btnCargar.addActionListener(al); 
	}
	
	//Esto rompería el patrón MVC
	/* btnCargar.addActionListener(e -> {
   		// código que procesa el Excel y modifica la tabla
	});*/
	
    public void agregarFila(Object[] fila) { 
    	modeloTabla.addRow(fila); 
    }
    
    public void limpiarTabla() { 
    	modeloTabla.setRowCount(0); 
    }
	
}
