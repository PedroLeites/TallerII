package modelo;

public class Usuario {
	private int id; //id_empr
    private int ci; //empr_cb
    private String nombre; //empr_prenom
    private String apellido; //empr_nom
    private String correo; //empr_mail
    private ColeccionPrestamos prestamos;
    
    //Constructor común
    public Usuario() {
    	this.prestamos = new ColeccionPrestamos();
    }
    
    //Constructor específico
    public Usuario(int id, int ci, String nombre, String apellido, String correo) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.prestamos = new ColeccionPrestamos();
    }
    
    //Constructor de copia
    public Usuario(Usuario u) {
        this.id = u.id;
        this.ci = u.ci;
        this.nombre = u.nombre;
        this.apellido = u.apellido;
        this.correo = u.correo;
        this.prestamos = u.prestamos;
    }

    //Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public ColeccionPrestamos getPrestamosDeUsuario() {
		return prestamos;
	}

	public void setPrestamosDeUsuario(ColeccionPrestamos prestamos) {
		this.prestamos = prestamos;
	}
    
	//Métodos específicos
	public void agregarPrestamo(Prestamo p) {
        prestamos.agregarPrestamo(p);
    }
	
	public String librosAtrasados() {
		String resultado = "";
		if (prestamos == null || prestamos.vacia()) {
			resultado = "-";
		}
		
		for (int i = 0; i < prestamos.largo(); i++) {
			 Prestamo p = prestamos.obtenerPrestamoPorPosicion(i);
			 resultado += p.getFechaPrestamo() + ","
			            + p.getFechaDevolucionPrevista() + ","
			            + p.getDiasRetraso() + ","
			            + this.id + ","
			            + limpiar(this.nombre) + ","
			            + limpiar(this.apellido) + ","
			            + limpiar(this.correo) + ","
			            + limpiar(p.getTituloLibro()) + ","
			            + p.getIdLibro() + ";";
		}
		
		return resultado;
	}
	
	// Evita que comas/; de los títulos rompan la vista
	private String limpiar(String s) {
	    return (s == null) ? "" : s.replace(",", " ").replace(";", " ");
	}
	
	public void mostrarAtraso() {
		prestamos.mostrarColPrestamos();
	}

	//toString
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", ci=" + ci + ", nombre=" + nombre + ", apellido=" + apellido + ", correo="
				+ correo + ", prestamos=" + prestamos + "]";
	}
    
}
