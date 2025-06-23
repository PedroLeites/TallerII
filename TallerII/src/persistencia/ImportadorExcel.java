package persistencia;

import modelo.Estudiante;
import modelo.ColeccionPrestamos;
import modelo.Libro;
import modelo.Prestamo;
//import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.CellType; // Importar CellType
import java.io.FileInputStream;

public class ImportadorExcel {

	public ColeccionPrestamos importarDeExcel(String rutaArchivo) {
		ColeccionPrestamos prestamosVencidos = new ColeccionPrestamos();
		
		try (FileInputStream flujoEntrada = new FileInputStream(rutaArchivo)){
			
			XSSFWorkbook wb = new XSSFWorkbook(flujoEntrada); //Toma el contenido leído desde el archivo y lo convierte en una estructura de datos compatible con Java
			XSSFSheet hoja = wb.getSheetAt(0); //Usamos la primera hoja
			
			// Iterar sobre las filas, empezando desde la segunda fila (índice 1) para omitir los encabezados
			for (int i = 1; i <= hoja.getLastRowNum(); i++) {
				XSSFRow fila = hoja.getRow(i);
                if (fila == null) {
                    continue; // Saltar filas vacías
                }
                
                // Extraer datos de la fila
                // Asegurarse de manejar diferentes tipos de celdas (String, Numeric, etc)
                String fechaPrestamo = obtenerCeldaString(fila, 0); // aff_pret_date
                String fechaDevolucionPrevista = obtenerCeldaString(fila, 1); // aff_pret_retour
                int diasRetraso = (int) obtenerCeldaNumeric(fila, 2); // retard
                
                int idEmprPMB = (int) obtenerCeldaNumeric(fila, 3); // id_empr
                String emprNom = obtenerCeldaString(fila, 4); // empr_nom
                String emprPrenom = obtenerCeldaString(fila, 5); // empr_prenom
                String emprMail = obtenerCeldaString(fila, 6); // empr_mail
                int emprCb = (int) obtenerCeldaNumeric(fila, 7); // empr_cb
                
                String explCote = obtenerCeldaString(fila, 8); // expl_cote
                String explCb = obtenerCeldaString(fila, 9); // expl_cb
                String tit = obtenerCeldaString(fila, 11); // tit (columna 11, ya que la 10 es expl_notice)
                String tdocLibelle = obtenerCeldaString(fila, 12); // tdoc_libelle
                
                // Crear objetos Estudiante, Libro y Prestamo
                String nombreCompletoEstudiante = emprNom + " " + emprPrenom;
                Estudiante estudiante = new Estudiante(idEmprPMB, emprCb, nombreCompletoEstudiante, emprMail);
                
                Libro libro = new Libro(explCote, explCb, tit, tdocLibelle);
                
                Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucionPrevista, diasRetraso, estudiante, libro);
                
                prestamosVencidos.agregar(prestamo);
                
			}
			
			wb.close();
			
		}catch (Exception e) {
			System.out.println("Error al importar el archivo Excel: " + e.getMessage());
		}
		
		return prestamosVencidos;
		
	}
	
	//Métodos auxiliares
	// Método auxiliar para obtener el valor de una celda como String
    private String obtenerCeldaString(XSSFRow row, int cellIndex) {
        if (row.getCell(cellIndex) == null) {
            return "";
        }
        // Manejar diferentes tipos de celdas
        if (row.getCell(cellIndex).getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) row.getCell(cellIndex).getNumericCellValue());
        } else {
            return row.getCell(cellIndex).getStringCellValue().trim();
        }
    }
    
    // Método auxiliar para obtener el valor de una celda como Numeric
    private double obtenerCeldaNumeric(XSSFRow row, int cellIndex) {
        if (row.getCell(cellIndex) == null || row.getCell(cellIndex).getCellType() != CellType.NUMERIC) {
            return 0.0; // Devolvemos un valor por defecto si la celda no es numérica
        }
        return row.getCell(cellIndex).getNumericCellValue();
    }
	
}
