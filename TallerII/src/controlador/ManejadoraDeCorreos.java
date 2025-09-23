package controlador;

import modelo.Correo;
import modelo.Correo.Tipo;
import modelo.Historial;
import modelo.ColeccionHistorial;
import modelo.Fecha;

import java.util.ArrayList;

public class ManejadoraDeCorreos {
	private ColeccionHistorial coleccionHistorial;
	
    public ManejadoraDeCorreos() {
    	this.coleccionHistorial = new ColeccionHistorial();
    }
    
    public ManejadoraDeCorreos(ColeccionHistorial col) {
    	if (col != null) {
            this.coleccionHistorial = col;
        } else {
            this.coleccionHistorial = new ColeccionHistorial();
        }
    }
    
    public ColeccionHistorial getColeccionHistorial() {
        return this.coleccionHistorial;
    }
    
    public void enviarConstanciaPorPrestamo(String destinatario,
            int idUsuario,
            String nombreCompleto,
            int idLibro,
            String tituloLibro,
            String fechaPrestamo,
            String fechaDevolucionPrevista) {

    	// 1) Asunto
    	String asunto = Correo.asuntoPara(Correo.Tipo.CONSTANCIA_DEVOLUCION, 1);

    	// 2) Cuerpo
    	int[] ids = new int[1];
    	ids[0] = idLibro;

    	String[] titulos = new String[1];
    	titulos[0] = (tituloLibro != null) ? tituloLibro : "";

    	String[] fPrest = new String[1];
    	fPrest[0] = (fechaPrestamo != null) ? fechaPrestamo : "";

    	String[] fDevPrev = new String[1];
    	fDevPrev[0] = (fechaDevolucionPrevista != null) ? fechaDevolucionPrevista : "";

    	String fechaEmision = new Fecha().toString();

    	String cuerpo = Correo.cuerpoConstancia(nombreCompleto, ids, titulos, fPrest, fDevPrev, fechaEmision);

    	// 3) Instanciar Correo => envía
    	Correo c = new Correo(destinatario, asunto, cuerpo);

    	// 4) Guardar en historial
    	Historial h = new Historial(idUsuario, c);
    	coleccionHistorial.agregarHistorial(h);
    }

    public void enviarConstanciasPorUsuario(String destinatario,
            int idUsuario,
            String nombreCompleto,
            ArrayList<Integer> idsLibros,
            ArrayList<String> titulosLibros,
            ArrayList<String> fechasPrestamo,
            ArrayList<String> fechasDevolucionPrevista) {

    	int n = (idsLibros != null) ? idsLibros.size() : 0;

    	// 1) Asunto
    	String asunto = Correo.asuntoPara(Correo.Tipo.CONSTANCIA_DEVOLUCION, n);

    	// 2) Cuerpo
    	int[] ids = new int[n];
    	String[] titulos = new String[n];
    	String[] fPrest = new String[n];
    	String[] fDevPrev = new String[n];

    	int i = 0;
    	for (i = 0; i < n; i++) {
    		ids[i] = (idsLibros.get(i) != null) ? idsLibros.get(i) : 0;
    		titulos[i] = (titulosLibros != null && titulosLibros.size() > i && titulosLibros.get(i) != null)? titulosLibros.get(i) : "";
    		fPrest[i] = (fechasPrestamo != null && fechasPrestamo.size() > i && fechasPrestamo.get(i) != null)? fechasPrestamo.get(i) : "";
    		fDevPrev[i] = (fechasDevolucionPrevista != null && fechasDevolucionPrevista.size() > i && fechasDevolucionPrevista.get(i) != null)? fechasDevolucionPrevista.get(i) : "";
    	}

    	String fechaEmision = new Fecha().toString();

    	String cuerpo = Correo.cuerpoConstancia(nombreCompleto, ids, titulos, fPrest, fDevPrev, fechaEmision);

    	// 3) Instanciar Correo => envía
    	Correo c = new Correo(destinatario, asunto, cuerpo);

    	// 4) Guardar en historial
    	Historial h = new Historial(idUsuario, c);
    	coleccionHistorial.agregarHistorial(h);
    }
    
