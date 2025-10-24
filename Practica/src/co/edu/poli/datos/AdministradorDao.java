package co.edu.poli.datos;

import co.edu.poli.presentacion.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Administrador, migrado a JDBC para persistencia.
 */
public class AdministradorDao {

	// Lista de simulación (Administrador es de capa Presentación, pero simulamos un
	// DAO)
	private List<Administrador> administradores = new ArrayList<>();
	private static int nextId = 1; // Solo se mantiene para la inicialización simulada si no hay DB.

	public AdministradorDao() {
		// Inicializamos con un dato de prueba
		Administrador admin = new Administrador();
		admin.setIdAdmin(nextId++);
		admin.setCedula(90001);
		admin.setNombre("Neo Anderson");
		admin.setCorreo("neo.admin@poli.edu.co");
		admin.setRango(1);
		administradores.add(admin);
	}

	/**
	 * Crea la tabla 'administrador' si no existe.
	 */
	public void crearTablaAdministrador() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				stmt = conn.createStatement();

				String sql = "CREATE TABLE IF NOT EXISTS administrador (" + "idAdmin SERIAL PRIMARY KEY,"
						+ "cedula INT UNIQUE NOT NULL," + "nombre VARCHAR(100) NOT NULL,"
						+ "correo VARCHAR(100) UNIQUE NOT NULL," + "rango INT NOT NULL" + ");";

				stmt.execute(sql);
				System.out.println("✅ Tabla 'administrador' creada/verificada.");
			}
		} catch (SQLException e) {
			System.err.println("❌ Error creando tabla administrador: " + e.getMessage());
		} finally {
			// FIX: Uso de cierre individual para recursos
			ConexionDB.close(stmt);
			ConexionDB.close(conn);
		}
	}

	/**
	 * Inserta un nuevo administrador en la base de datos.
	 * 
	 * @param nuevo El objeto Administrador a guardar.
	 */
	public void crearAdministrador(Administrador nuevo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "INSERT INTO administrador (cedula, nombre, correo, rango) VALUES (?, ?, ?, ?)";
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				ps.setInt(1, nuevo.getCedula());
				ps.setString(2, nuevo.getNombre());
				ps.setString(3, nuevo.getCorreo());
				ps.setInt(4, nuevo.getRango());

				int affectedRows = ps.executeUpdate();

				if (affectedRows > 0) {
					rs = ps.getGeneratedKeys();
					if (rs.next()) {
						nuevo.setIdAdmin(rs.getInt(1));
					}
					System.out.println("✅ Administrador (DB): " + nuevo.getNombre() + " insertado correctamente. ID: "
							+ nuevo.getIdAdmin());
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando administrador: " + e.getMessage());
		} finally {
			// FIX: Uso de cierre individual para recursos
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	/**
	 * Elimina un administrador por su ID.
	 * 
	 * @param id ID del Administrador a eliminar.
	 */
	public void eliminarAdministrador(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM administrador WHERE idAdmin = ?";
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Administrador con ID " + id + " eliminado.");
				} else {
					System.out.println("❌ Administrador con ID " + id + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando administrador: " + e.getMessage());
		} finally {
			// FIX: Uso de cierre individual para recursos
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	/**
	 * Lista todos los administradores de la base de datos. Implementación necesaria
	 * para ManegerReportes.
	 */
	public void verAdministrador() {
		Connection conn = null;
		Statement stmt = null; // Mantenemos Statement, ya que no hay parámetros
		ResultSet rs = null;
		String sql = "SELECT idAdmin, nombre, rango FROM administrador ORDER BY idAdmin";

		System.out.println("📊 Lista de Administradores (DB):");
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);

				boolean found = false;
				while (rs.next()) {
					found = true;
					int id = rs.getInt("idAdmin");
					String n = rs.getString("nombre");
					int r = rs.getInt("rango");

					System.out.println("  - ID: " + id + ", Nombre: " + n + ", Rango: " + r);
				}

				if (!found) {
					System.out.println("No hay administradores registrados.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error listando administradores: " + e.getMessage());
		} finally {
			// FIX: Uso de cierre individual para recursos. Esto resuelve el error
			// reportado.
			ConexionDB.close(rs);
			ConexionDB.close(stmt);
			ConexionDB.close(conn);
		}
	}

	/**
	 * Actualiza el nombre de un administrador por su ID.
	 * 
	 * @param id          ID del Administrador a actualizar.
	 * @param nuevoNombre Nuevo nombre del administrador.
	 */
	public void actualizarAdministrador(int id, String nuevoNombre) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE administrador SET nombre = ? WHERE idAdmin = ?";
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);

				ps.setString(1, nuevoNombre);
				ps.setInt(2, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Administrador con ID " + id + " actualizado a nombre: " + nuevoNombre);
				} else {
					System.out.println("❌ Administrador con ID " + id + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando administrador: " + e.getMessage());
		} finally {
			// FIX: Uso de cierre individual para recursos
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	/**
	 * Busca un Administrador por su ID.
	 * 
	 * @param id ID del administrador a buscar.
	 * @return El objeto Administrador encontrado o null.
	 */
	public Administrador buscarAdministrador(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT idAdmin, cedula, nombre, correo, rango FROM administrador WHERE idAdmin = ?";
		Administrador administrador = null;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				rs = ps.executeQuery();

				if (rs.next()) {
					Administrador a = new Administrador();
					a.setIdAdmin(rs.getInt("idAdmin"));
					a.setCedula(rs.getInt("cedula"));
					a.setNombre(rs.getString("nombre"));
					a.setCorreo(rs.getString("correo"));
					a.setRango(rs.getInt("rango"));
					administrador = a;
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando administrador: " + e.getMessage());
		} finally {
			// FIX: Uso de cierre individual para recursos
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return administrador;
	}
}