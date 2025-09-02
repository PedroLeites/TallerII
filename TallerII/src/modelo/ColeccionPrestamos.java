package modelo;

import java.util.ArrayList;

public class ColeccionPrestamos {
	private ArrayList<Prestamo> prestamos;
	
	//Constructor
	public ColeccionPrestamos() {
		prestamos = new ArrayList<>();
	}
	
	//Getter y Setter
	//Devuelve todos los pr�stamos
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    //Establece la colecci�n de pr�stamos
    public void setPrestamos(ArrayList<Prestamo> p) {
    	this.prestamos = p;
    }
    
    //M�todos espec�ficos
	//Agrega un nuevo pr�stamo
	public void agregarPrestamo(Prestamo p) {
		 prestamos.add(p);
	}
	
    //Elimina un pr�stamo por la ID del libro
	public void eliminarPrestamo(int idLibro) {
	    for (int i = 0; i < prestamos.size(); i++) {
	        if (prestamos.get(i).getIdLibro() == idLibro) {
	            prestamos.remove(i);
	        }
	    }
	}
	
	//Dada la id de un ejemplar, devuelve el prestamo encontrado
	public Prestamo obtenerPrestamo(int id) {
		Prestamo encontrado = new Prestamo();
		for (int i = 0; i < prestamos.size(); i++) {
			if (prestamos.get(i).getIdLibro() == id) {
				encontrado = prestamos.get(i);
			}
		}
		return encontrado;
	}
	
    public Prestamo obtenerPrestamoPorPosicion(int posicion) {
        if (posicion >= 0 && posicion < prestamos.size()) {
            return prestamos.get(posicion);
        } else {
            return null;
        }
    }
		
    //Devuelve la cantidad total de pr�stamos
    public int largo() {
        return prestamos.size();
    }
    
    //Verifica si la colecci�n de pr�stamos est� vac�a
    public boolean vacia() {
        return prestamos.isEmpty();
    }
    
    //Dada la id de un ejemplar, verifica si pertenece a la colecci�n de pr�stamos
    public boolean pertenece(int id) {
    	boolean esta = false;
        for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getIdLibro() == id) {
                esta = true;
            }
        }
        return esta;
    }
    
    //toString
    @Override
    public String toString() {
    	//Verifica si la colecci�n es nula o vac�a
        if (prestamos == null || prestamos.isEmpty()) {
            return "ColeccionPrestamos {total=0}" + System.lineSeparator() + "(la colecci�n est� vac�a)";
        }

        //Usamos StringBuilder para armar la cadena de salida
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        
        sb.append("ColeccionPrestamos {total=").append(prestamos.size()).append("}").append(ls); //Encabezado con el total de pr�stamos
        sb.append("----------------------------------------------------------------------------").append(ls); //L�nea separadora
        sb.append(String.format("%-6s | %-35s | %-12s | %-12s | %-6s%n",
                "ID", "T�tulo", "Prest.", "Dev.Prev.", "Retr.")); //Encabezados de las columnas
        sb.append("----------------------------------------------------------------------------").append(ls);

        for (int i = 0; i < prestamos.size(); i++) { //Recorremos la lista de pr�stamos
            Prestamo p = prestamos.get(i);

            if (p != null) { //Validamos que el pr�stamo no sea nulo
            	String titulo = p.getTituloLibro(); //Columna T�tulo
                if (titulo == null) {
                    titulo = "(sin t�tulo)";
                } else {
                    if (titulo.trim().isEmpty()) {
                        titulo = "(sin t�tulo)";
                    } else {
                        if (titulo.length() > 35) {
                            titulo = titulo.substring(0, 32) + "...";
                        }
                    }
                }

                String fP; //Columna Fecha de pr�stamo
                if (p.getFechaPrestamo() == null) {
                    fP = "--";
                } else {
                    fP = p.getFechaPrestamo().toString();
                }

                String fD; //Columna Fecha de devoluci�n prevista
                if (p.getFechaDevolucionPrevista() == null) {
                    fD = "--";
                } else {
                    fD = p.getFechaDevolucionPrevista().toString();
                }

                long retraso = p.getDiasRetraso(); //Columna Retraso

                sb.append(String.format("%-6d | %-35s | %-12s | %-12s | %-6d%n",
                        p.getIdLibro(), titulo, fP, fD, retraso));
            }
        }
        sb.append("----------------------------------------------------------------------------");
        
        return sb.toString(); //Devolvemos la cadena completa
    }

}
