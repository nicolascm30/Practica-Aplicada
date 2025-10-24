package co.edu.poli.datos;

import co.edu.poli.dataBase.Cancion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList; // 💡 Importación necesaria
import java.util.List; // 💡 Importación necesaria

/**
 * DAO para la entidad Cancion (Versión de Base de Datos JDBC).
 */
public class CancionDao {

	// 💡 Método auxiliar para formatear segundos a MM:SS, tomado de la versión de
	// simulación
	private String formatDuration(double totalSegundos) {
		int segundos = (int) Math.round(totalSegundos);
		if (segundos < 0)
			return "0:00";
		int minutos = segundos / 60;
		int segundosRestantes = segundos % 60;
		return String.format("%d:%02d", minutos, segundosRestantes);
	}

	// 1. ✅ FIX: Implementa el método llamado desde Main.java para inicializar la DB.
	public void crearTablasSiNoExisten() {
		crearTablaCancion();
		// Aquí podrías agregar llamadas a la creación de otras tablas relacionadas si
		// las tuvieras
	}

	private void crearTablaCancion() { // Se mantiene privado ya que la llamada general es crearTablasSiNoExisten
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Cancion (\r\n" + "  id SERIAL PRIMARY KEY,\r\n"
				+ "  titulo VARCHAR(255) NOT NULL,\r\n" + "  artista VARCHAR(255) NOT NULL,\r\n"
				+ "  duracionSegundos DOUBLE PRECISION NOT NULL\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("✅ Tabla 'Cancion' creada/verificada.");
		} catch (SQLException e) {
			System.err.println("❌ Error al crear la tabla Cancion: " + e.getMessage());
		} finally {
			ConexionDB.close(st);
			ConexionDB.close(conn);
		}
	}
    
    // ---------------------------------------------------
	// 📜 Método OBTENER TODOS (Read All - Lista)
	// ---------------------------------------------------

    /**
	 * Obtiene todas las Canciones de la base de datos.
	 * 💡 ESTE MÉTODO ES NECESARIO para que Main.java verifique si debe cargar las canciones de prueba.
	 * @return Una lista de objetos Cancion.
	 */
	public List<Cancion> obtenerTodasLasCanciones() {
		List<Cancion> canciones = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = "SELECT id, titulo, artista, duracionSegundos FROM Cancion ORDER BY id";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);

				while (rs.next()) {
					int id = rs.getInt("id");
					String titulo = rs.getString("titulo");
					String artista = rs.getString("artista");
					double duracionSegundos = rs.getDouble("duracionSegundos");
					
					// Mapear el ResultSet a un objeto Cancion
					canciones.add(new Cancion(id, titulo, artista, duracionSegundos));
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error al obtener la lista de Canciones: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(st); // Usamos Statement, no PreparedStatement
			ConexionDB.close(conn);
		}
		return canciones;
	}


	// 2. ✅ FIX: Implementa el método llamado desde Main.java para listar (ver) las
	// canciones.
	public void verCanciones() {
        // En lugar de duplicar la lógica, llamamos al método que obtiene la lista.
		List<Cancion> canciones = obtenerTodasLasCanciones(); 
        
        System.out.println("Lista de canciones (DB):");
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-30s | %-20s | %-10s |\n", "ID", "TÍTULO", "ARTISTA", "DURACIÓN");
        System.out.println("--------------------------------------------------------------------------");
        
        if (canciones.isEmpty()) {
            System.out.printf("| %-70s |\n", "No hay canciones registradas.");
        } else {
            for (Cancion cancion : canciones) {
                String duracionFormateada = formatDuration(cancion.getDuracionSegundos());

                System.out.printf("| %-4d | %-30s | %-20s | %-10s |\n", 
                                cancion.getId(), 
                                cancion.getTitulo(), 
                                cancion.getArtista(), 
                                duracionFormateada);
            }
        }
        System.out.println("--------------------------------------------------------------------------");
	}

	// 3. Implementación de buscarCancion para que Main.java no falle en la opción 3
	// (Comprar)
	public Cancion buscarCancion(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Cancion cancion = null;
		String sql = "SELECT id, titulo, artista, duracionSegundos FROM Cancion WHERE id = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				rs = ps.executeQuery();

				if (rs.next()) {
					String titulo = rs.getString("titulo");
					String artista = rs.getString("artista");
					double duracionSegundos = rs.getDouble("duracionSegundos");
					// Usar el constructor de entidad Cancion para devolver el objeto
					cancion = new Cancion(id, titulo, artista, duracionSegundos);
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error buscando Cancion con ID " + id + ": " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
		return cancion;
	}

	// ---------------------------------------------------
	// ➕ Método CREAR (Create)
	// ---------------------------------------------------

	/**
	 * Crea un nuevo registro de Cancion. Asigna el ID generado a la entidad Cancion
	 * pasada por referencia. * @param nuevo El objeto Cancion a guardar.
	 */
	public void crearCancion(Cancion nuevo) { // FIX: Agregado para MP3Dao (Línea 65)
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Uso de DEFAULT para que la DB (PostgreSQL SERIAL) asigne el ID.
		String sql = "INSERT INTO Cancion (titulo, artista, duracionSegundos) VALUES (?, ?, ?)";
		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				// Solicitamos las claves generadas
				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, nuevo.getTitulo());
				ps.setString(2, nuevo.getArtista());
				ps.setDouble(3, nuevo.getDuracionSegundos());

				int affectedRows = ps.executeUpdate();

				if (affectedRows > 0) {
					rs = ps.getGeneratedKeys();
					if (rs.next()) {
						// Asignamos el ID generado al objeto Cancion, vital para MP3Dao
						nuevo.setId(rs.getInt(1));
					}
					System.out.println("✅ Canción creada (DB): ID " + nuevo.getId() + " - " + nuevo.getTitulo());
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error insertando Cancion: " + e.getMessage());
		} finally {
			ConexionDB.close(rs);
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	public void actualizarCancion(int id, String nuevoTitulo, String nuevoArtista, double nuevaDuracionSegundos) { // FIX:
																													// Firma
																													// solicitada
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Cancion SET titulo = ?, artista = ?, duracionSegundos = ? WHERE id = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, nuevoTitulo);
				ps.setString(2, nuevoArtista);
				ps.setDouble(3, nuevaDuracionSegundos);
				ps.setInt(4, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows == 0) {
					System.out.println("❌ Canción con ID " + id + " no encontrada para actualizar.");
				} else {
					// El MP3Dao imprime su propio mensaje de éxito, este se puede omitir o ser solo
					// para debug.
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error actualizando Cancion: " + e.getMessage());
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}

	// ---------------------------------------------------
	// 🗑️ Método ELIMINAR (Delete)
	// ---------------------------------------------------

	/**
	 * Elimina una canción por su ID. * @param id ID de la canción a eliminar.
	 */
	public void eliminarCancion(int id) { // FIX: Agregado para MP3Dao (Línea 275)
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM Cancion WHERE id = ?";

		try {
			conn = ConexionDB.getConnection();
			if (conn != null) {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);

				int affectedRows = ps.executeUpdate();
				if (affectedRows > 0) {
					System.out.println("✅ Canción con ID " + id + " eliminada (y MP3 asociado por CASCADE).");
				} else {
					System.out.println("❌ Canción con ID " + id + " no encontrada para eliminar.");
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Error eliminando Canción: " + e.getMessage());
		} finally {
			ConexionDB.close(ps);
			ConexionDB.close(conn);
		}
	}
}