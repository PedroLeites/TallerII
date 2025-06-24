package persistencia;

import modelo.Estudiante;
import modelo.ColeccionPrestamos;
import modelo.Libro;
import modelo.Prestamo;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
//Para .xlsx
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
//Para .xls
import jxl.read.biff.BiffException;

public class ImportadorExcel {

	public ColeccionPrestamos importarDeExcel(String rutaArchivo) {
		 if (rutaArchivo.endsWith(".xlsx")) {
	            return importarXLSX(rutaArchivo);
	        } else if (rutaArchivo.endsWith(".xls")) {
	            return importarXLS(rutaArchivo);
	        } else {
	            JOptionPane.showMessageDialog(null, "Formato no soportado: debe ser .xls o .xlsx", "Error", JOptionPane.ERROR_MESSAGE);
	            return new ColeccionPrestamos();
	        }
	    }
	
	// === Método para .xls (formato antiguo, usa JXL) ===
    private ColeccionPrestamos importarXLS(String rutaArchivo) {
    	ColeccionPrestamos prestamosVencidos = new ColeccionPrestamos();
    	
    	try (FileInputStream flujoEntrada = new FileInputStream(rutaArchivo)){
    		jxl.Workbook workbook = jxl.Workbook.getWorkbook(new File(rutaArchivo));
    		jxl.Sheet sheet = workbook.getSheet(0);

            for (int i = 3; i < sheet.getRows(); i++) {
            	jxl.Cell[] fila = sheet.getRow(i);
                if (fila.length < 16) continue;

                String fechaPrestamo = fila[0].getContents().trim();
                String fechaDevolucionPrevista = fila[1].getContents().trim();
                int diasRetraso = parsearEntero(fila[2].getContents());

                int idPMB = parsearEntero(fila[3].getContents());
                String apellido = fila[4].getContents().trim();
                String nombre = fila[5].getContents().trim();
                String email = fila[6].getContents().trim();
                int ci = parsearEntero(fila[8].getContents());

                String cote = fila[9].getContents().trim();
                String cb = fila[10].getContents().trim();
                String titulo = fila[14].getContents().trim();
                String tipoDoc = fila[15].getContents().trim();

                if (diasRetraso > 0) {
                    Estudiante est = new Estudiante(idPMB, ci, nombre + " " + apellido, email);
                    Libro libro = new Libro(cote, cb, titulo, tipoDoc);
                    Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucionPrevista, diasRetraso, est, libro);
                    prestamosVencidos.agregar(prestamo);
                }
            }
    	} catch (BiffException | java.io.IOException e) {
    		System.out.println("Error al importar el archivo Excel: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al leer archivo .xls: " + e.getMessage());
        }
    	
    	return prestamosVencidos;
    }
    
 // === Método para .xlsx (moderno, usa Apache POI) ===
    private ColeccionPrestamos importarXLSX(String rutaArchivo) {
        ColeccionPrestamos prestamosVencidos = new ColeccionPrestamos();

        try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                Row fila = sheet.getRow(i);
                if (fila == null) continue;

                String fechaPrestamo = getString(fila.getCell(0));
                String fechaDevolucion = getString(fila.getCell(1));
                int diasRetraso = parsearEntero(getString(fila.getCell(2)));

                int idPMB = parsearEntero(getString(fila.getCell(3)));
                String apellido = getString(fila.getCell(4));
                String nombre = getString(fila.getCell(5));
                String email = getString(fila.getCell(6));
                int ci = parsearEntero(getString(fila.getCell(8)));

                String cote = getString(fila.getCell(9));
                String cb = getString(fila.getCell(10));
                String titulo = getString(fila.getCell(14));
                String tipoDoc = getString(fila.getCell(15));

                if (diasRetraso > 0) {
                    Estudiante est = new Estudiante(idPMB, ci, nombre + " " + apellido, email);
                    Libro libro = new Libro(cote, cb, titulo, tipoDoc);
                    Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, diasRetraso, est, libro);
                    prestamosVencidos.agregar(prestamo);
                }
            }

            wb.close();

        } catch (Exception e) {
        	System.out.println("Error al importar el archivo Excel: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al leer archivo .xlsx: " + e.getMessage());
        }

        return prestamosVencidos;
    }
    
 // === Utilidades ===
    private int parsearEntero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private String getString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue()); // usar sin cast si querés decimales
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // o evaluarla
            default:
                return "";
        }
    }
    

	
}
