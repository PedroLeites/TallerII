package modelo;

public class Fecha {
	//Atributos
	private int dia;
	private int mes;
	private int anio;
	
	//Constructor por defecto
	public Fecha() {
		
	}
	
	//Constructor común
	public Fecha(int d, int m, int a) {
		this.dia = d;
		this.mes = m;
		this.anio = a;
	}
	
	//Constructor de copia
	public Fecha(Fecha f) {
		this.dia = f.dia;
		this.mes = f.mes;
		this.anio = f.anio;
	}

	//Selectores y Modificadores
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	@Override
	public String toString() {
		return dia + "-" + mes + "-" + anio;
	}
	
	
}
