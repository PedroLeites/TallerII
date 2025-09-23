package modelo;

public class Historial {
	private int idUsuario;
    private Correo correo;
    
    public Historial() {
    	
    }
    
    public Historial(int id, Correo c) {
    	this.idUsuario = id;
    	this.correo = c;
    }
    
    public Historial(Historial h) {
    	this(h.idUsuario, h.correo);
    }

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
	}
    
    @Override
    public String toString() {
    	return idUsuario + " - " + correo.toString();
    }
    
}
