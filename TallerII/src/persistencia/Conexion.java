package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private Connection conexion;
    private String url;
    private String usuario;
    private String contrasena;
     
    public Conexion(String usuario, String contrasena) {
    	this.url = "jdbc:mysql://localhost:3306/bibliotecainet"
    			+ "?useSSL=false&allowPublicKeyRetrieval=true"
                + "&serverTimezone=America/Montevideo"
                + "&useUnicode=true&characterEncoding=UTF-8";
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.conexion = null;
    }
    
    public boolean conectar() {
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("[BD] Conectado.");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("[BD] Driver JDBC no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("[BD] Error de conexión.");
            e.printStackTrace();
        }
        return false;
    }
    
    public void desconectar() {
    	if (conexion != null) {
            try {
                conexion.close();
                System.out.println("[BD] Desconectado.");
            } catch (SQLException e) {
                System.err.println("[BD] Error al cerrar conexión.");
                e.printStackTrace();
            } finally {
                conexion = null;
            }
        }
    }
    
    public Connection getConexion() {
    	return this.conexion;
    }
    
    public boolean estaConectado() {
        return conexion != null;
    }
    
}
