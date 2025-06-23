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
	
    // Busca todos los préstamos de un estudiante por su CI
    public ArrayList<Prestamo> obtenerPorCI(int ci) {
        ArrayList<Prestamo> resultado = new ArrayList<>();
        for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getEstudiante().getCI() == ci) {
                resultado.add(prestamos.get(i));
            }
        }
        return resultado;
    }	
    
    // Devuelve todos los préstamos
    public ArrayList<Prestamo> obtenerTodos() {
        return prestamos;
    }

    // Devuelve la cantidad total de préstamos
    public int largo() {
        return prestamos.size();
    }
    
    // Verifica si existe al menos un préstamo para el estudiante con CI dado
    public boolean existePrestamoParaEstudiante(int ci) {
        boolean existe = false; 
    	for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getEstudiante().getCI() == ci) {
                existe = true;
            }
        }
        return existe;
    }
	
}
