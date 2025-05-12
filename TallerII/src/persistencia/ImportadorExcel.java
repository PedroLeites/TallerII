package persistencia;

import modelo.Estudiante;
import modelo.ColeccionEstudiantes;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileInputStream;

public class ImportadorExcel {

	public ColeccionEstudiantes importarDeExcel(String rutaArchivo) {
		ColeccionEstudiantes coleccion = new ColeccionEstudiantes();
		
		try (FileInputStream flujoEntrada = new FileInputStream(rutaArchivo)){
			
			XSSFWorkbook wb = new XSSFWorkbook(flujoEntrada); //Toma el contenido leído desde el archivo y lo convierte en una estructura de datos compatible con Java
			XSSFSheet hoja = wb.getSheetAt(0); //Usamos la primera hoja
			
			for (int i = 1; i <= hoja.getLastRowNum(); i++) { //Comenzamos desde la segunda fila
				XSSFRow fila = hoja.getRow(i);
				
				int ci = (int) fila.getCell(0).getNumericCellValue(); //CI
				String nombre = fila.getCell(1).getStringCellValue();//Nombre
				String email = fila.getCell(2).getStringCellValue();//Email
				String libro = fila.getCell(3).getStringCellValue();//Libro
				String fecha = fila.getCell(4).getStringCellValue();//Fecha
				
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
