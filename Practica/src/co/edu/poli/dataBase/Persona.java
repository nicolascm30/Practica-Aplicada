package co.edu.poli.dataBase;

public class Persona {
	private int cedula;
	private String nombre;
	private String correo;

	public Persona() {
	}

	public boolean iniciarSesion() {
		// TODO: implementar lógica
		return false;
	}

	// Getters y Setters
	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
}