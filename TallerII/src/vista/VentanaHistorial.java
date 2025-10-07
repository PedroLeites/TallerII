package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import modelo.Fecha;
import modelo.Correo;
import modelo.Historial;
import controlador.ManejadoraDePersistencia;

public class VentanaHistorial extends JDialog{
	private final ManejadoraDePersistencia controladorPersistencia;
    private final int idUsuario;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnCerrar = new JButton("Cerrar");
    private JButton btnVerCuerpo = new JButton("Ver cuerpo");
    
    public VentanaHistorial(Frame owner, ManejadoraDePersistencia controlador, int idUsuario) {
    	super(owner, "Historial del usuario " + idUsuario, true);
        this.controladorPersistencia = controlador;
        this.idUsuario = idUsuario;

        construirUI();
        cargarDatos();
    }
    
    private void construirUI() {
    	String[] cols = { "Fecha", "Tipo", "Asunto", "Cuerpo (oculto)" };
        modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);

        // Ocultar columna "Cuerpo (oculto)" índice 3 (porque hay 4 columnas: 0..3)
        if (tabla.getColumnModel().getColumnCount() > 3) {
            tabla.getColumnModel().getColumn(3).setMinWidth(0);
            tabla.getColumnModel().getColumn(3).setMaxWidth(0);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(0);
        }

        // Opcional: ajustes visuales
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabla.setRowHeight(22);

        JScrollPane sp = new JScrollPane(tabla);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnVerCuerpo);
        botones.add(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());

        btnVerCuerpo.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un envío.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String fecha  = String.valueOf(modelo.getValueAt(row, 0)); // Fecha
            String tipo   = String.valueOf(modelo.getValueAt(row, 1)); // Tipo
            String asu    = String.valueOf(modelo.getValueAt(row, 2)); // Asunto
            String cuerpo = String.valueOf(modelo.getValueAt(row, 3)); // Cuerpo (oculto)

            JTextArea ta = new JTextArea(cuerpo);
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
            ta.setEditable(false);
            ta.setCaretPosition(0);

            JPanel panel = new JPanel(new BorderLayout(8,8));
            panel.add(new JLabel("<html><b>"+tipo+"</b> — "+fecha+"<br><i>"+asu+"</i></html>"), BorderLayout.NORTH);
            panel.add(new JScrollPane(ta), BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(this, panel, "Detalle del mensaje", JOptionPane.PLAIN_MESSAGE);
        });

        setLayout(new BorderLayout(8,8));
        add(sp, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        setSize(900, 500);
        setLocationRelativeTo(getOwner());
    }
    
    private void cargarDatos() {
        try {
            // 1) Solicita al controlador de persistencia la lista de envíos del usuario
            List<Historial> filas = controladorPersistencia.listarHistorialPorUsuario(idUsuario);

            // 2) Recorre cada elemento de la lista obtenida
            for (int i = 0; i < filas.size(); i++) {
                Historial historial = filas.get(i);

                String tipo = "";
                String asunto = "";
                String cuerpo = "";
                String fechaStr = "";

                // 3) Verifica que el historial tenga un objeto Correo asociado
                Correo correo = historial.getCorreo();
                if (correo != null) {
                    // 4) Obtiene el tipo de correo si está definido
                    if (correo.getTipo() != null) {
                        tipo = correo.getTipo().name();
                    } else {
                        tipo = "";
                    }

                    // 5) Obtiene el asunto si no es nulo
                    if (correo.getAsunto() != null) {
                        asunto = correo.getAsunto();
                    } else {
                        asunto = "";
                    }

                    // 6) Obtiene el cuerpo si no es nulo
                    if (correo.getMensaje() != null) {
                        cuerpo = correo.getMensaje();
                    } else {
                        cuerpo = "";
                    }

                    // 7) Obtiene la fecha de emisión si existe y la convierte a texto con formato
                    Fecha fechaEmision = correo.getFechaEmision();
                    if (fechaEmision != null) {
                        int dia = fechaEmision.getDia();
                        int mes = fechaEmision.getMes();
                        int anio = fechaEmision.getAnio();

                        // Convierte a formato dd/MM/yyyy con ceros a la izquierda
                        fechaStr = String.format("%02d/%02d/%04d", dia, mes, anio);
                    } else {
                        fechaStr = "";
                    }
                } else {
                    // Si el correo es nulo, todas las cadenas quedan vacías
                    tipo = "";
                    asunto = "";
                    cuerpo = "";
                    fechaStr = "";
                }

                // 8) Agrega una nueva fila a la tabla con los datos obtenidos
                Object[] fila = new Object[4];
                fila[0] = fechaStr;
                fila[1] = tipo;
                fila[2] = asunto;
                fila[3] = cuerpo;
                modelo.addRow(fila);
            }

            // 9) Ajusta el ancho de las columnas si existen
            if (tabla.getColumnCount() >= 3) {
                tabla.getColumnModel().getColumn(0).setPreferredWidth(100); // Columna Fecha
                tabla.getColumnModel().getColumn(1).setPreferredWidth(180); // Columna Tipo
                tabla.getColumnModel().getColumn(2).setPreferredWidth(520); // Columna Asunto
            }

        } catch (Exception ex) {
            // 10) Si ocurre un error, muestra un mensaje al usuario
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar historial: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
}
