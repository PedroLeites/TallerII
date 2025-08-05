package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class DatosVista extends JFrame {
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar = new JButton("Cargar datos");
	private JButton btnEnviar = new JButton("Enviar correo");
	private JTextField campoBuscarID = new JTextField(10);
	private JButton btnBuscarPorID = new JButton("Buscar por ID");
	JPanel panelBotones = new JPanel();
	
	public DatosVista() {
		super("Datos de pr�stamos vencidos");
		
		// Columnas de la tabla para mostrar los datos de los pr�stamos
        modeloTabla = new DefaultTableModel(new String[] {
        		"Fecha Pr�stamo", "Fecha Devoluci�n Prevista", "D�as de Retraso",
                "ID Usuario", "Nombre Completo", "Correo Electr�nico",
                "T�tulo del Libro", "ID del Libro"
        }, 0);
        tabla = new JTable(modeloTabla);
        
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnCargar);
        panelBotones.add(btnEnviar);
        
        panelBotones.add(new JLabel("ID Usuario:"));
        panelBotones.add(campoBuscarID);
        panelBotones.add(btnBuscarPorID);
        
        this.add(panelBotones, BorderLayout.SOUTH);
        
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centrar la ventana
	}
	
	//Este m�todo asocia un ActionListener al bot�n btnCargar.
	public void setControladorDatos(ActionListener al) { 
		btnCargar.addActionListener(al); 
		btnBuscarPorID.addActionListener(al);
	}
	
	public void setControladorCorreo(ActionListener al) {
		btnEnviar.addActionListener(al);
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
	
 // M�todo que el controlador usar� para obtener la fila seleccionada
    public Object[] getDatosFilaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return null;

        Object[] datos = new Object[modeloTabla.getColumnCount()];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = modeloTabla.getValueAt(fila, i);
        }
        return datos;
    }
    
    public int getIDUsuarioBuscado() {
        try {
            return Integer.parseInt(campoBuscarID.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
 // GETTERS PARA RECONOCER EL BOT�N DESDE EL CONTROLADOR
    public JButton getBotonCargar() {
        return btnCargar;
    }

    public JButton getBotonBuscarPorID() {
        return btnBuscarPorID;
    }
    
}
