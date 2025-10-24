package co.edu.poli.datos;

import co.edu.poli.dataBase.Usuario;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Usuario. Implementa las operaciones CRUD con JDBC. Se
 * asume que Usuario extiende Persona, heredando cedula, nombre y correo.
 */
public class UsuarioDao {

	public UsuarioDao() {
	}

	// ---------------------------------------------------
	// 🛠️ Mapeo de Entidad
	// ---------------------------------------------------

	/**
	 * Mapea un ResultSet a un objeto Usuario.
	 * 
	 * @param rs El ResultSet con los datos del Usuario.
	 * @return El objeto Usuario mapeado.
	 * @throws SQLException Si ocurre un error de SQL.
	 */
	private Usuario mapearUsuario(ResultSet rs) throws SQLException {
		return new Usuario(rs.getInt("cedula"), rs.getString("nombre"), rs.getString("correo"),
				rs.getString("contrasena"), rs.getString("rol"));
	}

	// ---------------------------------------------------
	// 🏗️ Creación de la Tabla
	// ---------------------------------------------------

	public void crearTablaUsuario() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Usuario (\r\n" + "  cedula INT PRIMARY KEY,\r\n"
				+ "  nombre VARCHAR(255) NOT NULL,\r\n" + "  correo VARCHAR(255) UNIQUE NOT NULL,\r\n"
				+ "  contrasena VARCHAR(255) NOT NULL,\r\n" + "  rol VARCHAR(50) NOT NULL\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Usuario' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Usuario: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// ➕ Método CREAR (Create)
	// ---------------------------------------------------

	/**
	 * Crea un nuevo registro de Usuario en la base de datos.
	 * 
	 * @param nuevo El objeto Usuario a guardar.
	 */
	public void crearUsuario(Usuario nuevo) {
		Connection conn = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO Usuario (cedula, nombre, correo, contrasena, rol) VALUES (?, ?, ?, ?, ?)";

		try {
			conn = ConexionDB.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, nuevo.getCedula());
			ps.setString(2, nuevo.getNombre());
			ps.setString(3, nuevo.getCorreo());
			ps.setString(4, nuevo.getPassword()); // Usar getPassword() para obtener la contrasena
			ps.setString(5, nuevo.getRol());

			ps.executeUpdate();
			System.out
					.println("✅ Usuario creado (Cédula: " + nuevo.getCedula() + ", Nombre: " + nuevo.getNombre() + ")");
		} catch (SQLException e) {
			// Manejo de error de clave duplicada (ej. cédula o correo)
			if (e.getSQLState().startsWith("23")) { // Código de violación de restricción de integridad
				System.err.println("❌ Error: La cédula o el correo ya están registrados. " + e.getMessage());
			} else {
				System.err.println("❌ Error insertando Usuario: " + e.getMessage());
			}
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// 🔍 Métodos BUSCAR (Read One)
	// ---------------------------------------------------

	/**
	 * Busca un Usuario por su Cédula.
	 * 
	 * @param cedula Cédula del usuario a buscar.
	 * @return El objeto Usuario encontrado o null.
	 */
	public Usuario buscarUsuario(int cedula) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Usuario usuario = null;

		String sql = "SELECT cedula, nombre, correo, contrasena, rol FROM Usuario WHERE cedula = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, cedula);
				rs = ps.executeQuery();

				if (rs.next()) {
					usuario = mapearUsuario(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Usuario por cédula: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return usuario;
	}

	/**
	 * Busca un usuario por su correo. Necesario para el Manager de Seguridad.
	 * 
	 * @param correo Correo del usuario a buscar.
	 * @return Usuario encontrado o null si no existe.
	 */
	public Usuario buscarUsuarioPorCorreo(String correo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Usuario usuario = null;

		String sql = "SELECT cedula, nombre, correo, contrasena, rol FROM Usuario WHERE correo = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, correo);
				rs = ps.executeQuery();

				if (rs.next()) {
					usuario = mapearUsuario(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Usuario por correo: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return usuario;
	}

	// ---------------------------------------------------
	// 🔄 Método ACTUALIZAR (Update)
	// ---------------------------------------------------

	/**
	 * Actualiza el nombre y/o correo de un usuario.
	 * 
	 * @param cedula      Cédula del usuario a actualizar.
	 * @param nuevoNombre Nuevo nombre.
	 * @param nuevoCorreo Nuevo correo.
	 * @return true si se actualizó, false en caso contrario.
	 */
	public boolean actualizarUsuario(int cedula, String nuevoNombre, String nuevoCorreo) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Usuario SET nombre = ?, correo = ? WHERE cedula = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, nuevoNombre);
				ps.setString(2, nuevoCorreo);
				ps.setInt(3, cedula);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Usuario con cédula " + cedula + " actualizado.");
					exito = true;
				} else {
					System.out.println("❌ Usuario con cédula " + cedula + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			// Manejo de error de correo duplicado
			if (e.getSQLState().startsWith("23")) {
				System.err.println("❌ Error: El nuevo correo ya está registrado para otro usuario. " + e.getMessage());
			} else {
				System.err.println("❌ Error actualizando Usuario: " + e.getMessage());
			}
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return exito;
	}

	// ---------------------------------------------------
	// 🗑️ Método ELIMINAR (Delete)
	// ---------------------------------------------------

	/**
	 * Elimina un Usuario por su Cédula.
	 * 
	 * @param cedula Cédula del usuario a eliminar.
	 * @return true si se eliminó, false en caso contrario.
	 */
	public boolean eliminarUsuario(int cedula) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM Usuario WHERE cedula = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, cedula);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Usuario con cédula " + cedula + " eliminado.");
					exito = true;
				} else {
					System.out.println("❌ Usuario con cédula " + cedula + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando Usuario: " + e.getMessage());
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return exito;
	}

	// ---------------------------------------------------
	// 📜 Método VER TODOS (Read All)
	// ---------------------------------------------------

	/**
	 * Obtiene y muestra todos los Usuarios de la base de datos.
	 */
	public void verUsuarios() {
		System.out.println("\n--- 🧑‍💻 Lista de Usuarios ---");
		List<Usuario> lista = obtenerTodosLosUsuarios();
		if (lista.isEmpty()) {
			System.out.println("No hay usuarios registrados.");
			return;
		}
		for (Usuario u : lista) {
			// Se imprime el toString de Usuario que muestra rol, pero no la contraseña
			System.out.println(u);
		}
	}

	/**
	 * Obtiene todos los Usuarios de la base de datos.
	 * 
	 * @return Una lista de objetos Usuario.
	 */
	public List<Usuario> obtenerTodosLosUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT cedula, nombre, correo, contrasena, rol FROM Usuario ORDER BY cedula";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					usuarios.add(mapearUsuario(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al listar Usuarios: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
		return usuarios;
	}
}