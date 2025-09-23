package modelo;

import java.util.ArrayList;

public class ColeccionHistorial {
	private ArrayList<Historial> colHistorial;
	
	public ColeccionHistorial() {
		colHistorial = new ArrayList<Historial>();
	}

	public ArrayList<Historial> getColHistorial() {
		return colHistorial;
	}

	public void setColHistorial(ArrayList<Historial> colHistorial) {
		this.colHistorial = colHistorial;
	}
	
    public void agregarHistorial(Historial h) {
        colHistorial.add(h);
    }
    
    public int largo() {
    	return colHistorial.size();
    }
    
    public boolean pertenece(int id) {
    	boolean s = false;
    	int i = this.posicion(id);
    	if (i != -1) {
    		s = true;
    	}
    	return s;
    }
  
    public int posicion(int idUsu) {
    	int p = 1;
    	boolean s = false;
    	int i = 0;
    	do {
    		if (idUsu==colHistorial.get(i).getIdUsuario()) {
    			s = true;
    			p = i;
    		}
    		i++;
    	} while (!s && i < this.largo());
    	
    	return p;
    }
    
    public Historial obtener(int id) {
    	int i = 0;
    	i = posicion(id);
    	return colHistorial.get(i);
    }
	
    //toString
    @Override
    public String toString() {
    	return "Historial {" + colHistorial + "}";
    }
}
