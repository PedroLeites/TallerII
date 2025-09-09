package controlador;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import modelo.Correo;

public class ManejadoraDeCorreos {
	private String remitente = "leitespedro2004@gmail.com";
    private String contrasenia = "ymcb uzco roas xppx";
    private Properties props;
    
    public ManejadoraDeCorreos() {
    	// Configuración para conectarse al servidor SMTP de Gmail
    	props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }
    
    public ManejadoraDeCorreos(String remitente, String contrasenia) {
        this();
        this.remitente = remitente;
        this.contrasenia = contrasenia;
    }
    
    public boolean enviar(Correo correo) {
    	boolean seMando = false;
    	
    	try {
    		List<String> destinos = correo.destinatariosValidos();
    		
    		// Crear la sesión con autenticación del remitente
    		Session sesion = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, contrasenia);
                }
            });
    		
    		// Crear mensaje de correo
    		Message mensaje = new MimeMessage(sesion);
    		mensaje.setFrom(new InternetAddress(remitente));
             
    		InternetAddress[] direcciones = new InternetAddress[destinos.size()];
    		for (int i = 0; i < destinos.size(); i++) {
    			direcciones[i] = new InternetAddress(destinos.get(i));
    		}
            mensaje.setRecipients(Message.RecipientType.TO, direcciones);
            mensaje.setSubject(correo.getAsunto());
            mensaje.setText(correo.getCuerpo());

            Transport.send(mensaje);
            seMando = true;
    	} catch (MessagingException e) {
    		e.printStackTrace();
    	}
    	return seMando;
    }
    
    // Notificación por préstamo (fila)
    public boolean enviarNotificacionAtrasoPorPrestamo(String destinatario,
                                                       int cedula,
                                                       String nombreCompleto,
                                                       int idLibro,
                                                       String tituloLibro,
                                                       String fechaPrestamo,
                                                       String fechaDevolucionPrevista,
                                                       long diasAtraso) {
    	
        Correo c = new Correo(destinatario, cedula, nombreCompleto,
                              idLibro, tituloLibro, fechaPrestamo, fechaDevolucionPrevista, diasAtraso,
                              Correo.Tipo.NOTIFICACION_ATRASO);
        return enviar(c);
    }
    
    // Notificación por usuario (varios préstamos en un solo correo)
    public boolean enviarNotificacionAtrasoPorUsuario(String destinatario,
                                                      int cedula,
                                                      String nombreCompleto,
                                                      List<Integer> idsLibros,
                                                      List<String> titulosLibros,
                                                      List<String> fechasPrestamo,
                                                      List<String> fechasDevolucionPrevista,
                                                      List<Long> diasAtraso) {
    	
        Correo c = new Correo(destinatario, cedula, nombreCompleto,
                              idsLibros, titulosLibros, fechasPrestamo, fechasDevolucionPrevista, diasAtraso,
                              Correo.Tipo.NOTIFICACION_ATRASO);
        return enviar(c);
    }
    
    // Constancia por préstamo (fila)
    public boolean enviarConstanciaPorPrestamo(String destinatario,
                                               int cedula,
                                               String nombreCompleto,
                                               int idLibro,
                                               String tituloLibro,
                                               String fechaPrestamo,
                                               String fechaDevolucionPrevista) {
        Correo c = new Correo(destinatario, cedula, nombreCompleto,
                              idLibro, tituloLibro, fechaPrestamo, fechaDevolucionPrevista, 0L,
                              Correo.Tipo.CONSTANCIA_DEVOLUCION);
        return enviar(c);
    }
    
    // Constancias por usuario (varios préstamos en un solo correo)
    public boolean enviarConstanciasPorUsuario(String destinatario,
                                               int cedula,
                                               String nombreCompleto,
                                               List<Integer> idsLibros,
                                               List<String> titulosLibros,
                                               List<String> fechasPrestamo,
                                               List<String> fechasDevolucionPrevista) { 
    	
        int n = 0;
        if (idsLibros != null) {
            n = idsLibros.size();
        }
        ArrayList<Long> dias = new ArrayList<Long>();
        for (int i = 0; i < n; i++) {
            dias.add(0L);
        }

        Correo c = new Correo(destinatario, cedula, nombreCompleto,
                              idsLibros, titulosLibros, fechasPrestamo, fechasDevolucionPrevista, dias,
                              Correo.Tipo.CONSTANCIA_DEVOLUCION);
        return enviar(c);
    }
    
}
