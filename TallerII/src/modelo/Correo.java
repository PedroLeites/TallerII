package modelo;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Correo {
	public enum Tipo {NOTIFICACION_ATRASO, CONSTANCIA_DEVOLUCION}

    private Fecha fechaEmision;
    private String destinatario;
    private String remitente;
    private String contrasenia;
    private String asunto;
    private String mensaje;

    //Constructores
    public Correo() {
        
    }
    
    public Correo(String destinatario, String asunto, String mensaje) {
    	LocalDate hoy = LocalDate.now();
    	this.fechaEmision = new Fecha(hoy.getDayOfMonth(), hoy.getMonthValue(), hoy.getYear());
    	
    	this.destinatario = destinatario;
    	
    	this.remitente = "leitespedro2004@gmail.com";
    	this.contrasenia = "ymcb uzco roas xppx";
    	
    	this.asunto = asunto;
    	this.mensaje = mensaje;
    	
    	enviar();
    	
    }

    //Getters y Setters
    public Fecha getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Fecha fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String cuerpo) { this.mensaje = cuerpo; }
    
    //Métodos Específicos
    private void enviar() {
    	boolean ok = enviarInterno();
    	if (!ok) {
    		throw new RuntimeException("No se pudo enviar el correo.");
    	}
    }
    
    private boolean enviarInterno() {
    	boolean seMando = true;
    	try {
			//Controlar celdas con varias direcciones de correo
			ArrayList<String> destinos = destinatariosValidos(this.destinatario);
            if (destinos.isEmpty()) {
                System.err.println("Correo: no hay destinatarios válidos.");
                seMando = false;
            }
            
            if (this.remitente.isEmpty() || this.contrasenia.isEmpty()) {
                System.err.println("Correo: remitente o contraseña no configurados.");
                seMando = false;
            }
			
			//Configuración para conectarse al servidor SMTP de Gmail
	    	Properties props = new Properties();
	    	props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        
	        //Crear la sesión con autenticación del remitente
			Session sesion = Session.getInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(remitente, contrasenia);
	            }
	        });
			
			//Creación del mensaje
			Message msj = new MimeMessage(sesion);
    		msj.setFrom(new InternetAddress(remitente));
    		
    		InternetAddress[] direcciones = new InternetAddress[destinos.size()];
    		for (int i = 0; i < destinos.size(); i++) {
    			direcciones[i] = new InternetAddress(destinos.get(i));
    		}
            msj.setRecipients(Message.RecipientType.TO, direcciones);
            msj.setSubject(asunto);
            msj.setText(mensaje);

            Transport.send(msj);
            seMando = true;	
            System.out.println("Mensaje enviado con éxito");
            
		} catch (MessagingException e) {
    		e.printStackTrace();
    		seMando = false;
    	}
    	return seMando;
    }
    
 @Override
	public String toString() {
		return "Correo [fechaEmision=" + fechaEmision + ", destinatario=" + destinatario + ", remitente=" + remitente
				+ ", contrasenia=" + contrasenia + ", asunto=" + asunto + ", mensaje=" + mensaje + "]";
	}

	// Asuntos
 	public static String asuntoPara(Tipo tipo, int cantidadPrestamos) {
 		String asunto;
 		if (tipo == Tipo.CONSTANCIA_DEVOLUCION) {
 			if (cantidadPrestamos <= 1) {
 				asunto = "Constancia de devolución de libro";
	        } else {
	            asunto = "Constancias de devolución (" + cantidadPrestamos + " préstamos)";
	        }
 		} else {
	        // Caso: NOTIFICACION_ATRASO
	        if (cantidadPrestamos <= 1) {
	            asunto = "Notificación de devolución pendiente";
	        } else {
	            asunto = "Notificación de devolución pendiente (" + cantidadPrestamos + " préstamos)";
	        }
	    }

	    return asunto;
	}
 	
    // Cuerpo para notificación de atraso
 	public static String cuerpoNotificacionAtraso(String nombreCompletoUsuario,
 													List<Integer> idsLibros,
 													List<String> titulosLibros,
 													List<String> fechasPrestamo,
 													List<String> fechasDevolucionPrevista,
 													List<Long> diasAtraso) {

 		String nombreSeguro;
 		if (nombreCompletoUsuario == null) {
 			nombreSeguro = "";
 		} else {
 			nombreSeguro = nombreCompletoUsuario;
 		}

 		StringBuilder cuerpo = new StringBuilder();
 		cuerpo.append("Estimado/a ").append(nombreSeguro).append(",\n\n");
 		cuerpo.append("Le recordamos que tiene devolución pendiente de los siguientes materiales:\n\n");

 		int cantidadElementos;
 		if (idsLibros == null) {
 			cantidadElementos = 0;
 		} else {
 			cantidadElementos = idsLibros.size();
 		}

 		long mayorAtraso = 0L;

 		int i = 0;
 		while (i < cantidadElementos) {
 			// id
 			int idSeguro;
 			Integer idObj = idsLibros.get(i);
 			if (idObj == null) {
 				idSeguro = 0;
 			} else {
 				idSeguro = idObj;
 			}

 			// título
 			String tituloSeguro;
 			if (titulosLibros != null && titulosLibros.size() > i) {
 				String titulo = titulosLibros.get(i);
 				if (titulo == null) {
 					tituloSeguro = "";
 				} else {
 					tituloSeguro = titulo;
 				}
 			} else {
 				tituloSeguro = "";
 			}

 			// fecha préstamo
 			String fechaPrestamoSegura;
 			if (fechasPrestamo != null && fechasPrestamo.size() > i) {
 				String fp = fechasPrestamo.get(i);
 				if (fp == null) {
 					fechaPrestamoSegura = "";
 				} else {
 					fechaPrestamoSegura = fp;
 				}
 			} else {
 				fechaPrestamoSegura = "";
 			}

 			// fecha devolución prevista
 			String fechaDevolucionSegura;
 			if (fechasDevolucionPrevista != null && fechasDevolucionPrevista.size() > i) {
 				String fd = fechasDevolucionPrevista.get(i);
 				if (fd == null) {
 					fechaDevolucionSegura = "";
 				} else {
 					fechaDevolucionSegura = fd;
 				}
 			} else {
 				fechaDevolucionSegura = "";
 			}

 			// días de atraso
 			long diasAtrasoSeguro;
 			if (diasAtraso != null && diasAtraso.size() > i) {
 				Long d = diasAtraso.get(i);
 				if (d == null) {
 					diasAtrasoSeguro = 0L;
 				} else {
 					diasAtrasoSeguro = d;
 				}
 			} else {
 				diasAtrasoSeguro = 0L;
 			}

 			cuerpo.append("- Título: \"").append(tituloSeguro).append("\" (ID: ").append(idSeguro).append(")\n");
 			cuerpo.append("  • Fecha préstamo: ").append(fechaPrestamoSegura).append("\n");
 			cuerpo.append("  • Fecha límite: ").append(fechaDevolucionSegura).append("\n");
 			cuerpo.append("  • Días de atraso: ").append(diasAtrasoSeguro).append("\n\n");

 			if (diasAtrasoSeguro > mayorAtraso) {
 				mayorAtraso = diasAtrasoSeguro;
 			}

 			i = i + 1;
 		}

 		cuerpo.append("El mayor atraso registrado es de ").append(mayorAtraso).append(" día(s).\n\n");
 		cuerpo.append("Por favor, regularice su situación a la brevedad.\n\n");
 		cuerpo.append("Saludos cordiales,\nBiblioteca INET");

 		return cuerpo.toString();
 	}


 	//Cuerpo para constancias de devolución
 	public static String cuerpoConstancia(String nombreCompletoUsuario,
 											List<Integer> idsLibros,
 											List<String> titulosLibros,
 											List<String> fechasPrestamo,
 											List<String> fechasDevolucionPrevista,
 											String fechaEmision) {

 		String nombreSeguro;
 		if (nombreCompletoUsuario == null) {
 			nombreSeguro = "";
 		} else {
 			nombreSeguro = nombreCompletoUsuario;
 		}

 		String fechaEmisionSegura;
 		if (fechaEmision == null) {
 			fechaEmisionSegura = "";
 		} else {
 			fechaEmisionSegura = fechaEmision;
 		}

 		StringBuilder cuerpo = new StringBuilder();
 		cuerpo.append("Estimado/a ").append(nombreSeguro).append(",\n\n");

 		int cantidadElementos;
 		if (idsLibros == null) {
 			cantidadElementos = 0;
 		} else {
 			cantidadElementos = idsLibros.size();
 		}

 		if (cantidadElementos <= 1) {
 			cuerpo.append("Se emite constancia de devolución del siguiente material:\n\n");
 		} else {
 			cuerpo.append("Se emiten constancias de devolución de los siguientes materiales:\n\n");
 		}

 		int i = 0;
 		while (i < cantidadElementos) {
 			// id
 			int idSeguro;
 			Integer idObj = idsLibros.get(i);
 			if (idObj == null) {
 				idSeguro = 0;
 			} else {
 				idSeguro = idObj;
 			}

 			// título
 			String tituloSeguro;
 			if (titulosLibros != null && titulosLibros.size() > i) {
 				String titulo = titulosLibros.get(i);
 				if (titulo == null) {
 					tituloSeguro = "";
 				} else {
 					tituloSeguro = titulo;
 				}
 			} else {
 				tituloSeguro = "";
 			}

 			// fecha préstamo
 			String fechaPrestamoSegura;
 			if (fechasPrestamo != null && fechasPrestamo.size() > i) {
 				String fp = fechasPrestamo.get(i);
 				if (fp == null) {
 					fechaPrestamoSegura = "";
 				} else {
 					fechaPrestamoSegura = fp;
 				}
 			} else {
 				fechaPrestamoSegura = "";
 			}

 			// fecha devolución prevista
 			String fechaDevolucionSegura;
 			if (fechasDevolucionPrevista != null && fechasDevolucionPrevista.size() > i) {
 				String fd = fechasDevolucionPrevista.get(i);
 				if (fd == null) {
 					fechaDevolucionSegura = "";
 				} else {
 					fechaDevolucionSegura = fd;
 				}
 			} else {
 				fechaDevolucionSegura = "";
 			}

 			cuerpo.append("- Título: \"").append(tituloSeguro).append("\" (ID: ").append(idSeguro).append(")\n");
 			cuerpo.append("  • Fecha préstamo: ").append(fechaPrestamoSegura).append("\n");
 			cuerpo.append("  • Fecha límite: ").append(fechaDevolucionSegura).append("\n\n");

 			i = i + 1;
 		}

 		cuerpo.append("Fecha de emisión: ").append(fechaEmisionSegura).append("\n\n");
 		cuerpo.append("Gracias por su colaboración.\n\n");
 		cuerpo.append("Atentamente,\nBiblioteca INET");

 		return cuerpo.toString();
 	}


 	//Valida una dirección de correo con una expresión regular simple
 	private static boolean esCorreoValido(String email) {
 		if (email == null) {
 			return false;
 		}

 		String patron = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
 		boolean coincide = email.matches(patron);

 		if (coincide) {
 			return true;
 		} else {
 			return false;
 		}
 	}

 	//Separa una cadena con 1..N correos y devuelve solo los válidos
 	private static ArrayList<String> destinatariosValidos(String texto) {
 		ArrayList<String> listaValidos = new ArrayList<String>();

 		if (texto == null) {
 			return listaValidos;
 		}

 		String textoSinEspaciosExtremos = texto.trim();
 		if (textoSinEspaciosExtremos.isEmpty()) {
 			return listaValidos;
 		}

 		String[] posiblesCorreos = textoSinEspaciosExtremos.split("[,;\\s]+");

 		int i = 0;
 		while (i < posiblesCorreos.length) {
 			String candidato = posiblesCorreos[i];
 			boolean valido = esCorreoValido(candidato);

 			if (valido) {
 				listaValidos.add(candidato);
 			}

 			i = i + 1;
 		}

 		return listaValidos;
 	}
	
}
