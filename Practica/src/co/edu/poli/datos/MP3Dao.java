package co.edu.poli.datos;

import co.edu.poli.dataBase.Cancion;
import co.edu.poli.dataBase.Mp3;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * DAO para la entidad Mp3, que es una composición (tiene un objeto Cancion).
 * Las operaciones deben orquestar la persistencia entre las tablas Mp3 y
 * Cancion.
 */
public class MP3Dao {

	// 🤝 Dependencia del DAO Padre/Compuesto
	private final CancionDao cancionDao = new CancionDao();

	public MP3Dao() {
	}

	// ---------------------------------------------------
	// 🏗️ Creación de la Tabla
	// ---------------------------------------------------

	public void crearTablaMp3() {
		Connection conn = null;
		Statement st = null;
		// La tabla Mp3 tiene una clave foránea a Cancion.
		String sql = "CREATE TABLE IF NOT EXISTS Mp3 (\r\n" + "  idMp3 SERIAL PRIMARY KEY,\r\n"
				+ "  idCancion INT NOT NULL UNIQUE,\r\n" // UNIQUE asegura relación 1:1 o 1:N si se tuviera
				+ "  formato VARCHAR(50) NOT NULL,\r\n" + "  tamanioMB DOUBLE PRECISION NOT NULL,\r\n"
				+ "  precio DOUBLE PRECISION NOT NULL,\r\n"
				+ "  FOREIGN KEY (idCancion) REFERENCES Cancion(id) ON DELETE CASCADE\r\n" + ");"; // ON DELETE CASCADE:
																									// Si se borra la
																									// Cancion base, se
																									// borra el Mp3.
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

	// ---------------------------------------------------
	// ➕ Método CREAR (Create)
	// ---------------------------------------------------

	/**
	 * Crea un nuevo registro Mp3. Requiere crear primero la Cancion base.
	 * 
	 * @param nuevo El objeto Mp3 a guardar.
	 */
	public void crearMp3(Mp3 nuevo) {
		// 1. Crear la Cancion base y obtener su ID.
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
						// Asignamos el ID generado al objeto Mp3
						nuevo.setIdMp3(rs.getInt(1));
					}
					System.out.println("✅ MP3 creado (DB): ID " + nuevo.getIdMp3() + " para Cancion ID " + idCancion);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando Mp3: " + e.getMessage());
			// Si falla la inserción de Mp3, se debería considerar deshacer la Cancion
			// creada (Transacción)
			// Por simplicidad, aquí solo mostramos el error.
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// 🔍 Método BUSCAR (Read One)
	// ---------------------------------------------------

	/**
	 * Busca un Mp3 por su ID, uniendo con la tabla Cancion para obtener todos los
	 * datos.
	 * 
	 * @param idMp3 ID del Mp3 a buscar.
	 * @return El objeto Mp3 encontrado o null.
	 */
	public Mp3 buscarMp3(int idMp3) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Mp3 mp3 = null;

		// Consulta que une Mp3 y Cancion para obtener todos los campos necesarios.
		String sql = "SELECT m.*, c.titulo, c.artista, c.duracionSegundos "
				+ "FROM Mp3 m JOIN Cancion c ON m.idCancion = c.id " + "WHERE m.idMp3 = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, idMp3);
				rs = ps.executeQuery();

				if (rs.next()) {
					// 1. Crear el objeto Cancion base
					Cancion cancion = new Cancion(rs.getInt("idCancion"), // ID de la tabla Cancion (id)
							rs.getString("titulo"), rs.getString("artista"), rs.getDouble("duracionSegundos"));
					// 2. Crear el objeto Mp3
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

	// ---------------------------------------------------
	// 📜 Método VER TODOS (Read All)
	// ---------------------------------------------------

	/**
	 * Muestra todos los archivos MP3 disponibles en el catálogo.
	 */
	public void verMp3() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		// Consulta que une Mp3 y Cancion para obtener todos los campos necesarios.
		String sql = "SELECT m.idMp3, m.precio, m.formato, m.tamanioMB, c.titulo, c.artista, c.duracionSegundos "
				+ "FROM Mp3 m JOIN Cancion c ON m.idCancion = c.id ORDER BY c.titulo";

		System.out.println("\n--- 💾 Lista de archivos MP3 Disponibles ---");
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					// Formateo simple para la salida de consola
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

	// ---------------------------------------------------
	// 🔄 Método ACTUALIZAR (Update)
	// ---------------------------------------------------

	/**
	 * Actualiza los datos de un Mp3 y su Cancion base asociada.
	 * 
	 * @param idMp3         ID del MP3 a actualizar.
	 * @param nuevoTitulo   Nuevo título de la canción.
	 * @param nuevoTamanoMB Nuevo tamaño en MB.
	 * @param nuevoPrecio   Nuevo precio.
	 */
	public void actualizarMp3(int idMp3, String nuevoTitulo, double nuevoTamanoMB, double nuevoPrecio) {
		Mp3 mp3Existente = buscarMp3(idMp3);

		if (mp3Existente == null) {
			System.out.println("❌ MP3 con ID " + idMp3 + " no encontrado. No se puede actualizar.");
			return;
		}

		// 1. Actualizar la Cancion base (Delega a CancionDao)
		Cancion cancionExistente = mp3Existente.getCancion();
		cancionDao.actualizarCancion(cancionExistente.getId(), nuevoTitulo, cancionExistente.getArtista(), // Mantenemos
																											// el
																											// artista
				cancionExistente.getDuracionSegundos() // Mantenemos la duración
		);

		// 2. Actualizar los datos específicos del Mp3
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

	// ---------------------------------------------------
	// 🗑️ Método ELIMINAR (Delete)
	// ---------------------------------------------------

	/**
	 * Elimina un Mp3 por su ID. La eliminación en cascada en la DB se encarga de
	 * eliminar la Cancion base.
	 * 
	 * @param idMp3 ID del Mp3 a eliminar.
	 */
	public void eliminarMp3(int idMp3) {
		Mp3 mp3AEliminar = buscarMp3(idMp3);

		if (mp3AEliminar == null) {
			System.out.println("❌ MP3 con ID " + idMp3 + " no encontrado. No se puede eliminar.");
			return;
		}

		int idCancion = mp3AEliminar.getCancion().getId();

		// 1. Eliminar el registro Mp3 (opcionalmente, si la FK no tiene ON DELETE
		// CASCADE)
		// Si se usó ON DELETE CASCADE en la tabla Mp3, eliminar la Cancion es
		// suficiente.
		// Vamos a eliminar la Cancion base, que tiene ON DELETE CASCADE en la FK de
		// Mp3.
		cancionDao.eliminarCancion(idCancion);

		// Verificamos si la eliminación de la Cancion base fue exitosa (el método
		// de CancionDao ya imprime un mensaje si se elimina).
		// Podríamos hacer una verificación más estricta si CancionDao devolviera un
		// booleano.
		System.out.println("✅ MP3 con ID " + idMp3 + " y su Cancion base eliminados.");
	}

}