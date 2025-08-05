package modelo;

import java.util.ArrayList;

public class ColeccionUsuarios {
    private ArrayList<Usuario> usuarios;

    public ColeccionUsuarios() {
        usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario u) {
        if (!pertenece(u.getId())) {
            usuarios.add(u);
        }
    }
    
	public void eliminarUsuario(int id) {
	    for (int i = 0; i < usuarios.size(); i++) {
	        if (usuarios.get(i).getId() == id) {
	            usuarios.remove(i);
	        }
	    }
	}

    public Usuario obtenerUsuario(int id) {
    	Usuario encontrado = new Usuario();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                encontrado = usuarios.get(i);
            }
        }
        return encontrado;
    }

    public int largo() {
        return usuarios.size();
    }
    
    public boolean vacia() {
        return usuarios.isEmpty();
    }
    
    public boolean pertenece(int id) {
    	boolean esta = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                esta = true;
            }
        }
        return esta;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
