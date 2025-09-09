package modelo;

import java.util.ArrayList;
import java.util.List;

public class Correo {
	public enum Tipo {NOTIFICACION_ATRASO, CONSTANCIA_DEVOLUCION}
	
	// Cabecera
    private Fecha fechaEmision;
    private Tipo tipo;
    private String destinatario; // puede contener 1 o más direcciones separadas por coma, punto y coma o espacios
    private String asunto;
    private String cuerpo;

    // Datos del usuario
    private int cedulaUsuario;
    private String nombreCompletoUsuario;

    // Listas paralelas de préstamos (sirve para 1 o N)
    private ArrayList<Integer> idsLibros;
    private ArrayList<String> titulosLibros;
    private ArrayList<String> fechasPrestamo;
    private ArrayList<String> fechasDevolucionPrevista;
    private ArrayList<Long> diasAtraso;
	
    //Constructores
    public Correo() {
        this.fechaEmision = new Fecha();
        this.tipo = Tipo.NOTIFICACION_ATRASO;
        this.destinatario = "";
        this.asunto = "";
        this.cuerpo = "";
        this.cedulaUsuario = 0;
        this.nombreCompletoUsuario = "";
        this.idsLibros = new ArrayList<Integer>();
        this.titulosLibros = new ArrayList<String>();
        this.fechasPrestamo = new ArrayList<String>();
        this.fechasDevolucionPrevista = new ArrayList<String>();
        this.diasAtraso = new ArrayList<Long>();
    }
    
    // Un préstamo (fila)
    public Correo(String destinatario,
                  int cedulaUsuario,
                  String nombreCompletoUsuario,
                  int idLibro,
                  String tituloLibro,
                  String fechaPrestamo,
                  String fechaDevolucionPrevista,
                  long diasAtraso,
                  Tipo tipo) {

        this();
        if (destinatario != null) {
            this.destinatario = destinatario;
        } else {
            this.destinatario = "";
        }

        this.cedulaUsuario = cedulaUsuario;

        if (nombreCompletoUsuario != null) {
            this.nombreCompletoUsuario = nombreCompletoUsuario;
        } else {
            this.nombreCompletoUsuario = "";
        }

        if (tipo != null) {
            this.tipo = tipo;
        } else {
            this.tipo = Tipo.NOTIFICACION_ATRASO;
        }

        agregarPrestamo(idLibro, tituloLibro, fechaPrestamo, fechaDevolucionPrevista, diasAtraso);
        generarContenido();
    }

    // Varios préstamos (usuario)
    public Correo(String destinatario,
                  int cedulaUsuario,
                  String nombreCompletoUsuario,
                  List<Integer> idsLibros,
                  List<String> titulosLibros,
                  List<String> fechasPrestamo,
                  List<String> fechasDevolucionPrevista,
                  List<Long> diasAtraso,
                  Tipo tipo) {

        this();

        if (destinatario != null) {
            this.destinatario = destinatario;
        } else {
            this.destinatario = "";
        }

        this.cedulaUsuario = cedulaUsuario;

        if (nombreCompletoUsuario != null) {
            this.nombreCompletoUsuario = nombreCompletoUsuario;
        } else {
            this.nombreCompletoUsuario = "";
        }

        if (tipo != null) {
            this.tipo = tipo;
        } else {
            this.tipo = Tipo.NOTIFICACION_ATRASO;
        }

        if (idsLibros != null) {
            for (int i = 0; i < idsLibros.size(); i++) {
                this.idsLibros.add(idsLibros.get(i));
            }
        }
        if (titulosLibros != null) {
            for (int i = 0; i < titulosLibros.size(); i++) {
                this.titulosLibros.add(titulosLibros.get(i));
            }
        }
        if (fechasPrestamo != null) {
            for (int i = 0; i < fechasPrestamo.size(); i++) {
                this.fechasPrestamo.add(fechasPrestamo.get(i));
            }
        }
        if (fechasDevolucionPrevista != null) {
            for (int i = 0; i < fechasDevolucionPrevista.size(); i++) {
                this.fechasDevolucionPrevista.add(fechasDevolucionPrevista.get(i));
            }
        }
        if (diasAtraso != null) {
            for (int i = 0; i < diasAtraso.size(); i++) {
                this.diasAtraso.add(diasAtraso.get(i));
            }
        }

        generarContenido();
    }
	
