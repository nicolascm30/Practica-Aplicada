package co.edu.poli.dataBase;

 public class Usuario extends Persona {
	private String nombreDeUsuario;
	private int amigos;
	private int edad;

	public Usuario() {
	}

	public boolean registrar() {
		return false;
	}

	public void recargarSaldo(double monto) {
	}

	public void resaltarRespuesta(Cancion cancion) {
	}

	public void realizarCompra(Mp3 mp3) {
	}

	public void solicitarReporte() {
	}

	public void crearPlaylist(Cancion cancion) {
	}

	// Getters y Setters
	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}

	public void setNombreDeUsuario(String nombreDeUsuario) {
		this.nombreDeUsuario = nombreDeUsuario;
	}

	public int getAmigos() {
		return amigos;
	}

	public void setAmigos(int amigos) {
		this.amigos = amigos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
}