package persistencia;

import modelo.Fecha;
import modelo.Estudiante;
import modelo.ColeccionEstudiantes;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
				Fecha fecha;
                Cell celdaFecha = fila.getCell(4);
                if (celdaFecha.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(celdaFecha)) {
                    Date fechaExcel = celdaFecha.getDateCellValue();
                    LocalDate localDate = fechaExcel.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    fecha = new Fecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
                } else {
                    // Alternativa: parseo desde string manual si no es tipo fecha
                    String textoFecha = celdaFecha.toString().trim();
                    String[] partes = textoFecha.split("[/-]");
                    int dia = Integer.parseInt(partes[0]);
                    int mes = Integer.parseInt(partes[1]);
                    int anio = Integer.parseInt(partes[2]);
                    fecha = new Fecha(dia, mes, anio);
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
