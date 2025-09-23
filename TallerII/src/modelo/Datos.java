package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Datos {
	private String ruta;
    private String cadena;
    
    public Datos() {
    	this.ruta = "datos2.xlsx"; //Ruta por defecto
        this.cadena = "";
        
        try {
        	if (ruta.endsWith(".xls")) {
        		//JExcelAPI para .xls
        		Workbook libro = Workbook.getWorkbook(new FileInputStream(ruta));
                Sheet hoja = libro.getSheet(0);
                
                for (int i = 0; i < hoja.getRows(); i++) {
                    for (int j = 0; j < hoja.getColumns(); j++) {
                        cadena += hoja.getCell(j, i).getContents().trim();
                        if (j < hoja.getColumns() - 1) cadena += ", ";
                    }
                    cadena += "; ";
                }
                
                libro.close();
        	}else if (ruta.endsWith(".xlsx")) {
                //Apache POI para .xlsx
        		java.io.FileInputStream fis = new java.io.FileInputStream(ruta);
        		org.apache.poi.ss.usermodel.Workbook libro = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis);
        		org.apache.poi.ss.usermodel.Sheet hoja = libro.getSheetAt(0);

                for (int i = 0; i <= hoja.getLastRowNum(); i++) {
                    Row fila = hoja.getRow(i);
                    if (fila == null) continue;

                    for (int j = 0; j < fila.getLastCellNum(); j++) {
                        Cell celda = fila.getCell(j);
                        if (celda != null) {
                            cadena += celda.toString().trim();
                        }
                        if (j < fila.getLastCellNum() - 1) cadena += ", ";
                    }
                    cadena += "; ";
                }

                libro.close();
                fis.close();
            } else {
                cadena = "Formato de archivo no soportado.";
            }
        	
            
        } catch (BiffException | java.io.IOException e) {
        	System.out.println("Error al leer el archivo Excel: " + e.getMessage());
        }
        System.out.println("==== CONTENIDO DE LA CADENA ====");
        System.out.println(cadena);
        System.out.println("================================");
    }
    
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

}
