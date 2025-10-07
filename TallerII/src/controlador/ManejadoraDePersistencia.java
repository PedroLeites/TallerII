package controlador;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import modelo.Correo;
import modelo.Historial;
import modelo.ColeccionHistorial;
import persistencia.Conexion;

public class ManejadoraDePersistencia {
	private final Conexion con;
	
	public ManejadoraDePersistencia(Conexion c) {
		this.con = c;
	}
	
	//Inserta el envío y devuelve idEnvio generado
	public long insertarHistorial(Historial h) throws SQLException {
        String sql = "INSERT INTO HistorialEnvio "
                   + "(idUsuario, fechaEnvio, horaEnvio, tipoMensaje, asunto, cuerpo) "
                   + "VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = con.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int idUsuario = h.getIdUsuario();
            Correo c = h.getCorreo();

            LocalDate fecha = LocalDate.now(); // o tomalo de tu Correo.Fecha si lo guardás ahí
            LocalTime hora = LocalTime.now();

            ps.setInt(1, idUsuario);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(hora));
            ps.setString(4, c.getTipo().name()); // ATRASO / CONSTANCIA / OTRO
            ps.setString(5, c.getAsunto());
            ps.setString(6, c.getMensaje());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("No se obtuvo idEnvio generado.");
    }
	
	//Consulta el historial por la id del usuario y devuelve una colección
	public ArrayList<Historial> listarHistorialPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT idUsuario, fechaEnvio, horaEnvio, tipoMensaje, asunto, cuerpo "
            + "FROM HistorialEnvio "
            + "WHERE idUsuario = ? "
            + "ORDER BY fechaEnvio DESC, horaEnvio DESC";

        ArrayList<Historial> lista = new ArrayList<>();
        try (PreparedStatement ps = con.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Correo c = new Correo();
                    c.setTipo(Correo.Tipo.valueOf(rs.getString("tipoMensaje")));
                    c.setAsunto(rs.getString("asunto"));
                    c.setMensaje(rs.getString("cuerpo"));
                    // si tu Correo tiene fecha interna, podés setearla desde fechaEnvio/horaEnvio

                    Historial h = new Historial(rs.getInt("idUsuario"), c);
                    lista.add(h);
                }
            }
        }
        return lista;
    }
	
	//Cargar la colección en memoria al iniciar el programa
	public void cargarColeccionHistorial(ColeccionHistorial destino) throws SQLException {
        String sql = "SELECT idUsuario, tipoMensaje, asunto, cuerpo FROM HistorialEnvio";
        try (Statement st = con.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Correo c = new Correo();
                c.setTipo(Correo.Tipo.valueOf(rs.getString("tipoMensaje")));
                c.setAsunto(rs.getString("asunto"));
                c.setMensaje(rs.getString("cuerpo"));
                destino.agregarHistorial(new Historial(rs.getInt("idUsuario"), c));
            }
        }
    }
	
}
