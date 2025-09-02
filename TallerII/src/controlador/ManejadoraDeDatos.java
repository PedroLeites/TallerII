package controlador;

import modelo.Fecha;
import modelo.Prestamo;
import modelo.ColeccionPrestamos;
import modelo.Usuario;
import modelo.ColeccionUsuarios;
import modelo.Datos;

public class ManejadoraDeDatos {
	private Datos datos;
    private ColeccionUsuarios usuarios;
    
    public ManejadoraDeDatos() {
    	this.datos = new Datos();
    	this.usuarios = new ColeccionUsuarios();
    }
    
    public ManejadoraDeDatos(Datos d) {
        this.datos = d;
        this.usuarios = new ColeccionUsuarios();
    }
    
    private void procesarCadena() {
    	usuarios = new ColeccionUsuarios(); // Reiniciar la colección de usuarios si ya se había cargado antes
    	
        // Obtenemos el contenido crudo del archivo Excel como una sola cadena
        String contenido = datos.getCadena();

        // Separamos esa cadena en filas usando el separador ';'
        String[] filas = contenido.split(";");

        // Iteramos desde i = 1 para saltar encabezados o título
        for (int i = 1; i < filas.length; i++) {
            String fila = filas[i].trim();

            // Si la fila está vacía, no procesamos
            if (fila.isEmpty()) continue;

            // Separamos la fila en celdas usando la coma como separador
            String[] celdas = fila.split(",");

            // Si no tiene al menos 15 celdas, descartamos
            if (celdas.length < 15) continue;

            // Si la primera celda no parece una fecha válida (dd/MM/yyyy), salteamos
            if (celdas[0].trim().length() < 8 || !celdas[0].trim().matches("\\d{2}/\\d{2}/\\d{4}")) continue;

            try {
                // Interpretamos las fechas
                Fecha fechaPrestamo = new Fecha(celdas[0].trim());
                Fecha fechaDevolucion = new Fecha(celdas[1].trim());
                long diasRetraso = calcularDiasRetraso(fechaPrestamo, fechaDevolucion);

                // Datos del usuario
                int idUsuario = (int) Double.parseDouble(celdas[3].trim());
                String apellido = celdas[4].trim();
                String nombre = celdas[5].trim();
                String correo = celdas[6].trim();
                int ci = (int) Double.parseDouble(celdas[8].trim());

                // Datos del libro
                int idLibro = (int) Double.parseDouble(celdas[10].trim());
                String titulo = celdas[14].trim();

                // Creamos el préstamo
                Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, diasRetraso, idLibro, titulo);

                // Si el usuario ya existe, lo reutilizamos; si no, lo creamos
                Usuario usuario;
                if (usuarios.pertenece(idUsuario)) {
                    usuario = usuarios.obtenerUsuario(idUsuario);
                } else {
                    usuario = new Usuario(idUsuario, ci, nombre, apellido, correo);
                    usuarios.agregarUsuario(usuario);
                    System.out.println("Importando usuario ID: " + idUsuario + " - " + nombre + " " + apellido);
                }

                // Asociamos el préstamo al usuario
                usuario.agregarPrestamo(prestamo);

            } catch (Exception e) {
                // Capturamos errores de formato y los informamos por consola
                System.out.println("Error procesando fila " + i + ": " + e.getMessage());
            }
        }

        // Imprimir cuántos usuarios se importaron al final
        System.out.println("Total de usuarios importados: " + usuarios.largo());
    }
    
    public void cargarDatos() {
        procesarCadena();
    }
    
    public String mostrarTodosLosPrestamos() {
        if (this.usuarios == null || this.usuarios.vacia()) {
            return "No hay datos cargados.";
        }
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("ColeccionUsuarios {total=").append(this.usuarios.largo()).append("}").append(ls);

        java.util.ArrayList<Usuario> lista = this.usuarios.getUsuarios();
        for (int i = 0; i < lista.size(); i++) {
            Usuario u = lista.get(i);
            if (u != null) {
                sb.append("Usuario ").append(u.getId())
                  .append(" - ").append(u.getNombreCompleto())
                  .append(" <").append(u.getCorreo()).append(">").append(ls);
                sb.append(u.getPrestamosDeUsuario().toString()).append(ls).append(ls);
            }
        }
        return sb.toString();
    }
    
    public String mostrarPrestamosDeUsuario(int idUsuario) {
        Usuario u = obtenerUsuarioPorId(idUsuario);
        if (u == null) {
            return "No existe un usuario con ese ID o no hay datos cargados.";
        }
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario ").append(u.getId())
          .append(" - ").append(u.getNombreCompleto())
          .append(" <").append(u.getCorreo()).append(">").append(ls);
        sb.append(u.getPrestamosDeUsuario().toString());
        return sb.toString();
    }
    
    private long calcularDiasRetraso(Fecha fechaPrestamo, Fecha fechaDevolucionPrevista) {
    	long dias = 0;
    	Fecha hoy = new Fecha(java.time.LocalDate.now().getDayOfMonth(),
                java.time.LocalDate.now().getMonthValue(),
                java.time.LocalDate.now().getYear());

    	if (hoy.toLocalDate().isAfter(fechaDevolucionPrevista.toLocalDate())) {
    		dias = Fecha.diasEntre(fechaDevolucionPrevista, hoy);
    	}
    	
    	return dias;
    }

    public ColeccionUsuarios obtenerUsuarios() {
        return usuarios;
    }
    
    public Usuario obtenerUsuarioPorId(int id) {
    	return usuarios.obtenerUsuario(id);
    }
    
    public ColeccionPrestamos obtenerPrestamosDeUsuario(int idUsuario) {
        Usuario u = obtenerUsuarioPorId(idUsuario);
        if (u == null) {
            return null;
        }
        return u.getPrestamosDeUsuario();
    }

    public Datos getDatos() {
        return datos;
    }
    
}