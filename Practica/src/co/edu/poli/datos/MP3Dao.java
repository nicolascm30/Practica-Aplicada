package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
// import java.util.List; // Ya no se usa la lista
// import java.util.ArrayList; // Ya no se usa la lista
// import co.edu.poli.dataBase.Mp3; // Ya no se usa la lista

public class MP3Dao {

	// 💡 FIX: Se elimina la lista "mp3List" porque no se usaba (Warning)
	// private List<Mp3> mp3List = new ArrayList<>();

	public MP3Dao() {
	}

	public void crearTablaMp3() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Mp3 (\r\n" + "  idMp3 SERIAL PRIMARY KEY,\r\n"
				+ "  idCancion INT NOT NULL,\r\n" + "  formato VARCHAR(50) NOT NULL,\r\n"
				+ "  tamanioMB DOUBLE PRECISION NOT NULL,\r\n" + "  precio DOUBLE PRECISION NOT NULL,\r\n"
				+ "  FOREIGN KEY (idCancion) REFERENCES Cancion(id)\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Error al crear la tabla Mp3: " + e.getMessage());
		} finally {
			ConexionDB.close(conn, st);
		}
	}

	public void verMp3() {
		System.out.println("Lista de archivos MP3 (simulación, sin datos):");
	}
}