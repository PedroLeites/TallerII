package vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PrincipalVista extends JFrame {
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar = new JButton("Cargar Excel");
	private JButton btnNotificar = new JButton("Enviar Correos");
	private JPanel panelBotones = new JPanel();
	
	public PrincipalVista() {
		super("Morosos Biblioteca INET");
        modeloTabla = new DefaultTableModel(new String[]{"CI","Nombre","Email","Libro","Vencimiento"}, 0);
        tabla = new JTable(modeloTabla);
        
        /*
        //Mostrar prestamos vencidos
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("Sí".equals(table.getValueAt(row, 5))) {
                    c.setBackground(new Color(255, 180, 180));
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });*/
        
        panelBotones.add(btnCargar);
        panelBotones.add(btnNotificar);
        
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.SOUTH);
        this.setSize(700, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Este método asocia un ActionListener al botón btnCargar.
	public void setControlador(ActionListener al) { 
		btnCargar.addActionListener(al);
		btnNotificar.addActionListener(al);
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
