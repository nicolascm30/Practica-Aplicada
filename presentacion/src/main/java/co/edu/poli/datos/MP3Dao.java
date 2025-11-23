package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;

import co.edu.poli.database.Cancion;
import co.edu.poli.database.Mp3;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MP3Dao {

	private final CancionDao cancionDao = new CancionDao();

	public MP3Dao() {
	}

	public void crearTablaMp3() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Mp3 (\r\n" + "  idMp3 SERIAL PRIMARY KEY,\r\n"
				+ "  idCancion INT NOT NULL UNIQUE,\r\n" // UNIQUE asegura relación 1:1 o 1:N si se tuviera
				+ "  formato VARCHAR(50) NOT NULL,\r\n" + "  tamanioMB DOUBLE PRECISION NOT NULL,\r\n"
				+ "  precio DOUBLE PRECISION NOT NULL,\r\n"
				+ "  FOREIGN KEY (idCancion) REFERENCES Cancion(id) ON DELETE CASCADE\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Mp3' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Mp3: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	public void crearMp3(Mp3 nuevo) {
		cancionDao.crearCancion(nuevo.getCancion());
		int idCancion = nuevo.getCancion().getId();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "INSERT INTO Mp3 (idCancion, formato, tamanioMB, precio) VALUES (?, ?, ?, ?)";
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				ps.setInt(1, idCancion);
				ps.setString(2, nuevo.getFormato());
				ps.setDouble(3, nuevo.getTamanioMB());
				ps.setDouble(4, nuevo.getPrecio());

				int affectedRows = ps.executeUpdate();

				if (affectedRows > 0) {
					rs = ps.getGeneratedKeys();
					if (rs.next()) {
						nuevo.setIdMp3(rs.getInt(1));
					}
					System.out.println("✅ MP3 creado (DB): ID " + nuevo.getIdMp3() + " para Cancion ID " + idCancion);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando Mp3: " + e.getMessage());

		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	public Mp3 buscarMp3(int idMp3) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Mp3 mp3 = null;

		String sql = "SELECT m.*, c.titulo, c.artista, c.duracionSegundos "
				+ "FROM Mp3 m JOIN Cancion c ON m.idCancion = c.id " + "WHERE m.idMp3 = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, idMp3);
				rs = ps.executeQuery();

				if (rs.next()) {
					Cancion cancion = new Cancion(rs.getInt("idCancion"), // ID de la tabla Cancion (id)
							rs.getString("titulo"), rs.getString("artista"), rs.getDouble("duracionSegundos"));
					mp3 = new Mp3(rs.getInt("idMp3"), rs.getString("formato"), rs.getDouble("tamanioMB"),
							rs.getDouble("precio"), cancion // Asociar la Cancion al Mp3
					);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Mp3: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return mp3;
	}

	public void verMp3() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT m.idMp3, m.precio, m.formato, m.tamanioMB, c.titulo, c.artista, c.duracionSegundos "
				+ "FROM Mp3 m JOIN Cancion c ON m.idCancion = c.id ORDER BY c.titulo";

		System.out.println("\n--- 💾 Lista de archivos MP3 Disponibles ---");
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					String output = String.format(
							"ID: %d | Título: %s | Artista: %s | Precio: $%,.2f | Formato: %s (%.2f MB)",
							rs.getInt("idMp3"), rs.getString("titulo"), rs.getString("artista"), rs.getDouble("precio"),
							rs.getString("formato"), rs.getDouble("tamanioMB"));
					System.out.println(output);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al listar Mp3: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}

	public void actualizarMp3(int idMp3, String nuevoTitulo, double nuevoTamanoMB, double nuevoPrecio) {
		Mp3 mp3Existente = buscarMp3(idMp3);

		if (mp3Existente == null) {
			System.out.println("❌ MP3 con ID " + idMp3 + " no encontrado. No se puede actualizar.");
			return;
		}

		Cancion cancionExistente = mp3Existente.getCancion();
		cancionDao.actualizarCancion(cancionExistente.getId(), nuevoTitulo, cancionExistente.getArtista(), // Mantenemos
																											// el
																											// artista
				cancionExistente.getDuracionSegundos() // Mantenemos la duración
		);

		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Mp3 SET tamanioMB = ?, precio = ? WHERE idMp3 = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, nuevoTamanoMB);
				ps.setDouble(2, nuevoPrecio);
				ps.setInt(3, idMp3);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ MP3 con ID " + idMp3 + " actualizado (tamanio y precio).");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando Mp3: " + e.getMessage());
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	public void eliminarMp3(int idMp3) {
		Mp3 mp3AEliminar = buscarMp3(idMp3);

		if (mp3AEliminar == null) {
			System.out.println("❌ MP3 con ID " + idMp3 + " no encontrado. No se puede eliminar.");
			return;
		}

		int idCancion = mp3AEliminar.getCancion().getId();

		cancionDao.eliminarCancion(idCancion);

		System.out.println("✅ MP3 con ID " + idMp3 + " y su Cancion base eliminados.");
	}

}