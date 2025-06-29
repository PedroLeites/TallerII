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
	
    // Elimina un préstamo específico (por objeto exacto)
    public void eliminar(Prestamo p) {
        prestamos.remove(p);
    }
		
    // Devuelve todos los préstamos
    public ArrayList<Prestamo> obtenerTodos() {
        return prestamos;
    }

    // Devuelve la cantidad total de préstamos
    public int largo() {
        return prestamos.size();
    }
    
    // Verifica si la colección de préstamos está vacía
    public boolean vacia() {
        return prestamos.isEmpty();
    }
	
}
