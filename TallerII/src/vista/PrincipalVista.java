package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PrincipalVista extends JFrame {
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar = new JButton("Cargar Excel");
	
	public PrincipalVista() {
		super("Gesti�n de Pr�stamos Vencidos - Biblioteca INET");
		
		// Columnas de la tabla para mostrar los datos de los pr�stamos
        modeloTabla = new DefaultTableModel(new String[] {
            "Fecha Pr�stamo", "Fecha Devoluci�n Prevista", "D�as Retraso",
            "CI Estudiante", "Nombre Estudiante", "Email Estudiante",
            "T�tulo Libro", "Cote Libro", "CB Libro"
        }, 0);
        tabla = new JTable(modeloTabla);
        
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);
        this.add(btnCargar, BorderLayout.SOUTH);
        this.setSize(900, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centrar la ventana
	}
	
	//Este m�todo asocia un ActionListener al bot�n btnCargar.
	public void setControlador(ActionListener al) { 
		btnCargar.addActionListener(al); 
	}
	//Esto romper�a el patr�n MVC
	/* btnCargar.addActionListener(e -> {
   		// c�digo que procesa el Excel y modifica la tabla
	});*/
	
    public void agregarFila(Object[] fila) { 
    	modeloTabla.addRow(fila); 
    }
    
    public void limpiarTabla() { 
    	modeloTabla.setRowCount(0); 
    }
	
}
