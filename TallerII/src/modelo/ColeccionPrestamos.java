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
    
    public int posicion(int id) {
    	int p = 1;
    	if (pertenece(id)) {
    		boolean s = false;
    		int i = 0;
    		
    		do {
    			if (id == prestamos.get(i).getIdLibro()) {
    				s = true;
    				p = i;
    			}
    		} while(!s && i < this.largo());
    	}
    	return p;
    }
    
    //toString
    @Override
    public String toString() {
    	return "Prestamos {" + prestamos + "}";
    }
    
    public void mostrarColPrestamos() {
    	for (int i = 0; i < prestamos.size(); i++) {
    		prestamos.get(i).mostrarPrestamo();
    	}
    }
    
    //Falta test caja blanca
    
}
