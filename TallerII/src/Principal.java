import vista.DatosVista;
import controlador.ManejadoraDeDatos;
import controlador.ManejadoraDeCorreos;
import controlador.ManejadoraDePersistencia;
import persistencia.Conexion;
import javax.swing.SwingUtilities;

public class Principal {
	public static void main(String[] args) {
		
		DatosVista ventana = new DatosVista();
		
		ManejadoraDeDatos controladorDatos = new ManejadoraDeDatos();
		ManejadoraDeCorreos controladorCorreos = new ManejadoraDeCorreos();
		
		Conexion conex = new Conexion("root","1234");
		conex.conectar();
		ManejadoraDePersistencia controladorPersistencia = new ManejadoraDePersistencia(conex);
		controladorCorreos.setManejadoraDePersistencia(controladorPersistencia);
		
		
		ventana.setControladorDatos(controladorDatos);
		ventana.setControladorCorreo(controladorCorreos);
		ventana.setControladorPersistencia(controladorPersistencia);
		
		ventana.setVisible(true);
		
		/*
		SwingUtilities.invokeLater(() -> {
            PrincipalVista view = new PrincipalVista();
            new PrincipalControlador(view);
            view.setVisible(true);
        });*/
        
	}
}