    //Getters y Setters
    public Fecha getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Fecha fechaEmision) { this.fechaEmision = fechaEmision; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getCuerpo() { return cuerpo; }
    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }

    public int getCedulaUsuario() { return cedulaUsuario; }
    public void setCedulaUsuario(int cedulaUsuario) { this.cedulaUsuario = cedulaUsuario; }

    public String getNombreCompletoUsuario() { return nombreCompletoUsuario; }
    public void setNombreCompletoUsuario(String nombreCompletoUsuario) { this.nombreCompletoUsuario = nombreCompletoUsuario; }

    public List<Integer> getIdsLibros() { return new ArrayList<Integer>(idsLibros); }
    public void setIdsLibros(List<Integer> idsLibros) {
        this.idsLibros.clear();
        if (idsLibros != null) {
            for (int i = 0; i < idsLibros.size(); i++) {
                this.idsLibros.add(idsLibros.get(i));
            }
        }
    }

    public List<String> getTitulosLibros() { return new ArrayList<String>(titulosLibros); }
    public void setTitulosLibros(List<String> titulosLibros) {
        this.titulosLibros.clear();
        if (titulosLibros != null) {
            for (int i = 0; i < titulosLibros.size(); i++) {
                this.titulosLibros.add(titulosLibros.get(i));
            }
        }
    }

    public List<String> getFechasPrestamo() { return new ArrayList<String>(fechasPrestamo); }
    public void setFechasPrestamo(List<String> fechasPrestamo) {
        this.fechasPrestamo.clear();
        if (fechasPrestamo != null) {
            for (int i = 0; i < fechasPrestamo.size(); i++) {
                this.fechasPrestamo.add(fechasPrestamo.get(i));
            }
        }
    }

    public List<String> getFechasDevolucionPrevista() { return new ArrayList<String>(fechasDevolucionPrevista); }
    public void setFechasDevolucionPrevista(List<String> fechasDevolucionPrevista) {
        this.fechasDevolucionPrevista.clear();
        if (fechasDevolucionPrevista != null) {
            for (int i = 0; i < fechasDevolucionPrevista.size(); i++) {
                this.fechasDevolucionPrevista.add(fechasDevolucionPrevista.get(i));
            }
        }
    }

    public List<Long> getDiasAtraso() { return new ArrayList<Long>(diasAtraso); }
    public void setDiasAtraso(List<Long> diasAtraso) {
        this.diasAtraso.clear();
        if (diasAtraso != null) {
            for (int i = 0; i < diasAtraso.size(); i++) {
                this.diasAtraso.add(diasAtraso.get(i));
            }
        }
    }
    
    //Métodos Específicos
    public void agregarPrestamo(int idLibro, String tituloLibro, String fechaPrestamo, String fechaDevolucionPrevista, long diasAtraso) {
    	this.idsLibros.add(idLibro);

    	if (tituloLibro != null) {
    		this.titulosLibros.add(tituloLibro);
    	} else {
    		this.titulosLibros.add("");
    	}

    	if (fechaPrestamo != null) {
    		this.fechasPrestamo.add(fechaPrestamo);
    	} else {
    		this.fechasPrestamo.add("");
    	}

    	if (fechaDevolucionPrevista != null) {
    		this.fechasDevolucionPrevista.add(fechaDevolucionPrevista);
    	} else {
    		this.fechasDevolucionPrevista.add("");
    	}

    	this.diasAtraso.add(diasAtraso);
    }
    
    public void generarContenido() {
        if (this.tipo == Tipo.NOTIFICACION_ATRASO) {
            this.asunto = construirAsuntoNotificacion();
            this.cuerpo = construirCuerpoNotificacion();
        } else {
            this.asunto = construirAsuntoConstancia();
            this.cuerpo = construirCuerpoConstancia();
        }
    }
    
    private String construirAsuntoNotificacion() {
        int n = this.idsLibros.size();
        if (n <= 1) {
            return "Notificación de devolución pendiente";
        } else {
            return "Notificación de devolución pendiente (" + n + " préstamos)";
        }
    }
    
    private String construirCuerpoNotificacion() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estimado/a ").append(this.nombreCompletoUsuario).append(",\n\n");
        sb.append("Le recordamos que tiene devolución pendiente de los siguientes materiales:\n\n");

        for (int i = 0; i < this.idsLibros.size(); i++) {
            sb.append("- Título: \"").append(this.titulosLibros.get(i)).append("\" (ID: ")
              .append(this.idsLibros.get(i)).append(")\n");
            sb.append("  • Fecha préstamo: ").append(this.fechasPrestamo.get(i)).append("\n");
            sb.append("  • Fecha límite: ").append(this.fechasDevolucionPrevista.get(i)).append("\n");
            sb.append("  • Días de atraso: ").append(this.diasAtraso.get(i)).append("\n\n");
        }

        long max = maxDiasAtraso();
        sb.append("El mayor atraso registrado es de ").append(max).append(" día(s).\n\n");
        sb.append("Por favor, regularice su situación a la brevedad.\n\n");
        sb.append("Saludos cordiales,\nBiblioteca INET");
        return sb.toString();
    }
    
    private String construirAsuntoConstancia() {
        int n = this.idsLibros.size();
        if (n <= 1) {
            return "Constancia de devolución de libro";
        } else {
            return "Constancias de devolución (" + n + " préstamos)";
        }
    }
    
    private String construirCuerpoConstancia() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estimado/a ").append(this.nombreCompletoUsuario).append(",\n\n");

        if (this.idsLibros.size() == 1) {
            sb.append("Se emite constancia de devolución del siguiente material:\n\n");
        } else {
            sb.append("Se emiten constancias de devolución de los siguientes materiales:\n\n");
        }

        for (int i = 0; i < this.idsLibros.size(); i++) {
            sb.append("- Título: \"").append(this.titulosLibros.get(i)).append("\" (ID: ")
              .append(this.idsLibros.get(i)).append(")\n");
            sb.append("  • Fecha préstamo: ").append(this.fechasPrestamo.get(i)).append("\n");
            sb.append("  • Fecha límite: ").append(this.fechasDevolucionPrevista.get(i)).append("\n\n");
        }

        sb.append("Fecha de emisión: ").append(this.fechaEmision.toString()).append("\n\n");
        sb.append("Gracias por su colaboración.\n\n");
        sb.append("Atentamente,\nBiblioteca INET");
        return sb.toString();
    }
    
    public long maxDiasAtraso() {
        long max = 0L;
        for (int i = 0; i < this.diasAtraso.size(); i++) {
            Long v = this.diasAtraso.get(i);
            if (v == null) {
                v = 0L;
            }
            if (v > max) {
                max = v;
            }
        }
        return max;
    }
    
    public String[] dividirDestinatario() {
        if (this.destinatario == null) {
            return new String[0];
        }
        String texto = this.destinatario.trim();
        if (texto.isEmpty()) {
            return new String[0];
        }
        return texto.split("[,;\\s]+");
    }

    public static boolean esCorreoValido(String email) {
        if (email == null) {
            return false;
        }
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }

    public List<String> destinatariosValidos() {
        ArrayList<String> out = new ArrayList<String>();
        String[] arr = dividirDestinatario();
        for (int i = 0; i < arr.length; i++) {
            boolean ok = esCorreoValido(arr[i]);
            if (ok) {
                out.add(arr[i]);
            }
        }
        return out;
    }
	
}
