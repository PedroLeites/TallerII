package modelo;

public class Prestamo {
    private String fechaPrestamo; // aff_pret_date
    private String fechaDevolucionPrevista; // aff_pret_retour
    private int diasRetraso; // retard
    private Estudiante estudiante; // Relación con Estudiante
    private Libro libro; // Relación con Libro

    public Prestamo() {}

    public Prestamo(String fechaPrestamo, String fechaDevolucionPrevista, int diasRetraso, Estudiante estudiante, Libro libro) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
        this.diasRetraso = diasRetraso;
        this.estudiante = estudiante;
        this.libro = libro;
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "Prestamo [fechaPrestamo=" + fechaPrestamo + ", fechaDevolucionPrevista=" + fechaDevolucionPrevista +
               ", diasRetraso=" + diasRetraso + ", estudiante=" + estudiante.getNombre() + ", libro=" + libro.getTitulo() + "]";
    }
}
