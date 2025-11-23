package co.edu.poli.negocio;

import co.edu.poli.datos.UsuarioDao;
import co.edu.poli.database.Usuario;
import co.edu.poli.datos.AdministradorDao;
import co.edu.poli.presentacion.Administrador;

public class ManegerSeguridad {

	private final UsuarioDao usuarioDao;
	private final AdministradorDao administradorDao;

	public ManegerSeguridad() {
		// Inicializamos los DAOs para acceder a la base de datos
		this.usuarioDao = new UsuarioDao();
		this.administradorDao = new AdministradorDao();
	}

	/**
	 * Intenta iniciar sesión buscando el usuario por cédula y verificando la
	 * contraseña.
	 * 
	 * @param identificacion La cédula del usuario.
	 * @param contrasena     La contraseña a verificar.
	 * @return El objeto Usuario si las credenciales son correctas, o null si falla.
	 */
	public Usuario loginUsuario(int identificacion, String contrasena) {
		// 1. Buscar al usuario por cédula
		Usuario usuario = usuarioDao.buscarUsuario(identificacion);

		if (usuario != null) {
			// 2. Verificar si la contraseña coincide
			// Se usa getPassword() que devuelve el campo 'contrasena' de Usuario.java
			if (usuario.getPassword().equals(contrasena)) {
				System.out.println("✅ Login exitoso: Usuario " + usuario.getNombre() + " (" + usuario.getRol() + ")");
				return usuario;
			}
		}
		return null; // Credenciales incorrectas o usuario no existe
	}

	/**
	 * Intenta iniciar sesión buscando el administrador por ID (y verificando la
	 * cédula). * @param idAdmin El ID del administrador.
	 * 
	 * @param cedula La cédula del administrador.
	 * @return El objeto Administrador si las credenciales son correctas, o null si
	 *         falla.
	 */
	public Administrador loginAdministrador(int idAdmin, int cedula) {
		// 1. Buscar al administrador por ID
		Administrador admin = administradorDao.buscarAdministrador(idAdmin);

		if (admin != null) {
			// 2. Verificar la cédula (por simplicidad, como si fuera la segunda credencial)
			if (admin.getCedula() == cedula) {
				System.out.println(
						"✅ Login exitoso: Administrador " + admin.getNombre() + " (Rango: " + admin.getRango() + ")");
				return admin;
			}
		}
		return null; // Credenciales incorrectas o administrador no existe
	}
}