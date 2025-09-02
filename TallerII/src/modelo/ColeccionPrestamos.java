package modelo;

import java.util.ArrayList;

public class ColeccionPrestamos {
	private ArrayList<Prestamo> prestamos;
	
	//Constructor
	public ColeccionPrestamos() {
		prestamos = new ArrayList<>();
	}
	
	//Getter y Setter
	//Devuelve todos los préstamos
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    //Establece la colección de préstamos
    public void setPrestamos(ArrayList<Prestamo> p) {
    	this.prestamos = p;
    }
    
    //Métodos específicos
	//Agrega un nuevo préstamo
	public void agregarPrestamo(Prestamo p) {
		 prestamos.add(p);
	}
	
    //Elimina un préstamo por la ID del libro
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
		
    //Devuelve la cantidad total de préstamos
    public int largo() {
        return prestamos.size();
    }
    
    //Verifica si la colección de préstamos está vacía
    public boolean vacia() {
        return prestamos.isEmpty();
    }
    
    //Dada la id de un ejemplar, verifica si pertenece a la colección de préstamos
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
    	//Verifica si la colección es nula o vacía
        if (prestamos == null || prestamos.isEmpty()) {
            return "ColeccionPrestamos {total=0}" + System.lineSeparator() + "(la colección está vacía)";
        }

        //Usamos StringBuilder para armar la cadena de salida
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        
        sb.append("ColeccionPrestamos {total=").append(prestamos.size()).append("}").append(ls); //Encabezado con el total de préstamos
        sb.append("----------------------------------------------------------------------------").append(ls); //Línea separadora
        sb.append(String.format("%-6s | %-35s | %-12s | %-12s | %-6s%n",
                "ID", "Título", "Prest.", "Dev.Prev.", "Retr.")); //Encabezados de las columnas
        sb.append("----------------------------------------------------------------------------").append(ls);

        for (int i = 0; i < prestamos.size(); i++) { //Recorremos la lista de préstamos
            Prestamo p = prestamos.get(i);

            if (p != null) { //Validamos que el préstamo no sea nulo
            	String titulo = p.getTituloLibro(); //Columna Título
                if (titulo == null) {
                    titulo = "(sin título)";
                } else {
                    if (titulo.trim().isEmpty()) {
                        titulo = "(sin título)";
                    } else {
                        if (titulo.length() > 35) {
                            titulo = titulo.substring(0, 32) + "...";
                        }
                    }
                }

                String fP; //Columna Fecha de préstamo
                if (p.getFechaPrestamo() == null) {
                    fP = "--";
                } else {
                    fP = p.getFechaPrestamo().toString();
                }

                String fD; //Columna Fecha de devolución prevista
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
