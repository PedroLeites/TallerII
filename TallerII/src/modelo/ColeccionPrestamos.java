package modelo;

import java.util.ArrayList;

public class ColeccionPrestamos {
	private ArrayList<Prestamo> prestamos;
	
	//Constructor
	public ColeccionPrestamos() {
		prestamos = new ArrayList<>();
	}
	
	// Agrega un nuevo préstamo
	public void agregar(Prestamo p) {
		 prestamos.add(p);
	}
	
    // Elimina un préstamo por la ID del libro
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
		

    // Devuelve la cantidad total de préstamos
    public int largo() {
        return prestamos.size();
    }
    
    // Verifica si la colección de préstamos está vacía
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
    
    // Devuelve todos los préstamos
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
	
}
