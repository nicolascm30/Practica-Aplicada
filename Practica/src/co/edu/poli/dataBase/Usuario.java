package co.edu.poli.dataBase;


public class Usuario extends Persona {
	private String contrasena;
	private String rol;

	public Usuario() {
	}

	public Usuario(int cedula, String nombre, String correo, String contrasena, String rol) {
		super(cedula, nombre, correo);
		this.contrasena = contrasena;
		this.rol = rol;
	}
	
	public Usuario(int cedula, String nombre, String correo) {
		super(cedula, nombre, correo);
	}


	// FIX: Se añade getPassword() que ManegerSeguridad requiere
	public String getPassword() {
		return contrasena;
	}
	// FIN FIX

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [cedula=" + getCedula() + ", nombre=" + getNombre() + ", correo=" + getCorreo() + ", rol="
				+ rol + "]";
	}

	
}
