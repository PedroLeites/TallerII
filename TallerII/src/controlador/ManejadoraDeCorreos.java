package controlador;

import modelo.Correo;
import modelo.Historial;
import modelo.ColeccionHistorial;
import modelo.Fecha;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

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

    	// 1) Asumto
    	String asunto = Correo.asuntoPara(Correo.Tipo.CONSTANCIA_DEVOLUCION, 1);

    	// 2) Cuerpo
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

    	// 3) Instanciar Correo => envía
    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	// 4) Instanciar y guardar en el historial
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

    	// 1) Controlar cantidad de elementos (ejemplares que debe el usuario) 
    	int cantidadElementos;
    	if (idsLibros == null) {
    		cantidadElementos = 0;
    	} else {
    		cantidadElementos = idsLibros.size();
    	}

    	// 2) Asunto
    	String asunto = Correo.asuntoPara(Correo.Tipo.CONSTANCIA_DEVOLUCION, cantidadElementos);

    	// 3) Cuerpo
    	String fechaEmision = new Fecha().toString();

    	String cuerpo = Correo.cuerpoConstancia(nombreCompleto,idsLibros,titulosLibros,fechasPrestamo,fechasDevolucionPrevista,fechaEmision);

    	// 4) Instanciar Correo => envía
    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	// 5) Instanciar y guardar en el historial
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

    	// 1) Asunto
    	String asunto = Correo.asuntoPara(Correo.Tipo.NOTIFICACION_ATRASO, 1);

    	// 2) Cuerpo
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

    	// 3) Instanciar Correo => envía
    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	// 4) Instanciar y guardar en el historial
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

    	// 1) Controlar cantidad de elementos (ejemplares que debe el usuario)
    	int cantidadElementos;
    	if (idsLibros == null) {
    		cantidadElementos = 0;
    	} else {
    		cantidadElementos = idsLibros.size();
    	}

    	// 2) Asunto
    	String asunto = Correo.asuntoPara(Correo.Tipo.NOTIFICACION_ATRASO, cantidadElementos);

    	// 3) Cuerpo
    	String cuerpo = Correo.cuerpoNotificacionAtraso(nombreCompleto,idsLibros,titulosLibros,fechasPrestamo,fechasDevolucionPrevista,diasAtraso);

    	// 4) Instanciar Correo => envía
    	Correo correo = new Correo(destinatario, asunto, cuerpo);

    	// 5) Instanciar y guardar en el historial
    	Historial historial = new Historial(idUsuario, correo);
    	coleccionHistorial.agregarHistorial(historial);
    }
    
    public void enviarNotificacionesAtrasoATodos(Map<Integer, DatosUsuarioAtraso> mapa) {

        Set<Integer> ids = mapa.keySet();
        Iterator<Integer> it = ids.iterator();

        while (it.hasNext()) {
            Integer idUsuario = it.next();
            DatosUsuarioAtraso d = mapa.get(idUsuario);
            //d.destinatario, d.nombre, d.ids, d.titulos, d.fPrest, d.fDev, d.dias

            this.enviarNotificacionAtrasoPorUsuario(
                    d.destinatario,
                    idUsuario,
                    d.nombre,
                    d.ids,
                    d.titulos,
                    d.fPrest,
                    d.fDev,
                    d.dias
            );
        }
    }

    //POJO auxiliar para agrupar préstamos por usuario
    public static class DatosUsuarioAtraso {
        public String destinatario;
        public String nombre;
        public java.util.ArrayList<Integer> ids = new java.util.ArrayList<>();
        public java.util.ArrayList<String> titulos = new java.util.ArrayList<>();
        public java.util.ArrayList<String> fPrest = new java.util.ArrayList<>();
        public java.util.ArrayList<String> fDev = new java.util.ArrayList<>();
        public java.util.ArrayList<Long> dias = new java.util.ArrayList<>();
    }

  
}
