package modelo;

import java.util.Hashtable;
import java.util.Collection;

public class ColeccionEstudiantes {
	private Hashtable<Integer, Estudiante> alumnos;
	
	//Constructor
	public ColeccionEstudiantes() {
		alumnos = new Hashtable<>();
	}
	
	// Agrega o reemplaza un estudiante
	public void agregar(Estudiante e) {
		alumnos.put(e.getCI(), e);
	}
	
	// Verifica si un estudiante pertenece dada su CI
	public boolean pertenece(int ci) {
		return alumnos.containsKey(ci);
	}
	
	// Elimina al estudiante si existe
	public void eliminar(int ci) {
		if (pertenece(ci)) {
			alumnos.remove(ci);
		}
	}
	
	// Busca un estudiante por su CI
	public Estudiante obtenerPorCI(int ci) {
		return alumnos.get(ci);
	}

	// Devuelve todos los estudiantes
	public Collection<Estudiante> obtenerTodos() {
		return alumnos.values();
	}

	// Devuelve la cantidad total de estudiantes
	public int largo() {
		return alumnos.size();
	}

	
}
