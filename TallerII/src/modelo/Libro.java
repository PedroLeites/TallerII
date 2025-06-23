package modelo;

public class Libro {
    private String codigoClasif; // expl_cote
    private String id;   // expl_cb
    private String titulo;   // tit
    private String tipoDocumento; // tdoc_libelle

    public Libro() {}

    public Libro(String explCote, String explCb, String titulo, String tipoDocumento) {
        this.codigoClasif = explCote;
        this.id = explCb;
        this.titulo = titulo;
        this.tipoDocumento = tipoDocumento;
    }

    // Getters y Setters
    public String getExplCote() {
        return codigoClasif;
    }

    public void setExplCote(String explCote) {
        this.codigoClasif = explCote;
    }

    public String getExplCb() {
        return id;
    }

    public void setExplCb(String explCb) {
        this.id = explCb;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public String toString() {
        return "Libro [explCote=" + codigoClasif + ", explCb=" + id + ", titulo=" + titulo + ", tipoDocumento=" + tipoDocumento + "]";
    }
}
