package persistencia;

import modelo.Fecha;
import modelo.Estudiante;
import modelo.ColeccionEstudiantes;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ImportadorExcel {

	public ColeccionEstudiantes importarDeExcel(String rutaArchivo) {
		ColeccionEstudiantes coleccion = new ColeccionEstudiantes();
		
		try (FileInputStream flujoEntrada = new FileInputStream(rutaArchivo)){
			
			XSSFWorkbook wb = new XSSFWorkbook(flujoEntrada); //Toma el contenido leído desde el archivo y lo convierte en una estructura de datos compatible con Java
			XSSFSheet hoja = wb.getSheetAt(0); //Usamos la primera hoja
			
			for (int i = 1; i <= hoja.getLastRowNum(); i++) { //Comenzamos desde la segunda fila
				XSSFRow fila = hoja.getRow(i);
				
				//int ci = (int) fila.getCell(0).getNumericCellValue(); 
				String ciTexto = fila.getCell(0).toString().trim();
				int ci = Integer.parseInt(ciTexto); //CI
				String nombre = fila.getCell(1).getStringCellValue();//Nombre
				String email = fila.getCell(2).getStringCellValue();//Email
				String libro = fila.getCell(3).getStringCellValue();//Libro
				
				//Fecha
				String fechaTexto = fila.getCell(4).toString().trim();
				Fecha fecha;
				try {
				    // Intenta parsear como yyyy-MM-dd
				    LocalDate localDate = LocalDate.parse(fechaTexto, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				    fecha = new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
				} catch (DateTimeParseException ex) {
				    // Si falla, intentamos con dd/MM/yyyy
				    LocalDate localDate = LocalDate.parse(fechaTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				    fecha = new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
				}
				
				Estudiante estudiante = new Estudiante(ci,nombre,email,libro,fecha);
		        coleccion.agregar(estudiante); //Agregamos el estudiante a la colección
			}
			wb.close();
			
		}catch (Exception e) {
			System.out.println("Error al importar el archivo Excel: " + e.getMessage());
		}
		return coleccion;
		
	}
	
}
