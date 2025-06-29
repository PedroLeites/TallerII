package modelo;

import java.io.FileInputStream;
import jxl.Sheet;
import jxl.Workbook;

//Establece el vínculo entre datos y arrmar al usuario, prestamos, etc
public class ManejadoraDeDatos {
	private Datos datos;
    private ColeccionUsuarios usuarios;
    
    public ManejadoraDeDatos() {
        datos = new Datos();
        usuarios = new ColeccionUsuarios();
        importarDatos();
    }
    
    private void importarDatos() {
    	try {
    		Workbook libro = Workbook.getWorkbook(new FileInputStream(datos.getRuta()));
            Sheet hoja = libro.getSheet(0);
            
            for (int i = 3; i < hoja.getRows(); i++) {
            	//Fechas del préstamo
            	String fechaPrestamo = hoja.getCell(0, i).getContents().trim();
                String fechaDevolucion = hoja.getCell(1, i).getContents().trim();
                int diasRetraso = 1; //Falta calcular los días de retraso
                
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
    
}
