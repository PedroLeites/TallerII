package modelo;

public class Usuario {
	private int id;
    private int ci;
    private String nombre;
    private String apellido;
    private String correo;
    private ColeccionPrestamos prestamos;
    
    public Usuario() {
    	this.prestamos = new ColeccionPrestamos();
    }
    
    public Usuario(int id, int ci, String nombre, String apellido, String correo) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.prestamos = new ColeccionPrestamos();
    }
    
    public Usuario(Usuario u) {
        this.id = u.id;
        this.ci = u.ci;
        this.nombre = u.nombre;
        this.apellido = u.apellido;
        this.correo = u.correo;
        this.prestamos = u.prestamos;
    }

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

	public ColeccionPrestamos getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(ColeccionPrestamos prestamos) {
		this.prestamos = prestamos;
	}
    
	public void agregarPrestamo(Prestamo p) {
        prestamos.agregarPrestamo(p);
    }
    
}
