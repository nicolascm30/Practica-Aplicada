package co.edu.poli.datos;

import co.edu.poli.dataBase.Proveedor;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Proveedor. Contiene las operaciones CRUD
 * para la tabla 'Proveedor', asumiendo que es independiente de la tabla 'Persona'
 * para simplificar el modelo de persistencia del Proveedor.
 */
public class ProveedorDao {

	public ProveedorDao() {
		// Se elimina la lista en memoria al migrar a JDBC
	}
	
	// ---------------------------------------------------
	// 🛠️ Mapeo de Entidad
	// ---------------------------------------------------

	/**
	 * Mapea un ResultSet a un objeto Proveedor.
	 */
	private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
		// Proveedor(idProveedor, nombreEmpresa, contacto/nombrePersona, correo)
		Proveedor p = new Proveedor(
				rs.getInt("idProveedor"), 
				rs.getString("nombreEmpresa"), 
				rs.getString("contacto"), // Se mapea a Persona.nombre
				rs.getString("correo")
		);
		// Nota: Proveedor.java usa un constructor que pasa 'contacto' a Persona.nombre
		// y usa un valor de cédula = 0 por defecto.
		return p;
	}


	// ---------------------------------------------------
	// 🏗️ Creación de la Tabla
	// ---------------------------------------------------

	public void crearTablaProveedor() {
		Connection conn = null;
		Statement st = null;
		// idProveedor como clave primaria autoincremental
		String sql = "CREATE TABLE IF NOT EXISTS Proveedor (\r\n"
				+ "  idProveedor SERIAL PRIMARY KEY,\r\n"
				+ "  nombreEmpresa VARCHAR(255) NOT NULL,\r\n"
				+ "  contacto VARCHAR(255) NOT NULL,\r\n"
				+ "  correo VARCHAR(255) UNIQUE NOT NULL\r\n" // Correo único
				+ ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Proveedor' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Proveedor: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// ➕ Método CREAR (Create)
	// ---------------------------------------------------

	/**
	 * Crea un nuevo registro en la tabla Proveedor.
	 * @param nuevo El objeto Proveedor a guardar.
	 * @return El ID generado del nuevo proveedor o -1 si falla.
	 */
	public int crearProveedor(Proveedor nuevo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO Proveedor (nombreEmpresa, contacto, correo) VALUES (?, ?, ?)";
		int idGenerado = -1;

		try {
			conn = ConexionDB.getConnection();
			// El segundo parámetro indica que queremos recuperar la clave generada
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, nuevo.getNombreEmpresa());
			ps.setString(2, nuevo.getNombre()); // Proveedor.nombreEmpresa -> contacto/Persona.nombre
			ps.setString(3, nuevo.getCorreo()); // Proveedor.correo -> Persona.correo

			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					idGenerado = rs.getInt(1); // Obtiene el valor de la primera columna generada (idProveedor)
					nuevo.setIdProveedor(idGenerado); // Actualiza el objeto Java
					System.out.println("✅ Proveedor creado (ID: " + idGenerado + ", Empresa: " + nuevo.getNombreEmpresa() + ")");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando Proveedor: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return idGenerado;
	}
	
	// ---------------------------------------------------
	// 🔍 Método BUSCAR (Read One)
	// ---------------------------------------------------

	/**
	 * Busca un Proveedor por su ID.
	 * @param idProveedor ID del proveedor a buscar.
	 * @return El objeto Proveedor encontrado o null.
	 */
	public Proveedor buscarProveedor(int idProveedor) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Proveedor proveedor = null;

		String sql = "SELECT idProveedor, nombreEmpresa, contacto, correo FROM Proveedor WHERE idProveedor = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, idProveedor);
				rs = ps.executeQuery();

				if (rs.next()) {
					proveedor = mapearProveedor(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Proveedor: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return proveedor;
	}

	// ---------------------------------------------------
	// 🔄 Método ACTUALIZAR (Update)
	// ---------------------------------------------------

	/**
	 * Actualiza el nombre de la empresa, el contacto y el correo de un Proveedor por su ID.
	 * @param id ID del proveedor a actualizar.
	 * @param nuevoNombreEmpresa Nuevo nombre de la empresa.
	 * @param nuevoContacto Nuevo nombre de contacto (mapeado a Persona.nombre).
	 * @param nuevoCorreo Nuevo correo.
	 * @return true si se actualizó, false en caso contrario.
	 */
	public boolean actualizarProveedor(int id, String nuevoNombreEmpresa, String nuevoContacto, String nuevoCorreo) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Proveedor SET nombreEmpresa = ?, contacto = ?, correo = ? WHERE idProveedor = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, nuevoNombreEmpresa);
				ps.setString(2, nuevoContacto);
				ps.setString(3, nuevoCorreo);
				ps.setInt(4, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Proveedor con ID " + id + " actualizado (Empresa: " + nuevoNombreEmpresa + ").");
					exito = true;
				} else {
					System.out.println("❌ Proveedor con ID " + id + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando Proveedor: " + e.getMessage());
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
	 * Elimina un Proveedor por su ID.
	 * @param id ID del proveedor a eliminar.
	 * @return true si se eliminó, false en caso contrario.
	 */
	public boolean eliminarProveedor(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM Proveedor WHERE idProveedor = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Proveedor con ID " + id + " eliminado.");
					exito = true;
				} else {
					System.out.println("❌ Proveedor con ID " + id + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando Proveedor: " + e.getMessage());
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
	 * Obtiene y muestra todos los Proveedores de la base de datos. (Método requerido por ManegerReportes).
	 */
    public void verProveedores() {
        System.out.println("\n--- 🏭 Lista de Proveedores ---");
        List<Proveedor> lista = obtenerTodosLosProveedores();
        if (lista.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        for (Proveedor p : lista) {
            System.out.println(String.format("ID: %d | Empresa: %s | Contacto: %s | Correo: %s",
                p.getIdProveedor(), p.getNombreEmpresa(), p.getNombre(), p.getCorreo()));
        }
    }

    /**
	 * Obtiene todas los Proveedores de la base de datos.
	 * @return Una lista de objetos Proveedor.
	 */
	public List<Proveedor> obtenerTodosLosProveedores() {
		List<Proveedor> proveedores = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT idProveedor, nombreEmpresa, contacto, correo FROM Proveedor ORDER BY nombreEmpresa";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					proveedores.add(mapearProveedor(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al listar Proveedores: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
		return proveedores;
	}
}