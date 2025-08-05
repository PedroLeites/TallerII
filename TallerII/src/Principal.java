import vista.DatosVista;
import controlador.ManejadoraDeDatos;
import controlador.ManejadoraDeCorreos;
import javax.swing.SwingUtilities;

public class Principal {
	public static void main(String[] args) {
		
		DatosVista ventana = new DatosVista();
		ManejadoraDeDatos controladorVentana = new ManejadoraDeDatos(ventana);
		ManejadoraDeCorreos controladorMails = new ManejadoraDeCorreos(ventana);
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
