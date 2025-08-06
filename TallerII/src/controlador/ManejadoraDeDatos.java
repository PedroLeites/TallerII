package controlador;

//Para JExcelAPI
import java.io.FileInputStream;
import jxl.Sheet;
import jxl.Workbook;

//Para Apache POI
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    
    private void procesarCadena() {
    	usuarios = new ColeccionUsuarios(); // Reiniciar la colección de usuarios si ya se había cargado antes
    	
        // Obtenemos el contenido crudo del archivo Excel como una sola cadena
        String contenido = datos.getCadena();

        // Separamos esa cadena en filas usando el separador ';'
        String[] filas = contenido.split(";");

        // Iteramos desde i = 1 para saltar encabezados o título
        for (int i = 1; i < filas.length; i++) {
            String fila = filas[i].trim();

            // Si la fila está vacía, no procesamos
            if (fila.isEmpty()) continue;

            // Separamos la fila en celdas usando la coma como separador
            String[] celdas = fila.split(",");

            // Si no tiene al menos 15 celdas, descartamos
            if (celdas.length < 15) continue;

            // Si la primera celda no parece una fecha válida (dd/MM/yyyy), salteamos
            if (celdas[0].trim().length() < 8 || !celdas[0].trim().matches("\\d{2}/\\d{2}/\\d{4}")) continue;

            try {
                // Interpretamos las fechas
                Fecha fechaPrestamo = new Fecha(celdas[0].trim());
                Fecha fechaDevolucion = new Fecha(celdas[1].trim());
                long diasRetraso = calcularDiasRetraso(fechaPrestamo, fechaDevolucion);

                // Datos del usuario
                int idUsuario = (int) Double.parseDouble(celdas[3].trim());
                String apellido = celdas[4].trim();
                String nombre = celdas[5].trim();
                String correo = celdas[6].trim();
                int ci = (int) Double.parseDouble(celdas[8].trim());

                // Datos del libro
                int idLibro = (int) Double.parseDouble(celdas[10].trim());
                String titulo = celdas[14].trim();

                // Creamos el préstamo
                Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, diasRetraso, idLibro, titulo);

                // Si el usuario ya existe, lo reutilizamos; si no, lo creamos
                Usuario usuario;
                if (usuarios.pertenece(idUsuario)) {
                    usuario = usuarios.obtenerUsuario(idUsuario);
                } else {
                    usuario = new Usuario(idUsuario, ci, nombre, apellido, correo);
                    usuarios.agregarUsuario(usuario);
                    System.out.println("Importando usuario ID: " + idUsuario + " - " + nombre + " " + apellido);
                }

                // Asociamos el préstamo al usuario
                usuario.agregarPrestamo(prestamo);

            } catch (Exception e) {
                // Capturamos errores de formato y los informamos por consola
                System.out.println("Error procesando fila " + i + ": " + e.getMessage());
            }
        }

        // Imprimir cuántos usuarios se importaron al final
        System.out.println("Total de usuarios importados: " + usuarios.largo());
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
    	    procesarCadena();
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
    

    private long calcularDiasRetraso(Fecha fechaPrestamo, Fecha fechaDevolucionPrevista) {
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
            procesarCadena();
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
    
    /*private void importarDatos() {
	String rutaArchivo = datos.getRuta();
	
	try {
		if (rutaArchivo.endsWith(".xls")) {
    		//con JExcelAPI 
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
            
    	}else if(rutaArchivo.endsWith(".xlsx")) {
    		//con Apache POI
    		java.io.FileInputStream fis = new java.io.FileInputStream(rutaArchivo);
    		org.apache.poi.ss.usermodel.Workbook libro = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis);
    		org.apache.poi.ss.usermodel.Sheet hoja = libro.getSheetAt(0);

            for (int i = 3; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (fila == null) continue;

                String fechaPrestamoStr = fila.getCell(0).toString().trim();
                String fechaDevolucionStr = fila.getCell(1).toString().trim();
                
                int idUsuario = (int) Double.parseDouble(fila.getCell(3).toString().trim());
                String apellido = fila.getCell(4).toString().trim();
                String nombre = fila.getCell(5).toString().trim();
                String correo = fila.getCell(6).toString().trim();
                int ci = (int) Double.parseDouble(fila.getCell(8).toString().trim());
                int idLibro = (int) Double.parseDouble(fila.getCell(10).toString().trim());
                String titulo = fila.getCell(14).toString().trim();

                Fecha fechaPrestamo = new Fecha(fechaPrestamoStr);
                Fecha fechaDevolucion = new Fecha(fechaDevolucionStr);
                int diasRetraso = calcularDiasRetraso(fechaPrestamo, fechaDevolucion);

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
            fis.close();
    	}
		
	}catch (Exception e) {
		System.out.println("Error al importar datos: " + e.getMessage());
	}
}*/

    
}
