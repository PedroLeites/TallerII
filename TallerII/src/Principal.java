import vista.DatosVista;
import controlador.ManejadoraDeDatos;
import controlador.ManejadoraDeCorreos;
import javax.swing.SwingUtilities;

public class Principal {
	public static void main(String[] args) {
		
		DatosVista ventana = new DatosVista();
		ManejadoraDeDatos controladorDatos = new ManejadoraDeDatos();
		ventana.setControladorDatos(controladorDatos);
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
