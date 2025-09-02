package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

import controlador.ManejadoraDeDatos;
import modelo.Usuario;
import modelo.Prestamo;
import modelo.ColeccionUsuarios;
import modelo.ColeccionPrestamos;

public class DatosVista extends JFrame {
	private ManejadoraDeDatos controlador;
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar = new JButton("Cargar datos");
	private JButton btnEnviar = new JButton("Enviar correo");
	private JTextField campoBuscarID = new JTextField(10);
	private JButton btnBuscarPorID = new JButton("Buscar por ID");
	JPanel panelBotones = new JPanel();
	
	public DatosVista() {
		super("Datos de préstamos vencidos");
		
		//Columnas de la tabla para mostrar los datos de los préstamos
        modeloTabla = new DefaultTableModel(new String[] {
        		"Fecha Préstamo", "Fecha Devolución Prevista", "Días de Retraso",
                "ID Usuario", "Nombre Completo", "Correo Electrónico",
                "Título del Libro", "ID del Libro"
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
        this.setLocationRelativeTo(null); //Centrar la ventana
	}
	
	public void setControladorDatos(ManejadoraDeDatos controlador) { 
		this.controlador = controlador;
		
		this.btnCargar.addActionListener(e -> {
			try {
				limpiarTabla();
				this.controlador.cargarDatos();
				
				ColeccionUsuarios cu = this.controlador.obtenerUsuarios();
				if(cu == null || cu.vacia()) {
					mostrarInfo("No se encontraron préstamos para mostrar.");
				}else {
					for(int i = 0; i < cu.largo(); i++) {
						Usuario u = cu.obtenerUsuarioPorPosicion(i);
						ColeccionPrestamos cp = u.getPrestamosDeUsuario();
						for(int j = 0; j < cp.largo(); j++) {
							Prestamo p = cp.obtenerPrestamoPorPosicion(j);
							agregarFila(u,p);
						}
					}
				}
				mostrarInfo("Datos cargados exitosamente.");
			}catch (Exception ex) {
				mostrarError("Error al cargar los datos: " + ex.getMessage());
			}
		});
		
		this.btnBuscarPorID.addActionListener(e-> {
			int id = getIDUsuarioBuscado();
			if (id <= -1) {
		        mostrarError("Ingrese un ID válido.");
		        return;
		    }

		    //Verifica si los datos ya están cargados
		    modelo.ColeccionUsuarios cu = this.controlador.obtenerUsuarios();
		    if (cu == null || cu.vacia()) {
		        try {
		            this.controlador.cargarDatos();           //intenta cargar
		            cu = this.controlador.obtenerUsuarios();  //carga a los usuarios en memoria
		            if (cu == null || cu.vacia()) {
		                mostrarInfo("Datos no cargados. Verifique la ruta del archivo y vuelva a intentar.");
		                return;
		            }
		        } catch (Exception ex) {
		            mostrarError("Error al cargar datos: " + ex.getMessage());
		            return;
		        }
		    }

		    //Busca por id de usuario
		    limpiarTabla();
		    modelo.Usuario u = this.controlador.obtenerUsuarioPorId(id);
		    if (u == null) {
		        mostrarInfo("No existe un usuario con ese ID.");
		        return;
		    }

		    //Muestra préstamos
		    modelo.ColeccionPrestamos cp = this.controlador.obtenerPrestamosDeUsuario(id);
		    if (cp == null || cp.vacia()) {
		        mostrarInfo("El usuario no tiene préstamos para mostrar.");
		        return;
		    }

		    for (int i = 0; i < cp.largo(); i++) {
		        modelo.Prestamo p = cp.obtenerPrestamoPorPosicion(i);
		        agregarFila(u, p);
		    }
		});
	}
	
	public void setControladorCorreo(ActionListener al) {
		btnEnviar.addActionListener(al);
	}
	
    public void agregarFila(Usuario usuario, Prestamo prestamo) { 
    	this.modeloTabla.addRow(new Object[] {
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
    
    public void limpiarTabla() { 
    	modeloTabla.setRowCount(0); 
    }
    
    public void mostrarInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
	
 // Método que el controlador usará para obtener la fila seleccionada
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
}
