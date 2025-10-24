package co.edu.poli.datos;

import co.edu.poli.dataBase.Persona;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad base Persona. Contiene las operaciones CRUD para la tabla
 * 'Persona'.
 */
public class PersonaDao {

	public PersonaDao() {
		// La inicialización de listas se elimina al migrar a JDBC
	}

	// ---------------------------------------------------
	// 🏗️ Creación de la Tabla
	// ---------------------------------------------------

	public void crearTablaPersona() {
		Connection conn = null;
		Statement st = null;
		// Cedula como clave primaria
		String sql = "CREATE TABLE IF NOT EXISTS Persona (\r\n" + "  cedula INT PRIMARY KEY,\r\n"
				+ "  nombre VARCHAR(100) NOT NULL,\r\n" + "  correo VARCHAR(100) NOT NULL UNIQUE\r\n" // Correo único
				+ ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Persona' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Persona: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// ➕ Método CREAR (Create)
	// ---------------------------------------------------

	/**
	 * Crea un nuevo registro en la tabla Persona.
	 * 
	 * @param nueva El objeto Persona a guardar.
	 */
	public boolean crearPersona(Persona nueva) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO Persona (cedula, nombre, correo) VALUES (?, ?, ?)";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);

				ps.setInt(1, nueva.getCedula());
				ps.setString(2, nueva.getNombre());
				ps.setString(3, nueva.getCorreo());

				int affectedRows = ps.executeUpdate();

				if (affectedRows > 0) {
					System.out.println("✅ Persona creada (DB): Cédula " + nueva.getCedula());
					exito = true;
				}
			}
		} catch (SQLException e) {
			// Maneja el caso de que la cédula/correo ya exista (violación de clave
			// primaria/única)
			System.err.println("❌ Error insertando Persona: " + e.getMessage());
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return exito;
	}

	// ---------------------------------------------------
	// 🔍 Método BUSCAR (Read One)
	// ---------------------------------------------------

	/**
	 * Busca una Persona por su cédula.
	 * 
	 * @param cedula Cédula de la persona a buscar.
	 * @return El objeto Persona encontrado o null.
	 */
	public Persona buscarPersona(int cedula) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Persona persona = null;

		String sql = "SELECT cedula, nombre, correo FROM Persona WHERE cedula = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, cedula);
				rs = ps.executeQuery();

				if (rs.next()) {
					persona = new Persona(rs.getInt("cedula"), rs.getString("nombre"), rs.getString("correo"));
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Persona: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return persona;
	}

	// ---------------------------------------------------
	// 🔄 Método ACTUALIZAR (Update)
	// ---------------------------------------------------

	/**
	 * Actualiza el nombre y correo de una Persona por su cédula.
	 * 
	 * @param cedula      Cédula de la persona a actualizar.
	 * @param nuevoNombre Nuevo nombre.
	 * @param nuevoCorreo Nuevo correo.
	 * @return true si se actualizó, false en caso contrario.
	 */
	public boolean actualizarPersona(int cedula, String nuevoNombre, String nuevoCorreo) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Persona SET nombre = ?, correo = ? WHERE cedula = ?";
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
					System.out.println("✅ Persona con cédula " + cedula + " actualizada.");
					exito = true;
				} else {
					System.out.println("❌ Persona con cédula " + cedula + " no encontrada.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando Persona: " + e.getMessage());
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
	 * Elimina una Persona por su cédula.
	 * 
	 * @param cedula Cédula de la persona a eliminar.
	 * @return true si se eliminó, false en caso contrario.
	 */
	public boolean eliminarPersona(int cedula) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM Persona WHERE cedula = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, cedula);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Persona con cédula " + cedula + " eliminada.");
					exito = true;
				} else {
					System.out.println("❌ Persona con cédula " + cedula + " no encontrada.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando Persona: " + e.getMessage());
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
	 * Muestra todas las Personas en la consola.
	 */
	public void verPersonas() {
		System.out.println("\n--- 🧑 Lista de Personas ---");
		List<Persona> lista = obtenerTodasLasPersonas();
		if (lista.isEmpty()) {
			System.out.println("No hay personas registradas.");
			return;
		}
		for (Persona p : lista) {
			System.out.println(
					String.format("Cédula: %d | Nombre: %s | Correo: %s", p.getCedula(), p.getNombre(), p.getCorreo()));
		}
	}

	/**
	 * Obtiene todas las Personas de la base de datos.
	 * 
	 * @return Una lista de objetos Persona.
	 */
	public List<Persona> obtenerTodasLasPersonas() {
		List<Persona> personas = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT cedula, nombre, correo FROM Persona ORDER BY nombre";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					Persona p = new Persona(rs.getInt("cedula"), rs.getString("nombre"), rs.getString("correo"));
					personas.add(p);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al listar Personas: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
		return personas;
	}
}