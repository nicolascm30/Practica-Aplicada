package co.edu.poli.datos;

import co.edu.poli.dataBase.Transaccion;
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
 * DAO para la entidad Transaccion. Contiene las operaciones CRUD para la tabla
 * 'Transaccion', gestionando las dos claves foráneas a Billetera.
 */
public class TransaccionDao {

	// 💡 Dependencia para obtener las Billeteras asociadas.
	private BilleteraDao billeteraDao;

	public TransaccionDao() {
		// Inicialización de dependencias (se asume BilleteraDao ya existe/está listo)
		this.billeteraDao = new BilleteraDao();
	}

	// ---------------------------------------------------
	// 🛠️ Mapeo de Entidad
	// ---------------------------------------------------

	/**
	 * Mapea un ResultSet a un objeto Transaccion, buscando las Billeteras de Origen
	 * y Destino.
	 * 
	 * @param rs El ResultSet con los datos de la Transaccion.
	 * @return El objeto Transaccion mapeado.
	 * @throws SQLException Si ocurre un error de SQL.
	 */
	private Transaccion mapearTransaccion(ResultSet rs) throws SQLException {
		int idTransaccion = rs.getInt("idTransaccion");
		int idBilleteraOrigen = rs.getInt("idBilleteraOrigen");
		int idBilleteraDestino = rs.getInt("idBilleteraDestino");

		// Buscar Billeteras por sus IDs (se asume que BilleteraDao puede hacerlo)
		Billetera origen = billeteraDao.buscarBilletera(idBilleteraOrigen);
		Billetera destino = billeteraDao.buscarBilletera(idBilleteraDestino);

		// Manejo de FK rotas (aunque en un DB con FKs activas no debería pasar)
		if (origen == null) {
			System.err.println("⚠️ Advertencia: Billetera Origen (ID " + idBilleteraOrigen + ") no encontrada.");
			origen = new Billetera(idBilleteraOrigen, null, 0.0, "ERROR_NO_ENCONTRADA");
		}
		if (destino == null) {
			System.err.println("⚠️ Advertencia: Billetera Destino (ID " + idBilleteraDestino + ") no encontrada.");
			destino = new Billetera(idBilleteraDestino, null, 0.0, "ERROR_NO_ENCONTRADA");
		}

		// Obtener la fecha de la DB y convertirla a String, ya que Transaccion.java usa
		// String para la fecha
		Date sqlDate = rs.getDate("fecha");
		String fechaString = (sqlDate != null) ? sqlDate.toString() : null;

		return new Transaccion(idTransaccion, origen, destino, rs.getDouble("monto"), fechaString);
	}

	// ---------------------------------------------------
	// 🏗️ Creación de la Tabla
	// ---------------------------------------------------

