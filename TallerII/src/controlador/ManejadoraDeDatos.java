package controlador;

import java.io.FileInputStream;
import jxl.Sheet;
import jxl.Workbook;

import modelo.Fecha;
import modelo.ColeccionUsuarios;
import modelo.ColeccionPrestamos;
import java.util.ArrayList;
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
            	Fecha fechaPrestamo = new Fecha(hoja.getCell(0, i).getContents().trim());  // aff_pret_date
                Fecha fechaDevolucion = new Fecha(hoja.getCell(1, i).getContents().trim()); // aff_pret_retour
                int diasRetraso = calcularDiasRetraso(fechaPrestamo, fechaDevolucion);
                
                //Datos del usuario
                int idUsuario = Integer.parseInt(hoja.getCell(3, i).getContents().trim()); // primer id_empr
                String apellido = hoja.getCell(4, i).getContents().trim();  // empr_nom
                String nombre = hoja.getCell(5, i).getContents().trim();   // empr_prenom
                String correo = hoja.getCell(6, i).getContents().trim();   // empr_mail
                int ci = Integer.parseInt(hoja.getCell(8, i).getContents().trim()); // empr_cb
                
                //Datos del libro
                int idLibro = Integer.parseInt(hoja.getCell(10, i).getContents().trim()); // expl_cb
                String titulo = hoja.getCell(14, i).getContents().trim(); // tit
                
                Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, diasRetraso, idLibro, titulo);
                
                Usuario usuario;
                if (usuarios.pertenece(idUsuario)) {
                    usuario = usuarios.obtenerUsuario(idUsuario);
                } else {
                    usuario = new Usuario(idUsuario, ci, nombre, apellido, correo);
                    System.out.println("Importando usuario ID: " + idUsuario + " - " + nombre + " " + apellido);
                    usuarios.agregarUsuario(usuario);
                    System.out.println("Total de usuarios: " + usuarios.largo());
                }

                usuario.agregarPrestamo(prestamo);
            }
            libro.close();
    	}catch (Exception e) {
    		System.out.println("Error al importar datos: " + e.getMessage());
    	}
    }
    
    private void mostrarTodosLosPrestamos() {
        ArrayList<Usuario> listaUsuarios = usuarios.getUsuarios();

        for (Usuario usuario : listaUsuarios) {
            ArrayList<Prestamo> listaPrestamos = usuario.getPrestamos().getPrestamos();

            for (Prestamo prestamo : listaPrestamos) {
                vista.agregarFila(new Object[]{
                    prestamo.getFechaPrestamo().toString(),
                    prestamo.getFechaDevolucionPrevista().toString(),
                    prestamo.getDiasRetraso(),
                    usuario.getId(),
                    usuario.getNombreCompleto(),
                    usuario.getCorreo(),
                    prestamo.getTituloLibro(),
                    prestamo.getIdLibro()
                });
            }
        }
    }
    
    public void mostrarPrestamosDeUsuario(int idUsuario) {
    	/*if (usuarios.vacia()) {
    	    JOptionPane.showMessageDialog(vista, "Primero cargá los datos.", "Aviso", JOptionPane.WARNING_MESSAGE);
    	    return;
    	}*/
    	
    	if (usuarios.vacia()) {
    	    importarDatos();
    	}
    	
        vista.limpiarTabla();

        if (!usuarios.pertenece(idUsuario)) {
            JOptionPane.showMessageDialog(vista, "No existe un usuario con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarios.obtenerUsuario(idUsuario);
        ArrayList<Prestamo> listaPrestamos = usuario.getPrestamos().getPrestamos();

        for (Prestamo prestamo : listaPrestamos) {
            vista.agregarFila(new Object[]{
                prestamo.getFechaPrestamo().toString(),
                prestamo.getFechaDevolucionPrevista().toString(),
                prestamo.getDiasRetraso(),
                usuario.getId(),
                usuario.getNombreCompleto(),
                usuario.getCorreo(),
                prestamo.getTituloLibro(),
                prestamo.getIdLibro()
            });
        }
    }
    

    private int calcularDiasRetraso(Fecha fechaPrestamo, Fecha fechaDevolucionPrevista) {
    	Fecha hoy = new Fecha(java.time.LocalDate.now().getDayOfMonth(),
                java.time.LocalDate.now().getMonthValue(),
                java.time.LocalDate.now().getYear());

    	if (hoy.toLocalDate().isAfter(fechaDevolucionPrevista.toLocalDate())) {
    		return Fecha.diasEntre(fechaDevolucionPrevista, hoy);
    	}
    	
    	return 0;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == vista.getBotonCargar()) {
            vista.limpiarTabla();
            importarDatos();
            mostrarTodosLosPrestamos();
            JOptionPane.showMessageDialog(vista, "Datos cargados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

        if (fuente == vista.getBotonBuscarPorID()) {
            int idBuscado = vista.getIDUsuarioBuscado();
            if (idBuscado == -1) {
                JOptionPane.showMessageDialog(vista, "Ingrese un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	System.out.println("==== DEBUG ====");
            	System.out.println("ID ingresado por el usuario: " + idBuscado);
            	System.out.println("Usuarios en memoria:");
            	for (Usuario u : usuarios.getUsuarios()) {
            	    System.out.println("ID: " + u.getId() + " - Nombre: " + u.getNombreCompleto());
            	}
            	System.out.println("===============");
                mostrarPrestamosDeUsuario(idBuscado);
            }
        }
    }

    public ColeccionUsuarios getUsuarios() {
        return usuarios;
    }

    public Datos getDatos() {
        return datos;
    }
    
}
