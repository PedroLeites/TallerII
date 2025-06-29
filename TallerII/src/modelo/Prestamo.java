package modelo;

public class Prestamo {
    private String fechaPrestamo; //aff_pret_date
    private String fechaDevolucionPrevista; //aff_pret_retour
    private int diasRetraso; //calcular con los atributos anteriores
    private int idLibro; //expl_cb
    private String tituloLibro; //tit

    public Prestamo() {
    	
    }

    public Prestamo(String fechaPrestamo, String fechaDevolucion, int diasRetraso, int idLibro, String tituloLibro) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucion;
        this.diasRetraso = diasRetraso;
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
    }

    // Getters y Setters
    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public void setFechaDevolucionPrevista(String fechaDevolucionPrevista) {
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
