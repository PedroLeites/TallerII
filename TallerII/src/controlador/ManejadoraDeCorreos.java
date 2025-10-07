package controlador;

import modelo.Correo;
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

    	String asunto = Correo.asuntoPara(Correo.Tipo.CONSTANCIA_DEVOLUCION, 1);

    	ArrayList<Integer> ids = new ArrayList<Integer>();
    	ids.add(idLibro);

    	ArrayList<String> titulos = new ArrayList<String>();
    	if (tituloLibro == null) {
    		titulos.add("");
    	} else {
    		titulos.add(tituloLibro);
    	}

    	ArrayList<String> fPrest = new ArrayList<String>();
    	if (fechaPrestamo == null) {
    		fPrest.add("");
    	} else {
    		fPrest.add(fechaPrestamo);
    	}

    	ArrayList<String> fDev = new ArrayList<String>();
    	if (fechaDevolucionPrevista == null) {
    		fDev.add("");
    	} else {
    		fDev.add(fechaDevolucionPrevista);
    	}

    	String fechaEmision = new Fecha().toString();

    	String cuerpo = Correo.cuerpoConstancia(nombreCompleto,ids,titulos,fPrest,fDev,fechaEmision);

    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	Historial historial = new Historial(idUsuario, correo);
    	coleccionHistorial.agregarHistorial(historial);
    }

    public void enviarConstanciasPorUsuario(String destinatario,
            								int idUsuario,
            								String nombreCompleto,
            								ArrayList<Integer> idsLibros,
            								ArrayList<String> titulosLibros,
            								ArrayList<String> fechasPrestamo,
            								ArrayList<String> fechasDevolucionPrevista) {

    	int cantidadElementos;
    	if (idsLibros == null) {
    		cantidadElementos = 0;
    	} else {
    		cantidadElementos = idsLibros.size();
    	}

    	String asunto = Correo.asuntoPara(Correo.Tipo.CONSTANCIA_DEVOLUCION, cantidadElementos);

    	String fechaEmision = new Fecha().toString();

    	String cuerpo = Correo.cuerpoConstancia(nombreCompleto,idsLibros,titulosLibros,fechasPrestamo,fechasDevolucionPrevista,fechaEmision);

    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	Historial historial = new Historial(idUsuario, correo);
    	coleccionHistorial.agregarHistorial(historial);
    }
    
    public void enviarNotificacionAtrasoPorPrestamo(String destinatario,
            										int idUsuario,
            										String nombreCompleto,
            										int idLibro,
            										String tituloLibro,
            										String fechaPrestamo,
            										String fechaDevolucionPrevista,
            										long diasAtraso) {

    	String asunto = Correo.asuntoPara(Correo.Tipo.NOTIFICACION_ATRASO, 1);

    	ArrayList<Integer> ids = new ArrayList<Integer>();
    	ids.add(idLibro);

    	ArrayList<String> titulos = new ArrayList<String>();
    	if (tituloLibro == null) {
    		titulos.add("");
    	} else {
    		titulos.add(tituloLibro);
    	}

    	ArrayList<String> fPrest = new ArrayList<String>();
    	if (fechaPrestamo == null) {
    		fPrest.add("");
    	} else {
    		fPrest.add(fechaPrestamo);
    	}

    	ArrayList<String> fDev = new ArrayList<String>();
    	if (fechaDevolucionPrevista == null) {
    		fDev.add("");
    	} else {
    		fDev.add(fechaDevolucionPrevista);
    	}

    	ArrayList<Long> atrasos = new ArrayList<Long>();
    	atrasos.add(diasAtraso);

    	String cuerpo = Correo.cuerpoNotificacionAtraso(nombreCompleto, ids, titulos, fPrest, fDev, atrasos);

    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	Historial historial = new Historial(idUsuario, correo);
    	coleccionHistorial.agregarHistorial(historial);
    }
    
    public void enviarNotificacionAtrasoPorUsuario(String destinatario,
            										int idUsuario,
            										String nombreCompleto,
            										ArrayList<Integer> idsLibros,
            										ArrayList<String> titulosLibros,
            										ArrayList<String> fechasPrestamo,
            										ArrayList<String> fechasDevolucionPrevista,
            										ArrayList<Long> diasAtraso) {

    	int cantidadElementos;
    	if (idsLibros == null) {
    		cantidadElementos = 0;
    	} else {
    		cantidadElementos = idsLibros.size();
    	}

    	String asunto = Correo.asuntoPara(Correo.Tipo.NOTIFICACION_ATRASO, cantidadElementos);

    	String cuerpo = Correo.cuerpoNotificacionAtraso(nombreCompleto,idsLibros,titulosLibros,fechasPrestamo,fechasDevolucionPrevista,diasAtraso);

    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	Historial historial = new Historial(idUsuario, correo);
    	coleccionHistorial.agregarHistorial(historial);
    }
  
}
