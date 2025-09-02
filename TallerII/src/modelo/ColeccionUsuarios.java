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
    
    //Establece la colecci�n de usuarios
    public void setUsuarios(ArrayList<Usuario> usuarios) {
    	this.usuarios = usuarios;
    }
    
    //M�todos espec�ficos
    //Agrega un usuario a la colecci�n
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

    //Devuelve el total de usuarios de la colecci�n
    public int largo() {
        return usuarios.size();
    }
    
    //Verifica si la colecci�n est� vac�a
    public boolean vacia() {
        return usuarios.isEmpty();
    }
    
    //Dada la id de un usuario, verifica si pertenece a la colecci�n
    public boolean pertenece(int id) {
    	boolean esta = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                esta = true;
            }
        }
        return esta;
    }
    
    //toString
    @Override
    public String toString() {
        if (usuarios == null || usuarios.isEmpty()) {
            return "ColeccionUsuarios {total=0} (la colecci�n est� vac�a)";
        }

        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        sb.append("ColeccionUsuarios {total=")
          .append(usuarios.size())
          .append("}")
          .append(ls);

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u != null) {
                sb.append(u.toString()).append(ls);
            }
        }

        return sb.toString();
    }
    
}
