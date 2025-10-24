package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
// import java.util.ArrayList; // Ya no se usa la lista
// import java.util.List; // Ya no se usa la lista
// import co.edu.poli.dataBase.Vinilo; // Ya no se usa la lista

public class ViniloDao {

	// 💡 FIX: Se elimina la lista "vinilos" porque no se usaba (Warning)
	// private List<Vinilo> vinilos = new ArrayList<>();

	public ViniloDao() {
	}

	public void crearTablaVinilo() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Vinilo (\r\n" + "  idVinilo SERIAL PRIMARY KEY,\r\n"
				+ "  idCancion INT NOT NULL,\r\n" + "  idProveedor INT NOT NULL,\r\n"
				+ "  precio DOUBLE PRECISION NOT NULL,\r\n" + "  estado VARCHAR(50) NOT NULL,\r\n"
				+ "  FOREIGN KEY (idCancion) REFERENCES Cancion(id),\r\n"
				+ "  FOREIGN KEY (idProveedor) REFERENCES Proveedor(idProveedor)\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Error al crear la tabla Vinilo: " + e.getMessage());
		} finally {
			ConexionDB.close(conn, st);
		}
	}

	public void verVinilos() {
		System.out.println("Lista de vinilos (simulación, sin datos):");
	}
}