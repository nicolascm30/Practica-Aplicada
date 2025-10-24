package co.edu.poli.datos;

import co.edu.poli.dataBase.Recarga;
import co.edu.poli.dataBase.Billetera;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Para manejar la fecha de SQL
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Recarga. Contiene las operaciones CRUD para la tabla
 * 'Recarga', incluyendo la gestión de la clave foránea a Billetera.
 */
public class RecargaDao {

	// 💡 Dependencia para obtener la Billetera asociada a una Recarga.
	// Se asume que BilleteraDao ya existe o se creará.
	private BilleteraDao billeteraDao;

	public RecargaDao() {
		// Inicialización de dependencias (sustituir por inyección de dependencias en un
		// entorno real)
		this.billeteraDao = new BilleteraDao();
	}

	// ---------------------------------------------------
	// 🛠️ Mapeo de Entidad
	// ---------------------------------------------------

	/**
	 * Mapea un ResultSet a un objeto Recarga, buscando la Billetera asociada.
	 * 
	 * @param rs El ResultSet con los datos de la Recarga.
	 * @return El objeto Recarga mapeado.
	 * @throws SQLException Si ocurre un error de SQL.
	 */
	private Recarga mapearRecarga(ResultSet rs) throws SQLException {
		int idRecarga = rs.getInt("idRecarga");
		int idBilletera = rs.getInt("idBilletera");
		Billetera billetera = billeteraDao.buscarBilletera(idBilletera);

		// Si no se encuentra la billetera (FK rota), se usa un placeholder para evitar
		// un NullPointerException
		if (billetera == null) {
			System.err.println(
					"⚠️ Advertencia: Billetera con ID " + idBilletera + " no encontrada para la recarga " + idRecarga);
			// Se crea una billetera placeholder solo con el ID y un objeto Usuario nulo.
			billetera = new Billetera(idBilletera, null, 0.0, "ERROR_NO_ENCONTRADA");
		}

		// Obtener la fecha de la DB y convertirla a String, ya que Recarga.java usa
		// String para la fecha
		Date sqlDate = rs.getDate("fecha");
		String fechaString = (sqlDate != null) ? sqlDate.toString() : null;

		return new Recarga(idRecarga, billetera, rs.getDouble("monto"), fechaString);
	}

	// ---------------------------------------------------
	// 🏗️ Creación de la Tabla
	// ---------------------------------------------------

	public void crearTablaRecarga() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Recarga (\r\n" + "  idRecarga SERIAL PRIMARY KEY,\r\n"
				+ "  idBilletera INT NOT NULL,\r\n" + "  monto DOUBLE PRECISION NOT NULL,\r\n"
				+ "  fecha DATE NOT NULL DEFAULT CURRENT_DATE,\r\n"
				+ "  FOREIGN KEY (idBilletera) REFERENCES Billetera(idBilletera)\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Recarga' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Recarga: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// ➕ Método CREAR (Create)
	// ---------------------------------------------------

	/**
	 * Crea un nuevo registro de Recarga en la base de datos. El campo 'fecha' se
	 * omite y se usa el DEFAULT (CURRENT_DATE) de la base de datos.
	 * 
	 * @param nueva El objeto Recarga a guardar.
	 * @return El ID generado de la nueva recarga o -1 si falla.
	 */
	public int crearRecarga(Recarga nueva) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Se omiten los campos SERIAL y con DEFAULT (fecha)
		String sql = "INSERT INTO Recarga (idBilletera, monto) VALUES (?, ?)";
		int idGenerado = -1;

		try {
			conn = ConexionDB.getConnection();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, nueva.getBilletera().getIdBilletera());
			ps.setDouble(2, nueva.getMonto());

			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					idGenerado = rs.getInt(1);
					nueva.setIdRecarga(idGenerado); // Actualiza el objeto Java
					System.out.println("✅ Recarga creada (ID: " + idGenerado + ", Monto: " + nueva.getMonto() + ")");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando Recarga: " + e.getMessage());
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
	 * Busca una Recarga por su ID.
	 * 
	 * @param idRecarga ID de la recarga a buscar.
	 * @return El objeto Recarga encontrado o null.
	 */
	public Recarga buscarRecarga(int idRecarga) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Recarga recarga = null;

		String sql = "SELECT idRecarga, idBilletera, monto, fecha FROM Recarga WHERE idRecarga = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, idRecarga);
				rs = ps.executeQuery();

				if (rs.next()) {
					recarga = mapearRecarga(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Recarga: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return recarga;
	}

	// ---------------------------------------------------
	// 🔄 Método ACTUALIZAR (Update)
	// ---------------------------------------------------

	/**
	 * Actualiza el monto de una recarga por su ID.
	 * 
	 * @param id         ID de la recarga a actualizar.
	 * @param nuevoMonto Nuevo monto de la recarga.
	 * @return true si se actualizó, false en caso contrario.
	 */
	public boolean actualizarRecarga(int id, double nuevoMonto) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Recarga SET monto = ? WHERE idRecarga = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, nuevoMonto);
				ps.setInt(2, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Recarga con ID " + id + " actualizada (Monto: " + nuevoMonto + ").");
					exito = true;
				} else {
					System.out.println("❌ Recarga con ID " + id + " no encontrada.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando Recarga: " + e.getMessage());
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
	 * Elimina una Recarga por su ID.
	 * 
	 * @param id ID de la recarga a eliminar.
	 * @return true si se eliminó, false en caso contrario.
	 */
	public boolean eliminarRecarga(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM Recarga WHERE idRecarga = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Recarga con ID " + id + " eliminada.");
					exito = true;
				} else {
					System.out.println("❌ Recarga con ID " + id + " no encontrada.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando Recarga: " + e.getMessage());
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
	 * Obtiene y muestra todas las Recargas de la base de datos.
	 */
	public void verRecargas() {
		System.out.println("\n--- 💰 Historial de Recargas ---");
		List<Recarga> lista = obtenerTodasLasRecargas();
		if (lista.isEmpty()) {
			System.out.println("No hay recargas registradas.");
			return;
		}
		for (Recarga r : lista) {
			System.out.println(String.format("ID: %d | Billetera: %d | Monto: %.2f | Fecha: %s", r.getIdRecarga(),
					r.getBilletera().getIdBilletera(), r.getMonto(), r.getFecha()));
		}
	}

	/**
	 * Obtiene todas las Recargas de la base de datos.
	 * 
	 * @return Una lista de objetos Recarga.
	 */
	public List<Recarga> obtenerTodasLasRecargas() {
		List<Recarga> recargas = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT idRecarga, idBilletera, monto, fecha FROM Recarga ORDER BY idRecarga DESC";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					recargas.add(mapearRecarga(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al listar Recargas: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
		return recargas;
	}
}