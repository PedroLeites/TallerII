package modelo;

public class Fecha {
	private int dia;
	private int mes;
	private int anio;
	
	public Fecha() {
		
	}
	
	public Fecha(int dia, int mes, int anio) {
		this.dia = dia;
		this.mes = mes;
		this.anio = anio;
	}
	
	public Fecha(Fecha f) {
		this.dia = f.dia;
		this.mes = f.mes;
		this.anio = f.anio;
	}
	
	public Fecha(String fechaStr) {
        // Formato esperado: "dd/MM/yyyy"
        String[] partes = fechaStr.split("/");
        if (partes.length == 3) {
            this.dia = Integer.parseInt(partes[0]);
            this.mes = Integer.parseInt(partes[1]);
            this.anio = Integer.parseInt(partes[2]);
        } else {
            throw new IllegalArgumentException("Formato de fecha inválido: " + fechaStr);
        }
    }

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
	
	public java.time.LocalDate toLocalDate() {
        return java.time.LocalDate.of(anio, mes, dia);
    }

    public static int diasEntre(Fecha desde, Fecha hasta) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
            desde.toLocalDate(), hasta.toLocalDate()
        );
    }
    
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, anio);
    }
	
}
