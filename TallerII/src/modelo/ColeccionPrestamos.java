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
	
    // Elimina un pr�stamo espec�fico (por objeto exacto)
    public void eliminar(Prestamo p) {
        prestamos.remove(p);
    }
		
    // Devuelve todos los pr�stamos
    public ArrayList<Prestamo> obtenerTodos() {
        return prestamos;
    }

    // Devuelve la cantidad total de pr�stamos
    public int largo() {
        return prestamos.size();
    }
    
    // Verifica si la colecci�n de pr�stamos est� vac�a
    public boolean vacia() {
        return prestamos.isEmpty();
    }
	
}
