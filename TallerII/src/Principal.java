import vista.DatosVista;
import controlador.DatosControlador;
import controlador.CorreoControlador;
import javax.swing.SwingUtilities;

public class Principal {
	public static void main(String[] args) {
		
		DatosVista ventana = new DatosVista();
		DatosControlador controladorVentana = new DatosControlador(ventana);
		CorreoControlador controladorMails = new CorreoControlador(ventana);
		ventana.setVisible(true);
		
		/*
		SwingUtilities.invokeLater(() -> {
            PrincipalVista view = new PrincipalVista();
            new PrincipalControlador(view);
            view.setVisible(true);
        });
        */
	}
}
