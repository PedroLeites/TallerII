package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import controlador.ManejadoraDeDatos;
import controlador.ManejadoraDeCorreos;
import controlador.ManejadoraDePersistencia; 

import modelo.Usuario;
import modelo.Prestamo;
import modelo.ColeccionUsuarios;
import modelo.ColeccionPrestamos;

public class DatosVista extends JFrame {
	private ManejadoraDeDatos controladorDatos;
	private ManejadoraDeCorreos controladorCorreos;
	private ManejadoraDePersistencia controladorPersistencia;
	
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	
	private JButton btnCargar = new JButton("Cargar datos");
	private JTextField campoBuscarID = new JTextField(10);
	private JButton btnBuscarPorID = new JButton("Buscar por ID");
	private JButton btnConsultarHistorial = new JButton("Consultar historial por ID");
	
	private JButton btnNotifFila = new JButton("Notificar atraso (fila)");
    private JButton btnNotifUsuario = new JButton("Notificar atraso (usuario)");
    private JButton btnConstFila = new JButton("Constancia (fila)");
    private JButton btnConstUsuario = new JButton("Constancias (usuario)");
    private JButton btnNotifTodos = new JButton("Notificar a todos");
	
	JPanel panelBotones = new JPanel();
	JPanel panelCorreo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
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
        
        panelBotones.add(new JLabel("ID Usuario:"));
        panelBotones.add(campoBuscarID);
        panelBotones.add(btnBuscarPorID);
        panelBotones.add(btnConsultarHistorial);
        
        this.add(panelBotones, BorderLayout.SOUTH);
        
        panelCorreo.add(btnNotifTodos);
        panelCorreo.add(btnNotifFila);
        panelCorreo.add(btnNotifUsuario);
        panelCorreo.add(btnConstFila);
        panelCorreo.add(btnConstUsuario);

        JPanel south = new JPanel(new GridLayout(2, 1));
        south.add(panelBotones);
        south.add(panelCorreo);

