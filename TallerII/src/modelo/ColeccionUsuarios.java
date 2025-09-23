package modelo;

import java.util.ArrayList;

public class ColeccionUsuarios {
    private ArrayList<Usuario> usuarios;

    //Constructor
    public ColeccionUsuarios() {
        usuarios = new ArrayList<>();
    }

    //Getter y Setter
    //Devuelve todos los usuarios
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    
    //Establece la colección de usuarios
    public void setUsuarios(ArrayList<Usuario> usuarios) {
    	this.usuarios = usuarios;
    }
    
    //Métodos específicos
    //Agrega un usuario a la colección
    public void agregarUsuario(Usuario u) {
        if (!pertenece(u.getId())) {
            usuarios.add(u);
        }
    }
    
    //Elimina un usuario por su ID
	public void eliminarUsuario(int id) {
	    for (int i = 0; i < usuarios.size(); i++) {
	        if (usuarios.get(i).getId() == id) {
	            usuarios.remove(i);
	        }
	    }
	}

	//Dada la ID del usuario, lo retorna
    public Usuario obtenerUsuario(int id) {
    	Usuario encontrado = new Usuario();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                encontrado = usuarios.get(i);
            }
        }
        return encontrado;
    }
    
    public Usuario obtenerUsuarioPorPosicion(int posicion) {
        if (posicion >= 0 && posicion < usuarios.size()) {
            return usuarios.get(posicion);
        } else {
            return null;
        }
    }

    //Devuelve el total de usuarios de la colección
    public int largo() {
        return usuarios.size();
    }
    
    //Verifica si la colección está vacía
    public boolean vacia() {
        return usuarios.isEmpty();
    }
    
    //Dada la id de un usuario, verifica si pertenece a la colección
    public boolean pertenece(int id) {
    	boolean esta = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
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
    			if (id == usuarios.get(i).getId()) {
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
    	return "Usuarios {" + usuarios + "}";
    }
    
}
