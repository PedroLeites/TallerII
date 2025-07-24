package modelo;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import modelo.Correo;

public class ServicioDeCorreo {
	private final String remitente = "taller2inet2025@gmail.com";
    private final String contraseña = "taller2inet2025";
    
    public boolean enviar(Correo correo) {
    	boolean seMando = false;
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session sesion = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contraseña);
            }
        });
        
        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo.getDestinatario()));
            mensaje.setSubject(correo.getAsunto());
            mensaje.setText(correo.getCuerpo());

            Transport.send(mensaje);
            seMando = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return seMando;
    }
    
}
