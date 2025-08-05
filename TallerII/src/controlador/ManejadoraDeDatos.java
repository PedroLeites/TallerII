package controlador;

import java.io.FileInputStream;
import jxl.Sheet;
import jxl.Workbook;

import modelo.ColeccionUsuarios;
import modelo.Datos;
import modelo.Prestamo;
import modelo.Usuario;

import vista.DatosVista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ManejadoraDeDatos implements ActionListener {
	private Datos datos;
    private ColeccionUsuarios usuarios;
    private DatosVista vista;
    
    public ManejadoraDeDatos(DatosVista vista) {
    	this.vista = vista;
        this.datos = new Datos();
        this.usuarios = new ColeccionUsuarios();
        this.vista.setControladorDatos(this);
    }
    
    private void importarDatos() {
    	try {
    		Workbook libro = Workbook.getWorkbook(new FileInputStream(datos.getRuta()));
            Sheet hoja = libro.getSheet(0);
            
            for (int i = 3; i < hoja.getRows(); i++) {
            	//Fechas del préstamo
            	String fechaPrestamo = hoja.getCell(0, i).getContents().trim();
                String fechaDevolucion = hoja.getCell(1, i).getContents().trim();
                int diasRetraso = calcularDiasRetraso(fechaPrestamo, fechaDevolucion);
                
                //Datos del usuario
                int idUsuario = Integer.parseInt(hoja.getCell(3, i).getContents().trim());
                String apellido = hoja.getCell(4, i).getContents().trim();
                String nombre = hoja.getCell(5, i).getContents().trim();
                String correo = hoja.getCell(6, i).getContents().trim();
                int ci = Integer.parseInt(hoja.getCell(8, i).getContents().trim());
                
                //Datos del libro
                int idLibro = Integer.parseInt(hoja.getCell(10, i).getContents().trim());
                String titulo = hoja.getCell(14, i).getContents().trim();
                
                Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, diasRetraso, idLibro, titulo);
                
                Usuario usuario;
                if (usuarios.pertenece(idUsuario)) {
                    usuario = usuarios.obtenerUsuario(idUsuario);
                } else {
                    usuario = new Usuario(idUsuario, ci, nombre, apellido, correo);
                    usuarios.agregarUsuario(usuario);
                }

                usuario.agregarPrestamo(prestamo);
            }
            libro.close();
    	}catch (Exception e) {
    		System.out.println("Error al importar datos: " + e.getMessage());
    	}
    }
    
    public ColeccionUsuarios getUsuarios() {
        return usuarios;
    }

    public Datos getDatos() {
        return datos;
    }
    
    private int calcularDiasRetraso(String fechaPStr, String fechaDPStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Formato de la fecha en el archivo
            LocalDate fechaPrestamo = LocalDate.parse(fechaPStr, formatter); //Formateamos la fecha de String a LocalDate
            LocalDate fechaDevolucionPrevista = LocalDate.parse(fechaDPStr, formatter); //Formateamos la fecha de String a LocalDate
            LocalDate hoy = LocalDate.now(); //Fecha actual

            // Si la devolución era para el pasado y todavía no se devolvió, se calcula el atraso.
            if (hoy.isAfter(fechaDevolucionPrevista)) {
                return (int) ChronoUnit.DAYS.between(fechaDevolucionPrevista, hoy);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error al calcular días de retraso: " + e.getMessage());
            return 0;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        vista.limpiarTabla();
        importarDatos();

        for (Usuario usuario : usuarios.getUsuarios().values()) {
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

        JOptionPane.showMessageDialog(vista, "Datos cargados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    
}
