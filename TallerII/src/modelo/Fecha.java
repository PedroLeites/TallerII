package modelo;

import java.time.LocalDate;

public class Fecha {
	private int dia;
	private int mes;
	private int anio;
	
	//Constructor com�n
	public Fecha() {
		LocalDate hoy = LocalDate.now();
        this.dia = hoy.getDayOfMonth();
        this.mes = hoy.getMonthValue();
        this.anio = hoy.getYear();
	}
	
	//Constructor espec�fico
	public Fecha(int dia, int mes, int anio) {
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
	}
	
	//Constructor de copia
	public Fecha(Fecha f) {
		this.dia = f.dia;
		this.mes = f.mes;
		this.anio = f.anio;
	}
	
	//Constructor de Fecha a partir de un String
	public Fecha(String fechaStr) {
        // Formato esperado: "dd/MM/yyyy"
        String[] partes = fechaStr.split("/");
        if (partes.length == 3) {
            this.dia = Integer.parseInt(partes[0]);
            this.mes = Integer.parseInt(partes[1]);
            this.anio = Integer.parseInt(partes[2]);
        } else {
            throw new IllegalArgumentException("Formato de fecha inv�lido: " + fechaStr);
        }
    }

	//Getters y Setters
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
	
	//M�todos espec�ficos
	public LocalDate toLocalDate() {
        return LocalDate.of(anio, mes, dia);
    }

    public static long diasEntre(Fecha desde, Fecha hasta) {
    	long dias = 0;
    	dias = (long) java.time.temporal.ChronoUnit.DAYS.between(desde.toLocalDate(), hasta.toLocalDate());
    	return dias;
    }
    
    //toString
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, anio);
    }
	
}