	public void crearTablaTransaccion() {
		Connection conn = null;
		Statement st = null;
		// La definición SQL de la tabla es correcta para PostgreSQL (SERIAL PRIMARY
		// KEY)
		String sql = "CREATE TABLE IF NOT EXISTS Transaccion (\r\n" + "  idTransaccion SERIAL PRIMARY KEY,\r\n"
				+ "  idBilleteraOrigen INT NOT NULL,\r\n" + "  idBilleteraDestino INT NOT NULL,\r\n"
				+ "  monto DOUBLE PRECISION NOT NULL,\r\n" + "  fecha DATE NOT NULL DEFAULT CURRENT_DATE,\r\n"
				+ "  FOREIGN KEY (idBilleteraOrigen) REFERENCES Billetera(idBilletera),\r\n"
				+ "  FOREIGN KEY (idBilleteraDestino) REFERENCES Billetera(idBilletera)\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Transaccion' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Transaccion: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// ➕ Método CREAR (Create)
	// ---------------------------------------------------

	/**
	 * Crea un nuevo registro de Transaccion en la base de datos. El campo 'fecha'
	 * se omite y se usa el DEFAULT (CURRENT_DATE) de la base de datos.
	 * 
	 * @param nueva El objeto Transaccion a guardar.
	 * @return El ID generado de la nueva transacción o -1 si falla.
	 */
	public int crearTransaccion(Transaccion nueva) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Se omiten los campos SERIAL y con DEFAULT (fecha)
		String sql = "INSERT INTO Transaccion (idBilleteraOrigen, idBilleteraDestino, monto) VALUES (?, ?, ?)";
		int idGenerado = -1;

		try {
			conn = ConexionDB.getConnection();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, nueva.getBilleteraOrigen().getIdBilletera());
			ps.setInt(2, nueva.getBilleteraDestino().getIdBilletera());
			ps.setDouble(3, nueva.getMonto());

			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					idGenerado = rs.getInt(1);
					nueva.setIdTransaccion(idGenerado); // Actualiza el objeto Java
					System.out
							.println("✅ Transacción creada (ID: " + idGenerado + ", Monto: " + nueva.getMonto() + ")");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando Transacción: " + e.getMessage());
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
	 * Busca una Transaccion por su ID.
	 * 
	 * @param idTransaccion ID de la transacción a buscar.
	 * @return El objeto Transaccion encontrado o null.
	 */
	public Transaccion buscarTransaccion(int idTransaccion) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Transaccion transaccion = null;

		String sql = "SELECT idTransaccion, idBilleteraOrigen, idBilleteraDestino, monto, fecha FROM Transaccion WHERE idTransaccion = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, idTransaccion);
				rs = ps.executeQuery();

				if (rs.next()) {
					transaccion = mapearTransaccion(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Transacción: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return transaccion;
	}

	// ---------------------------------------------------
	// 🔄 Método ACTUALIZAR (Update)
	// ---------------------------------------------------

	/**
	 * Actualiza el monto de una Transaccion por su ID. (Aunque normalmente el monto
	 * de una transacción no se actualiza).
	 * 
	 * @param id         ID de la transacción a actualizar.
	 * @param nuevoMonto Nuevo monto de la transacción.
	 * @return true si se actualizó, false en caso contrario.
	 */
	public boolean actualizarTransaccion(int id, double nuevoMonto) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Transaccion SET monto = ? WHERE idTransaccion = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, nuevoMonto);
				ps.setInt(2, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out
							.println("✅ Transacción con ID " + id + " actualizada (Nuevo Monto: " + nuevoMonto + ").");
					exito = true;
				} else {
					System.out.println("❌ Transacción con ID " + id + " no encontrada.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando Transacción: " + e.getMessage());
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
	 * Elimina una Transaccion por su ID.
	 * 
	 * @param id ID de la transacción a eliminar.
	 * @return true si se eliminó, false en caso contrario.
	 */
	public boolean eliminarTransaccion(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM Transaccion WHERE idTransaccion = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Transacción con ID " + id + " eliminada.");
					exito = true;
				} else {
					System.out.println("❌ Transacción con ID " + id + " no encontrada.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando Transacción: " + e.getMessage());
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
	 * Obtiene y muestra todas las Transacciones de la base de datos.
	 */
	public void verTransacciones() {
		System.out.println("\n--- 💸 Historial de Transacciones ---");
		List<Transaccion> lista = obtenerTodasLasTransacciones();
		if (lista.isEmpty()) {
			System.out.println("No hay transacciones registradas.");
			return;
		}
		for (Transaccion t : lista) {
			System.out.println(String.format("ID: %d | Origen: %d | Destino: %d | Monto: %.2f | Fecha: %s",
					t.getIdTransaccion(), t.getBilleteraOrigen().getIdBilletera(),
					t.getBilleteraDestino().getIdBilletera(), t.getMonto(), t.getFecha()));
		}
	}

	/**
	 * Obtiene todas las Transacciones de la base de datos.
	 * 
	 * @return Una lista de objetos Transaccion.
	 */
	public List<Transaccion> obtenerTodasLasTransacciones() {
		List<Transaccion> transacciones = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT idTransaccion, idBilleteraOrigen, idBilleteraDestino, monto, fecha FROM Transaccion ORDER BY idTransaccion DESC";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					transacciones.add(mapearTransaccion(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al listar Transacciones: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
		return transacciones;
	}
}