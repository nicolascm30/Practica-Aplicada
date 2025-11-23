package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.database.Cancion;
import co.edu.poli.database.Proveedor;
import co.edu.poli.database.Vinilo;

public class ViniloDao {

	public ViniloDao() {
	}

	private Vinilo mapearVinilo(ResultSet rs) throws SQLException {
		Proveedor proveedor = new Proveedor(rs.getInt("idProveedor"), rs.getString("nombreEmpresa"),
				rs.getString("contacto"), // Mapeado desde Proveedor.nombre AS contacto
				rs.getString("correo") // Mapeado desde Proveedor.correo
		);

		Cancion cancion = new Cancion(rs.getInt("idCancion"), rs.getString("titulo"), rs.getString("artista"),
				rs.getDouble("duracionSegundos"));

		return new Vinilo(rs.getInt("idVinilo"), cancion, // Objeto Cancion
				rs.getDouble("precio"), proveedor, rs.getString("estado"));
	}

	public void crearTablaVinilo() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Vinilo (\r\n" + "  idVinilo SERIAL PRIMARY KEY,\r\n"
				+ "  idCancion INT NOT NULL,\r\n" + "  idProveedor INT NOT NULL,\r\n"
				+ "  precio DOUBLE PRECISION NOT NULL,\r\n" + "  estado VARCHAR(50) NOT NULL,\r\n"
				+ "  FOREIGN KEY (idCancion) REFERENCES Cancion(id) ON DELETE CASCADE,\r\n" // Añadido ON DELETE CASCADE
				+ "  FOREIGN KEY (idProveedor) REFERENCES Proveedor(idProveedor) ON DELETE RESTRICT\r\n" // Añadido ON
																											// DELETE
																											// RESTRICT
				+ ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Vinilo' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Vinilo: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	public void crearVinilo(Vinilo nuevo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int idCancion = nuevo.getCancion().getId();
		int idProveedor = nuevo.getProveedor().getIdProveedor();

		String sql = "INSERT INTO Vinilo (idCancion, idProveedor, precio, estado) VALUES (?, ?, ?, ?)";

		try {
			conn = ConexionDB.getConnection();
			// Pedir las claves generadas para obtener el idVinilo
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, idCancion);
			ps.setInt(2, idProveedor);
			ps.setDouble(3, nuevo.getPrecio());
			ps.setString(4, nuevo.getEstado());

			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					nuevo.setIdVinilo(rs.getInt(1)); // Asignar el ID autogenerado al objeto
					System.out.println("✅ Vinilo creado y ID asignado: " + nuevo.getIdVinilo());
				}
			} else {
				System.err.println("❌ Error al crear el Vinilo.");
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando Vinilo (Verifique FKs: Cancion=" + idCancion + ", Proveedor="
					+ idProveedor + "): " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	public Vinilo buscarVinilo(int idVinilo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vinilo vinilo = null;

		// ✅ FIX 3: Se añade V.idCancion a la consulta SELECT
		String sql = "SELECT V.idVinilo, V.idCancion, V.precio, V.estado, "
				+ "C.titulo, C.artista, C.duracionSegundos, "
				+ "P.idProveedor, P.nombreEmpresa, P.nombre AS contacto, P.correo " + "FROM Vinilo V "
				+ "INNER JOIN Cancion C ON V.idCancion = C.id "
				+ "INNER JOIN Proveedor P ON V.idProveedor = P.idProveedor " + "WHERE V.idVinilo = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, idVinilo);
				rs = ps.executeQuery();

				if (rs.next()) {
					vinilo = mapearVinilo(rs);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Vinilo por ID: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return vinilo;
	}

	public boolean actualizarVinilo(int idVinilo, double nuevoPrecio, String nuevoEstado) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Vinilo SET precio = ?, estado = ? WHERE idVinilo = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, nuevoPrecio);
				ps.setString(2, nuevoEstado);
				ps.setInt(3, idVinilo);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Vinilo con ID " + idVinilo + " actualizado (Precio y Estado).");
					exito = true;
				} else {
					System.out.println("❌ Vinilo con ID " + idVinilo + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando Vinilo: " + e.getMessage());
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return exito;
	}

	public boolean eliminarVinilo(int idVinilo) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM Vinilo WHERE idVinilo = ?";
		boolean exito = false;

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, idVinilo);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Vinilo con ID " + idVinilo + " eliminado.");
					exito = true;
				} else {
					System.out.println("❌ Vinilo con ID " + idVinilo + " no encontrado.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando Vinilo: " + e.getMessage());
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return exito;
	}

	public List<Vinilo> obtenerTodosLosVinilos() {
		List<Vinilo> vinilos = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT V.idVinilo, V.idCancion, V.precio, V.estado, "
				+ "C.titulo, C.artista, C.duracionSegundos, "
				+ "P.idProveedor, P.nombreEmpresa, P.nombre AS contacto, P.correo " + "FROM Vinilo V "
				+ "INNER JOIN Cancion C ON V.idCancion = C.id "
				+ "INNER JOIN Proveedor P ON V.idProveedor = P.idProveedor " + "ORDER BY V.idVinilo";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					vinilos.add(mapearVinilo(rs));
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al listar Vinilos: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
		return vinilos;
	}

	public void verVinilos() {
		System.out.println("\n--- 💿 Lista de Vinilos ---");
		List<Vinilo> lista = obtenerTodosLosVinilos();
		if (lista.isEmpty()) {
			System.out.println("No hay vinilos registrados.");
			return;
		}
		for (Vinilo v : lista) {
			System.out.println(v);
		}
	}
}