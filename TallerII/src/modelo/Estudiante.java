package modelo;

public class Estudiante {
	//Atributos
	System.out.println("Estudiante class loaded");
	private int CI;
	private String nombre;
	private String email;
	private String libro;
	private String fechaVencimiento;
	
	//Constructor por defecto
	public Estudiante() {
		
	}
	
	//Constructor com�n
	public Estudiante(int cedula, String nombre, String email, String libro, String fechaVencimiento) {
		this.CI = cedula;
		this.nombre = nombre;
		this.email = email;
		this.libro = libro;
		this.fechaVencimiento = fechaVencimiento;
	}
	
	//Constructor de copia
	public Estudiante(Estudiante e) {
		this.CI = e.CI;
		this.nombre = e.nombre;
		this.email = e.email;
		this.libro = e.libro;
		this.fechaVencimiento = e.fechaVencimiento;
	}

	//Selectores y Modificadores
	public int getCI() {
		return CI;
	}

	public void setCI(int cI) {
		CI = cI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLibro() {
		return libro;
	}

	public void setLibro(String libro) {
		this.libro = libro;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	@Override
	public String toString() {
		return "Estudiante [CI=" + CI + ", nombre=" + nombre + ", email=" + email + ", libro=" + libro
				+ ", fechaVencimiento=" + fechaVencimiento + "]";
	}
	
}
