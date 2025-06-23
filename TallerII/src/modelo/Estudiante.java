package modelo;

public class Estudiante {
	//Atributos
	private int idPMB; // id_empr del PMB
	private int CI; // empr_cb
	private String nombre; // empr_nom + empr_prenom
	private String email; // empr_mail
	
	//Constructor por defecto
	public Estudiante() {
		
	}
	
	//Constructor común
	public Estudiante(int idPMB, int CI, String nombre, String email) {
		this.idPMB = idPMB;
        this.CI = CI;
        this.nombre = nombre;
        this.email = email;
	}
	
	//Constructor de copia
	public Estudiante(Estudiante e) {
		this.idPMB = e.idPMB;
        this.CI = e.CI;
        this.nombre = e.nombre;
        this.email = e.email;
	}

	//Selectores y Modificadores
	public int getIdPMB() {
		return idPMB;
	}
	
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

	@Override
	public String toString() {
		return "Estudiante [idPMB=" + idPMB + ", CI=" + CI + ", nombre=" + nombre + ", email=" + email + "]";
	}
	
}
