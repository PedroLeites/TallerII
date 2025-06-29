package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Datos {
	private String ruta;
    private String cadena;
    
    public Datos() {
    	this.ruta = "datos.xls"; // Ruta por defecto
        this.cadena = "";
        
        try {
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
            
        } catch (BiffException | java.io.IOException e) {
        	System.out.println("Error al leer el archivo Excel: " + e.getMessage());
        }
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