    public void enviarNotificacionAtrasoPorPrestamo(String destinatario,
            int idUsuario,
            String nombreCompleto,
            int idLibro,
            String tituloLibro,
            String fechaPrestamo,
            String fechaDevolucionPrevista,
            long diasAtraso) {

    	// 1) Asunto
    	String asunto = Correo.asuntoPara(Tipo.NOTIFICACION_ATRASO, 1);

    	// 2) Cuerpo
    	int[] ids = new int[1];
    	ids[0] = idLibro;

    	String[] titulos = new String[1];
    	titulos[0] = (tituloLibro != null) ? tituloLibro : "";

    	String[] fPrest = new String[1];
    	fPrest[0] = (fechaPrestamo != null) ? fechaPrestamo : "";

    	String[] fDevPrev = new String[1];
    	fDevPrev[0] = (fechaDevolucionPrevista != null) ? fechaDevolucionPrevista : "";

    	long[] atrasos = new long[1];
    	atrasos[0] = diasAtraso;

    	String cuerpo = Correo.cuerpoNotificacionAtraso(nombreCompleto, ids, titulos, fPrest, fDevPrev, atrasos);

    	// 3) Instanciar Correo => envía en el constructor
    	Correo c = new Correo(destinatario, asunto, cuerpo);

    	// 4) Guardar en historial
    	Historial h = new Historial(idUsuario, c);
    	coleccionHistorial.agregarHistorial(h);
    }
    
    public void enviarNotificacionAtrasoPorUsuario(String destinatario,
            int idUsuario,
            String nombreCompleto,
            ArrayList<Integer> idsLibros,
            ArrayList<String> titulosLibros,
            ArrayList<String> fechasPrestamo,
            ArrayList<String> fechasDevolucionPrevista,
            ArrayList<Long> diasAtraso) {

    	int n = (idsLibros != null) ? idsLibros.size() : 0;

    	// 1) Asunto
    	String asunto = Correo.asuntoPara(Tipo.NOTIFICACION_ATRASO, n);

    	// 2) Cuerpo (convertimos ArrayList -> arrays con for normal)
    	int[] ids = new int[n];
    	String[] titulos = new String[n];
    	String[] fPrest = new String[n];
    	String[] fDevPrev = new String[n];
    	long[] atrasos = new long[n];

    	int i = 0;
    	for (i = 0; i < n; i++) {
    		ids[i] = (idsLibros.get(i) != null) ? idsLibros.get(i) : 0;
    		titulos[i] = (titulosLibros != null && titulosLibros.size() > i && titulosLibros.get(i) != null)? titulosLibros.get(i) : "";
    		fPrest[i] = (fechasPrestamo != null && fechasPrestamo.size() > i && fechasPrestamo.get(i) != null)? fechasPrestamo.get(i) : "";
    		fDevPrev[i] = (fechasDevolucionPrevista != null && fechasDevolucionPrevista.size() > i && fechasDevolucionPrevista.get(i) != null)? fechasDevolucionPrevista.get(i) : "";
    		atrasos[i] = (diasAtraso != null && diasAtraso.size() > i && diasAtraso.get(i) != null)? diasAtraso.get(i) : 0L;
    	}

    	String cuerpo = Correo.cuerpoNotificacionAtraso(nombreCompleto, ids, titulos, fPrest, fDevPrev, atrasos);

    	// 3) Instanciar Correo => envía
    	Correo c = new Correo(destinatario, asunto, cuerpo);

    	// 4) Guardar en historial
    	Historial h = new Historial(idUsuario, c);
    	coleccionHistorial.agregarHistorial(h);
    }

    
}
