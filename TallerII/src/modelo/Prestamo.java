package modelo;

import java.time.LocalDate;

public class Prestamo {
    private Fecha fechaPrestamo; //aff_pret_date
    private Fecha fechaDevolucionPrevista; //aff_pret_retour
    private long diasRetraso; //calcular con los atributos anteriores
    private int idLibro; //expl_cb
    private String tituloLibro; //tit

    //Constructor común
    public Prestamo() {
    	
    }

    //Constructor específico
    public Prestamo(Fecha fechaPrestamo, Fecha fechaDevolucion, int idLibro, String tituloLibro) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucion;
        LocalDate hoy = LocalDate.now();
        Fecha fechaHoy = new Fecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
        this.diasRetraso = Fecha.diasEntre(fechaDevolucionPrevista, fechaHoy);
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
    }
    
    //Constructor de copia
    public Prestamo(Prestamo p) {
        this.fechaPrestamo = p.fechaPrestamo;
        this.fechaDevolucionPrevista = p.fechaDevolucionPrevista;
        this.diasRetraso = p.diasRetraso;
        this.idLibro = p.idLibro;
        this.tituloLibro = p.tituloLibro;
    }

    // Getters y Setters
    public Fecha getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Fecha fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Fecha getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public void setFechaDevolucionPrevista(Fecha fechaDevolucionPrevista) {
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
    }

    public long getDiasRetraso() {
        return diasRetraso;
    }

    public void setDiasRetraso(long diasRetraso) {
        this.diasRetraso = diasRetraso;
    }
    
    public int getIdLibro() {
        return idLibro;
    }
    
    public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public String getTituloLibro() {
        return tituloLibro;
    }

	public void setTituloLibro(String tituloLibro) {
		this.tituloLibro = tituloLibro;
	}

	//toString
	@Override
	public String toString() {
		return "Prestamo [fechaPrestamo=" + fechaPrestamo + ", fechaDevolucionPrevista=" + fechaDevolucionPrevista
				+ ", diasRetraso=" + diasRetraso + ", idLibro=" + idLibro + ", tituloLibro=" + tituloLibro + "]";
	}
	
	public void mostrarPrestamo() {
		System.out.println(toString());
	}	
	
	//Falta test caja blanca
	
}
