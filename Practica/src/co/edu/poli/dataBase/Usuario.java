package co.edu.poli.dataBase;

import java.util.List;

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

	public static final List<Usuario> Usuario = List.of(
			new Usuario(1010, "Carlos Pérez", "carlos.perez@gmail.com", "pass1", "Cliente"),
			new Usuario(1011, "Laura Gómez", "laura.gomez@gmail.com", "pass2", "Cliente"),
			new Usuario(1012, "Andrés Rojas", "andres.rojas@hotmail.com", "pass3", "Cliente"),
			new Usuario(1013, "Valentina Torres", "valentina.torres@gmail.com", "pass4", "Cliente"),
			new Usuario(1014, "Santiago Ruiz", "santiago.ruiz@yahoo.com", "pass5", "Cliente"));
}
