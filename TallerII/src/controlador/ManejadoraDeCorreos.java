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
    private final String contraseña = "ymcb uzco roas xppx";
    private DatosVista vista;
    
    public ManejadoraDeCorreos(DatosVista vista) {
        this.vista = vista;
        this.vista.setControladorCorreo(this);
    }
    
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
            //mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo.getDestinatario()));
            
            String[] correos = correo.getDestinatario().split("\\s+");
            InternetAddress[] direcciones = new InternetAddress[correos.length];

            for (int i = 0; i < correos.length; i++) {
                if (!correos[i].matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    System.out.println("Correo inválido: " + correos[i]);
                    continue; 
                }
                direcciones[i] = new InternetAddress(correos[i]);
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
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

        String asunto = "Reclamo por entrega tardía de libro";
        String cuerpo = "Estimado/a " + nombre + ",\n\n"
                + "Le recordamos que tiene pendiente la devolución del libro:\n"
                + tituloLibro + "\n"
                + "Fecha de préstamo: " + fechaPrestamo + "\n"
                + "Fecha límite de devolución: " + fechaDevolucion + "\n\n"
                + "Le solicitamos regularizar su situación lo antes posible.\n\n"
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
