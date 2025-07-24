package controlador;

import modelo.ServicioDeCorreo;
import modelo.Correo;

public class CorreoControlador {
	private ServicioDeCorreo servicio;

    public CorreoControlador() {
        servicio = new ServicioDeCorreo();
    }

    public boolean enviarCorreo(Correo correo) {
        return servicio.enviar(correo);
    }
}
