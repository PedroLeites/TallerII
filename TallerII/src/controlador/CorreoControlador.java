package controlador;

import modelo.ServicioDeCorreo;
import modelo.Correo;
import vista.DatosVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CorreoControlador implements ActionListener {
	private DatosVista vista;
	private ServicioDeCorreo servicio;

	public CorreoControlador(DatosVista vista) {
        this.vista = vista;
        this.servicio = new ServicioDeCorreo();
        this.vista.setControladorCorreo(this);
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
        boolean enviado = servicio.enviar(correo);

        if (enviado) {
            JOptionPane.showMessageDialog(null, "Correo enviado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error al enviar correo");
        }
    }

    public boolean enviarCorreo(Correo correo) {
        return servicio.enviar(correo);
    }
}
