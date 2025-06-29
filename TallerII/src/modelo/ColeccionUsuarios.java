package modelo;

import java.util.Hashtable;

public class ColeccionUsuarios {
	private Hashtable<Integer, Usuario> usuarios;
	
	public ColeccionUsuarios() {
        usuarios = new Hashtable<>();
    }
	
	public void agregarUsuario(Usuario u) {
        usuarios.put(u.getId(), u);
    }
	
	public Usuario obtenerUsuario(int id) {
        return usuarios.get(id);
    }

    public boolean pertenece(int id) {
        return usuarios.containsKey(id);
    }

    public Hashtable<Integer, Usuario> getUsuarios() {
        return usuarios;
    }
    
    /*Se usa Hashtable ya que necesitamos buscar usuarios por ID rápidamente (tiempo constante), 
    evitando duplicados.*/
	
}
