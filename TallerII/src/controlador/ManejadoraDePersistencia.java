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
	    if (con == null || con.getConexion() == null) {
	        if (con == null || !con.conectar()) {
	            throw new SQLException("Sin conexión a la BD.");
	        }
	    }

	    String sql = "INSERT INTO HistorialEnvio "
	               + "(idUsuario, fechaEnvio, tipoMensaje, asunto, cuerpo) "
	               + "VALUES (?,?,?,?,?)";

	    try (PreparedStatement ps = con.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        int idUsuario = h.getIdUsuario();
	        Correo c = h.getCorreo();

	        java.time.LocalDate fecha;
	        if (c != null && c.getFechaEmision() != null) {
	            int d = c.getFechaEmision().getDia();
	            int m = c.getFechaEmision().getMes();
	            int y = c.getFechaEmision().getAnio();
	            fecha = java.time.LocalDate.of(y, m, d);
	        } else {
	            fecha = java.time.LocalDate.now();
	        }

	        ps.setInt(1, idUsuario);
	        ps.setDate(2, Date.valueOf(fecha));
	        ps.setString(3, (c != null && c.getTipo() != null) ? c.getTipo().name() : "OTRO");
	        ps.setString(4, (c != null) ? c.getAsunto() : "");
	        ps.setString(5, (c != null) ? c.getMensaje() : "");

	        int rows = ps.executeUpdate();
	        if (rows == 0) throw new SQLException("Insert no afectó filas.");

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) return rs.getLong(1);
	        }
	    }
	    throw new SQLException("No se obtuvo idEnvio generado.");
	}

	
	//Consulta el historial por la id del usuario y devuelve una colección
	public ArrayList<Historial> listarHistorialPorUsuario(int idUsuario) throws SQLException {
	    // 1) Asegurar conexión
	    if (con == null) {
	        throw new SQLException("ManejadoraDePersistencia: la referencia a Conexion es null.");
	    }
	    if (con.getConexion() == null) {
	        boolean ok = con.conectar();
	        if (!ok || con.getConexion() == null) {
	            throw new SQLException("No se pudo abrir conexión a la base de datos.");
	        }
	    }

	    // 2) Consulta
	    String sql = "SELECT idUsuario, fechaEnvio, tipoMensaje, asunto, cuerpo "
	               + "FROM HistorialEnvio "
	               + "WHERE idUsuario = ? "
	               + "ORDER BY fechaEnvio DESC";

	    ArrayList<Historial> lista = new ArrayList<>();

	    try (PreparedStatement ps = con.getConexion().prepareStatement(sql)) {
	        ps.setInt(1, idUsuario);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                // Correo
	                Correo c = new Correo();

	                // tipoMensaje puede venir null; evitá valueOf(null)
	                String tipoSql = rs.getString("tipoMensaje");
	                if (tipoSql != null) {
	                    try {
	                        c.setTipo(Correo.Tipo.valueOf(tipoSql));
	                    } catch (IllegalArgumentException iae) {
	                        // Si en BD hay un literal inesperado, podés mapear a un default o dejar sin tipo
	                        System.err.println("[WARN] tipoMensaje desconocido en BD: " + tipoSql);
	                    }
	                }

	                String asunto = rs.getString("asunto");
	                if (asunto != null) c.setAsunto(asunto);

	                String cuerpo = rs.getString("cuerpo");
	                if (cuerpo != null) c.setMensaje(cuerpo);

	                // reconstruir fecha en Correo a partir de la DATE de la tabla
	                java.sql.Date f = rs.getDate("fechaEnvio");
	                if (f != null) {
	                    java.time.LocalDate ld = f.toLocalDate();
	                    // ajustá el paquete si tu clase Fecha está en otro lado
	                    modelo.Fecha fechaEmision = new modelo.Fecha(ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear());
	                    c.setFechaEmision(fechaEmision);
	                }

	                // Historial
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
