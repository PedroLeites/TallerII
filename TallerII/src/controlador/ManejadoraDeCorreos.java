package controlador;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import modelo.Correo;
import vista.DatosVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ManejadoraDeCorreos implements ActionListener {
	private final String remitente = "leitespedro2004@gmail.com";
    private final String contrase�a = "ymcb uzco roas xppx";
    private DatosVista vista;
    
    public ManejadoraDeCorreos(DatosVista vista) {
        this.vista = vista;
        this.vista.setControladorCorreo(this);
    }
    
    public boolean enviar(Correo correo) {
    	boolean seMando = false;
    	
    	// Configuraci�n para conectarse al servidor SMTP de Gmail
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Crear la sesi�n con autenticaci�n del remitente
        Session sesion = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrase�a);
            }
        });
        
        try {
        	 // Crear mensaje de correo
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(remitente));
            //mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo.getDestinatario()));
            
         //Procesar m�ltiples destinatarios por si aparece m�s de un mail en la celda
            String[] correos = correo.getDestinatario().split("\\s+");
            InternetAddress[] direcciones = new InternetAddress[correos.length];

            for (int i = 0; i < correos.length; i++) {
            	// Validar que sea una direcci�n de correo v�lida
                if (!correos[i].matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    System.out.println("Correo inv�lido: " + correos[i]);
                    continue; // Salta correos inv�lidos
                }
                direcciones[i] = new InternetAddress(correos[i]);
            }

         // Setear los destinatarios del correo
            mensaje.setRecipients(Message.RecipientType.TO, direcciones);
            
         // Asunto y cuerpo del mensaje
            mensaje.setSubject(correo.getAsunto());
            mensaje.setText(correo.getCuerpo());

            // Enviar el mensaje
            Transport.send(mensaje);
            seMando = true;
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return seMando;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) { // Este m�todo se ejecuta cuando se hace clic en el bot�n de "Enviar correo"
        Object[] datos = vista.getDatosFilaSeleccionada();
        if (datos == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila primero.");
            return;
        }

        String fechaPrestamo = datos[0].toString();
        String fechaDevolucion = datos[1].toString();
        String nombre = datos[4].toString();
        String correoDestino = datos[5].toString();
        String tituloLibro = datos[6].toString();

        String asunto = "Reclamo por entrega tard�a de libro";
        String cuerpo = "Estimado/a " + nombre + ",\n\n"
                + "Le recordamos que tiene pendiente la devoluci�n del libro:\n"
                + tituloLibro + "\n"
                + "Fecha de pr�stamo: " + fechaPrestamo + "\n"
                + "Fecha l�mite de devoluci�n: " + fechaDevolucion + "\n\n"
                + "Le solicitamos regularizar su situaci�n lo antes posible.\n\n"
                + "Saludos cordiales,\nBiblioteca INET";

        Correo correo = new Correo(correoDestino, asunto, cuerpo);
        boolean enviado = enviar(correo);

        if (enviado) {
            JOptionPane.showMessageDialog(null, "Correo enviado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error al enviar correo");
        }
    }
    
}
