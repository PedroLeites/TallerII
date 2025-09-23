package modelo;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import java.time.LocalDate;
import java.util.ArrayList;

public class Correo {
	public enum Tipo {NOTIFICACION_ATRASO, CONSTANCIA_DEVOLUCION}

    private Fecha fechaEmision;
    private String destinatario;
    private String remitente;
    private String contrasenia;
    private String asunto;
    private String mensaje;

    //Constructores
    public Correo() {
        
    }
    
    public Correo(String destinatario, String asunto, String mensaje) {
    	LocalDate hoy = LocalDate.now();
    	this.fechaEmision = new Fecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
    	
    	this.destinatario = destinatario;
    	
    	this.remitente = "leitespedro2004@gmail.com";
    	this.contrasenia = "ymcb uzco roas xppx";
    	
    	this.asunto = asunto;
    	this.mensaje = mensaje;
    	
    	enviar();
    	
    }

    //Getters y Setters
    public Fecha getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Fecha fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String cuerpo) { this.mensaje = cuerpo; }
    
    //M�todos Espec�ficos
    private void enviar() {
    	
		
		try {
			//Controlar celdas con varias direcciones de correo
			ArrayList<String> destinos = destinatariosValidos(this.destinatario);
            if (destinos.isEmpty()) {
                System.err.println("Correo: no hay destinatarios v�lidos.");
                return;
            }
            
            if (this.remitente.isEmpty() || this.contrasenia.isEmpty()) {
                System.err.println("Correo: remitente o contrase�a no configurados.");
                return;
            }
			
			//Configuraci�n para conectarse al servidor SMTP de Gmail
	    	Properties props = new Properties();
	    	props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        
	        //Crear la sesi�n con autenticaci�n del remitente
			Session sesion = Session.getInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(remitente, contrasenia);
	            }
	        });
			
			//Creaci�n del mensaje
			Message msj = new MimeMessage(sesion);
    		msj.setFrom(new InternetAddress(remitente));
    		
    		InternetAddress[] direcciones = new InternetAddress[destinos.size()];
    		for (int i = 0; i < destinos.size(); i++) {
    			direcciones[i] = new InternetAddress(destinos.get(i));
    		}
            msj.setRecipients(Message.RecipientType.TO, direcciones);
            msj.setSubject(asunto);
            msj.setText(mensaje);

            Transport.send(msj);
    		
            System.out.println("Mensaje enviado con �xito");
            
		} catch (MessagingException e) {
    		e.printStackTrace();
    	}
    }
    
 @Override
	public String toString() {
		return "Correo [fechaEmision=" + fechaEmision + ", destinatario=" + destinatario + ", remitente=" + remitente
				+ ", contrasenia=" + contrasenia + ", asunto=" + asunto + ", mensaje=" + mensaje + "]";
	}

	// Asuntos
    public static String asuntoPara(Tipo tipo, int cantidadPrestamos) {
        if (tipo == Tipo.CONSTANCIA_DEVOLUCION) {
            return (cantidadPrestamos <= 1) ? "Constancia de devoluci�n de libro"
                                            : "Constancias de devoluci�n (" + cantidadPrestamos + " pr�stamos)";
        }
        // NOTIFICACION_ATRASO
        return (cantidadPrestamos <= 1) ? "Notificaci�n de devoluci�n pendiente"
                                        : "Notificaci�n de devoluci�n pendiente (" + cantidadPrestamos + " pr�stamos)";
    }

    // Cuerpo para notificaci�n de atraso
    public static String cuerpoNotificacionAtraso(String nombreCompletoUsuario,
                                                  int[] idsLibros,
                                                  String[] titulosLibros,
                                                  String[] fechasPrestamo,
                                                  String[] fechasDevolucionPrevista,
                                                  long[] diasAtraso) {
        String s = "Estimado/a " + nvl(nombreCompletoUsuario) + ",\n\n" +
                   "Le recordamos que tiene devoluci�n pendiente de los siguientes materiales:\n\n";
        long max = 0L;
        int n = (idsLibros != null) ? idsLibros.length : 0;

        for (int i = 0; i < n; i++) {
            s += "- T�tulo: \"" + nvl(getStr(titulosLibros, i)) + "\" (ID: " + getInt(idsLibros, i) + ")\n";
            s += "  � Fecha pr�stamo: " + nvl(getStr(fechasPrestamo, i)) + "\n";
            s += "  � Fecha l�mite: " + nvl(getStr(fechasDevolucionPrevista, i)) + "\n";
            long d = getLong(diasAtraso, i);
            s += "  � D�as de atraso: " + d + "\n\n";
            if (d > max) max = d;
        }

        s += "El mayor atraso registrado es de " + max + " d�a(s).\n\n" +
             "Por favor, regularice su situaci�n a la brevedad.\n\n" +
             "Saludos cordiales,\nBiblioteca INET";
        return s;
    }

    // Cuerpo para constancias de devoluci�n
    public static String cuerpoConstancia(String nombreCompletoUsuario,
                                          int[] idsLibros,
                                          String[] titulosLibros,
                                          String[] fechasPrestamo,
                                          String[] fechasDevolucionPrevista,
                                          String fechaEmision) {
        String s = "Estimado/a " + nvl(nombreCompletoUsuario) + ",\n\n";
        int n = (idsLibros != null) ? idsLibros.length : 0;

        if (n <= 1) {
            s += "Se emite constancia de devoluci�n del siguiente material:\n\n";
        } else {
            s += "Se emiten constancias de devoluci�n de los siguientes materiales:\n\n";
        }

        for (int i = 0; i < n; i++) {
            s += "- T�tulo: \"" + nvl(getStr(titulosLibros, i)) + "\" (ID: " + getInt(idsLibros, i) + ")\n";
            s += "  � Fecha pr�stamo: " + nvl(getStr(fechasPrestamo, i)) + "\n";
            s += "  � Fecha l�mite: " + nvl(getStr(fechasDevolucionPrevista, i)) + "\n\n";
        }

        s += "Fecha de emisi�n: " + nvl(fechaEmision) + "\n\n" +
             "Gracias por su colaboraci�n.\n\n" +
             "Atentamente,\nBiblioteca INET";
        return s;
    }
    
    private static boolean esCorreoValido(String email) {
        if (email == null) return false;
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }

    private static ArrayList<String> destinatariosValidos(String texto) {
        ArrayList<String> out = new ArrayList<String>();
        if (texto == null) return out;
        String t = texto.trim();
        if (t.isEmpty()) return out;
        String[] arr = t.split("[,;\\s]+");
        for (int i = 0; i < arr.length; i++) {
            if (esCorreoValido(arr[i])) out.add(arr[i]);
        }
        return out;
    }
    
    private static String nvl(String s) { return (s == null) ? "" : s; }
    private static String getStr(String[] a, int i) { return (a != null && i >= 0 && i < a.length) ? a[i] : ""; }
    private static int getInt(int[] a, int i) { return (a != null && i >= 0 && i < a.length) ? a[i] : 0; }
    private static long getLong(long[] a, int i) { return (a != null && i >= 0 && i < a.length) ? a[i] : 0L; }

	
}
