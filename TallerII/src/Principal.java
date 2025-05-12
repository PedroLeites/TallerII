import vista.PrincipalVista;
import controlador.PrincipalControlador;
import javax.swing.SwingUtilities;

public class Principal {
	public static void main(String[] args) {
		
		PrincipalVista ventana = new PrincipalVista();
		PrincipalControlador controladorVentana = new PrincipalControlador(ventana);
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
