package co.edu.poli.datos;

import co.edu.poli.dataBase.Cancion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
// import java.util.ArrayList; // Ya no se usa la lista
// import java.util.List; // Ya no se usa la lista

public class CancionDao {

	// 💡 FIX: Se elimina la lista "canciones" porque no se usaba (Warning)
	// private List<Cancion> canciones = new ArrayList<>();

	// DAOs auxiliares para orquestar la creación de tablas
	private final ProveedorDao proveedorDao = new ProveedorDao();
	private final ViniloDao viniloDao = new ViniloDao();
	private final MP3Dao mp3Dao = new MP3Dao();
	private final UsuarioDao usuarioDao = new UsuarioDao();
	private final BilleteraDao billeteraDao = new BilleteraDao();
	private final RecargaDao recargaDao = new RecargaDao();
	private final TransaccionDao transaccionDao = new TransaccionDao();

	public CancionDao() {
	}

	public void crearTablasSiNoExisten() {
		System.out.println("Creando tablas en la Base de Datos...");

		// ... (resto del método sin cambios)
		usuarioDao.crearTablaUsuario();
		billeteraDao.crearTablaBilletera();
		recargaDao.crearTablaRecarga();
		transaccionDao.crearTablaTransaccion();
		proveedorDao.crearTablaProveedor();
		crearTablaCancion();
		mp3Dao.crearTablaMp3();
		viniloDao.crearTablaVinilo();

		System.out.println("Estructura de la base de datos verificada y/o creada.");
	}

	private void crearTablaCancion() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Cancion (\r\n" + "  id SERIAL PRIMARY KEY,\r\n"
				+ "  titulo VARCHAR(255) NOT NULL,\r\n" + "  artista VARCHAR(255) NOT NULL,\r\n"
				+ "  duracionSegundos DOUBLE PRECISION NOT NULL\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Error al crear la tabla Cancion: " + e.getMessage());
		} finally {
			ConexionDB.close(conn, st);
		}
	}

	public void verCanciones() {
		System.out.println("\n--- 💿 CANCIONES DISPONIBLES EN LA TIENDA (Simulación DB) ---");
		mp3Dao.verMp3();
		viniloDao.verVinilos();
		System.out.println("--------------------------------------------------------------------------");
	}

	public Cancion buscarCancion(int id) {
		return null;
	}
}