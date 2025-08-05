package modelo;

import java.util.ArrayList;

public class ColeccionPrestamos {
	private ArrayList<Prestamo> prestamos;
	
	//Constructor
	public ColeccionPrestamos() {
		prestamos = new ArrayList<>();
	}
	
	// Agrega un nuevo pr�stamo
	public void agregar(Prestamo p) {
		 prestamos.add(p);
	}
	
    // Elimina un pr�stamo por la ID del libro
	public void eliminar(int idLibro) {
	    for (int i = 0; i < prestamos.size(); i++) {
	        if (prestamos.get(i).getIdLibro() == idLibro) {
	            prestamos.remove(i);
	        }
	    }
	}
	
	   public Prestamo obtenerPrestamo(int id) {
	    	Prestamo encontrado = new Prestamo();
	        for (int i = 0; i < prestamos.size(); i++) {
	            if (prestamos.get(i).getIdLibro() == id) {
	                encontrado = prestamos.get(i);
	            }
	        }
	        return encontrado;
	    }
		

    // Devuelve la cantidad total de pr�stamos
    public int largo() {
        return prestamos.size();
    }
    
    // Verifica si la colecci�n de pr�stamos est� vac�a
    public boolean vacia() {
        return prestamos.isEmpty();
    }
    
    public boolean pertenece(int id) {
    	boolean esta = false;
        for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getIdLibro() == id) {
                esta = true;
            }
        }
        return esta;
    }
    
    // Devuelve todos los pr�stamos
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
	
}
