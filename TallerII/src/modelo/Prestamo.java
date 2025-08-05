package modelo;

public class Prestamo {
    private Fecha fechaPrestamo; //aff_pret_date
    private Fecha fechaDevolucionPrevista; //aff_pret_retour
    private int diasRetraso; //calcular con los atributos anteriores
    private int idLibro; //expl_cb
    private String tituloLibro; //tit

    public Prestamo() {
    	
    }

    public Prestamo(Fecha fechaPrestamo, Fecha fechaDevolucion, int diasRetraso, int idLibro, String tituloLibro) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucion;
        this.diasRetraso = diasRetraso;
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
    }
    
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

    public int getDiasRetraso() {
        return diasRetraso;
    }

    public void setDiasRetraso(int diasRetraso) {
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

	@Override
	public String toString() {
		return "Prestamo [fechaPrestamo=" + fechaPrestamo + ", fechaDevolucionPrevista=" + fechaDevolucionPrevista
				+ ", diasRetraso=" + diasRetraso + ", idLibro=" + idLibro + ", tituloLibro=" + tituloLibro + "]";
	}
	
	
}
