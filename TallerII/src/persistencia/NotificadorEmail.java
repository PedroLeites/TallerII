package persistencia;

import modelo.Estudiante;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class NotificadorEmail {
	private final String remitente = "taller2inet2025@gmail.com";
    private final String contraseña = "taller2inet2025PFJM";
    
    public void enviarMail(Estudiante est) {
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contraseña);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(est.getEmail()));
            mensaje.setSubject("Aviso de préstamo vencido");
            mensaje.setText("Estimado/a " + est.getNombre() + ", su préstamo del libro '" + est.getLibro() +
                "' venció el " + est.getFechaVencimiento() + ".\nPor favor regularice su situación.");
            Transport.send(mensaje);
        } catch (Exception e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }
    
}
