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
		super("Morosos Biblioteca INET");
        modeloTabla = new DefaultTableModel(new String[]{"CI","Nombre","Email","Libro","Vencimiento"}, 0);
        tabla = new JTable(modeloTabla);
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);
        this.add(btnCargar, BorderLayout.SOUTH);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