        this.add(south, BorderLayout.SOUTH);
        
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); //Centrar la ventana
	}
	
	public void setControladorDatos(ManejadoraDeDatos controlador) { 
		this.controladorDatos = controlador;
		
		this.btnCargar.addActionListener(e -> {
			try {
				limpiarTabla();
				this.controladorDatos.cargarDatos();
				
				ColeccionUsuarios cu = this.controladorDatos.obtenerUsuarios();
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
		    modelo.ColeccionUsuarios cu = this.controladorDatos.obtenerUsuarios();
		    if (cu == null || cu.vacia()) {
		        try {
		            this.controladorDatos.cargarDatos();           //intenta cargar
		            cu = this.controladorDatos.obtenerUsuarios();  //carga a los usuarios en memoria
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
		    modelo.Usuario u = this.controladorDatos.obtenerUsuarioPorId(id);
		    if (u == null) {
		        mostrarInfo("No existe un usuario con ese ID.");
		        return;
		    }

		    //Muestra préstamos
		    modelo.ColeccionPrestamos cp = this.controladorDatos.obtenerPrestamosDeUsuario(id);
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
	
	public void setControladorCorreo(ManejadoraDeCorreos controlador) {
		this.controladorCorreos = controlador;
		
		// Notificar atraso por préstamo (fila)
        btnNotifFila.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) {
                mostrarError("Seleccione una fila primero.");
                return;
            }

            String destinatario = String.valueOf(modeloTabla.getValueAt(row, 5));
            int cedula = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(row, 3)));
            String nombre = String.valueOf(modeloTabla.getValueAt(row, 4));
            int idLibro = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(row, 7)));
            String titulo = String.valueOf(modeloTabla.getValueAt(row, 6));
            String fPrest = String.valueOf(modeloTabla.getValueAt(row, 0));
            String fDev = String.valueOf(modeloTabla.getValueAt(row, 1));
            long dias = Long.parseLong(String.valueOf(modeloTabla.getValueAt(row, 2)));

            try {
                controladorCorreos.enviarNotificacionAtrasoPorPrestamo(
                    destinatario, cedula, nombre, idLibro, titulo, fPrest, fDev, dias
                );
                mostrarInfo("Notificación enviada correctamente.");
            } catch (RuntimeException ex) {
            	mostrarError("No se pudo enviar la notificación.\n" + ex.getMessage());
            }
            
        });
        
     // Notificar atraso por usuario (todas las filas visibles del mismo ID)
        btnNotifUsuario.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) {
                mostrarError("Seleccione una fila para identificar al usuario.");
                return;
            }

            int idUsuario = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(row, 3)));
            String destinatario = String.valueOf(modeloTabla.getValueAt(row, 5));
            String nombre = String.valueOf(modeloTabla.getValueAt(row, 4));
            int total = modeloTabla.getRowCount();

            ArrayList<Integer> ids = new ArrayList<Integer>();
            ArrayList<String> titulos = new ArrayList<String>();
            ArrayList<String> fPrest = new ArrayList<String>();
            ArrayList<String> fDev = new ArrayList<String>();
            ArrayList<Long> dias = new ArrayList<Long>();

            for (int r = 0; r < total; r++) {
                int idU = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(r, 3)));
                if (idU == idUsuario) {
                    ids.add(Integer.parseInt(String.valueOf(modeloTabla.getValueAt(r, 7))));
                    titulos.add(String.valueOf(modeloTabla.getValueAt(r, 6)));
                    fPrest.add(String.valueOf(modeloTabla.getValueAt(r, 0)));
                    fDev.add(String.valueOf(modeloTabla.getValueAt(r, 1)));
                    dias.add(Long.parseLong(String.valueOf(modeloTabla.getValueAt(r, 2))));
                }
            }

            if (ids.isEmpty()) {
                mostrarInfo("No hay préstamos visibles para ese usuario.");
                return;
            }

            try {
            	controladorCorreos.enviarNotificacionAtrasoPorUsuario(
                        destinatario, idUsuario, nombre, ids, titulos, fPrest, fDev, dias
                    );
            	mostrarInfo("Notificación enviada correctamente.");
            } catch (RuntimeException ex) {
            	mostrarError("No se pudo enviar la notificación.\n" + ex.getMessage());
            }
       
        });

        // Constancia por préstamo (fila)
        btnConstFila.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) {
                mostrarError("Seleccione una fila primero.");
                return;
            }

            String destinatario = String.valueOf(modeloTabla.getValueAt(row, 5));
            int cedula = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(row, 3)));
            String nombre = String.valueOf(modeloTabla.getValueAt(row, 4));
            int idLibro = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(row, 7)));
            String titulo = String.valueOf(modeloTabla.getValueAt(row, 6));
            String fPrest = String.valueOf(modeloTabla.getValueAt(row, 0));
            String fDev = String.valueOf(modeloTabla.getValueAt(row, 1));
  
            try {
            	controladorCorreos.enviarConstanciaPorPrestamo(
                        destinatario, cedula, nombre, idLibro, titulo, fPrest, fDev
                    );
            	mostrarInfo("Notificación enviada correctamente.");
            } catch (RuntimeException ex) {
            	mostrarError("No se pudo enviar la notificación.\n" + ex.getMessage());
            }
            
        });

        // Constancias por usuario (todas las filas visibles del mismo ID)
        btnConstUsuario.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) {
                mostrarError("Seleccione una fila para identificar al usuario.");
                return;
            }

            int idUsuario = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(row, 3)));
            String destinatario = String.valueOf(modeloTabla.getValueAt(row, 5));
            String nombre = String.valueOf(modeloTabla.getValueAt(row, 4));
            int total = modeloTabla.getRowCount();

            ArrayList<Integer> ids = new ArrayList<Integer>();
            ArrayList<String> titulos = new ArrayList<String>();
            ArrayList<String> fPrest = new ArrayList<String>();
            ArrayList<String> fDev = new ArrayList<String>();

            for (int r = 0; r < total; r++) {
                int idU = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(r, 3)));
                if (idU == idUsuario) {
                    ids.add(Integer.parseInt(String.valueOf(modeloTabla.getValueAt(r, 7))));
                    titulos.add(String.valueOf(modeloTabla.getValueAt(r, 6)));
                    fPrest.add(String.valueOf(modeloTabla.getValueAt(r, 0)));
                    fDev.add(String.valueOf(modeloTabla.getValueAt(r, 1)));
                }
            }

            if (ids.isEmpty()) {
                mostrarInfo("No hay préstamos visibles para ese usuario.");
                return;
            }
 
            try {
            	controladorCorreos.enviarConstanciasPorUsuario(
                        destinatario, idUsuario, nombre, ids, titulos, fPrest, fDev
                    );
            	mostrarInfo("Notificación enviada correctamente.");
            } catch (RuntimeException ex) {
            	mostrarError("No se pudo enviar la notificación.\n" + ex.getMessage());
            }
            
        });
        
        //Enviar notificación de atraso a todos los usuarios
        btnNotifTodos.addActionListener(e -> {
        	int total = modeloTabla.getRowCount();
            if (total == 0) {
                mostrarInfo("No hay datos cargados para notificar.");
                return;
            }
            
            //Agrupar filas por ID de usuario
            Map<Integer, ManejadoraDeCorreos.DatosUsuarioAtraso> mapa = new HashMap<>();
            
            for (int r = 0; r < total; r++) {
            	int idUsuario = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(r, 3)));
                String destinatario = String.valueOf(modeloTabla.getValueAt(r, 5));
                String nombre = String.valueOf(modeloTabla.getValueAt(r, 4));
                int idLibro = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(r, 7)));
                String titulo = String.valueOf(modeloTabla.getValueAt(r, 6));
                String fPrest = String.valueOf(modeloTabla.getValueAt(r, 0));
                String fDev = String.valueOf(modeloTabla.getValueAt(r, 1));
                long dias = Long.parseLong(String.valueOf(modeloTabla.getValueAt(r, 2)));

                ManejadoraDeCorreos.DatosUsuarioAtraso dato = mapa.get(idUsuario);
                if (dato == null) {
                    dato = new ManejadoraDeCorreos.DatosUsuarioAtraso();
                    dato.destinatario = destinatario;
                    dato.nombre = nombre;
                    mapa.put(idUsuario, dato);
                }
                dato.ids.add(idLibro);
                dato.titulos.add(titulo);
                dato.fPrest.add(fPrest);
                dato.fDev.add(fDev);
                dato.dias.add(dias);
            }
            
            //Enviar a todos
            try {
                controladorCorreos.enviarNotificacionesAtrasoATodos(mapa);
                mostrarInfo("Se enviaron las notificaciones a todos los usuarios visibles.");
            } catch (RuntimeException ex) {
            	mostrarError("Ocurrió un error al enviar algunas notificaciones.\n" + ex.getMessage());
            }
            
        });
        
}
	
	public void setControladorPersistencia(ManejadoraDePersistencia controlador) {
		this.controladorPersistencia = controlador;
		
		//Consutar Historial
		btnConsultarHistorial.addActionListener(e -> {
			int id = getIDUsuarioBuscado();
            if (id <= -1) {
                mostrarError("Ingrese un ID válido.");
                return;
            }
            System.out.println("Consultar historial ID=" + id);
            
            VentanaHistorial vh = new VentanaHistorial(this, controladorPersistencia, id);
            vh.setVisible(true);
		});
		
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