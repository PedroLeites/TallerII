import vista.PrincipalVista;
import controlador.PrincipalControlador;
import javax.swing.SwingUtilities;

public class Principal {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            PrincipalVista view = new PrincipalVista();
            new PrincipalControlador(view);
            view.setVisible(true);
        });
	}
}
