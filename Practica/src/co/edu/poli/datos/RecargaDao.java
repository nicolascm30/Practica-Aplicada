package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Recarga;

public class RecargaDao {

	// 💡 FIX (Error Recarga.Recarga): Se elimina la inicialización con la lista estática
	private List<Recarga> recargas = new ArrayList<>();

	public RecargaDao() {
	}

    // 💡 Método para crear la tabla Recarga
    public void crearTablaRecarga() {
        Connection conn = null;
        Statement st = null;
        String sql = "CREATE TABLE IF NOT EXISTS Recarga (\r\n"
        		+ "  idRecarga SERIAL PRIMARY KEY,\r\n"
        		+ "  idBilletera INT NOT NULL,\r\n"
        		+ "  monto DOUBLE PRECISION NOT NULL,\r\n"
        		+ "  fecha DATE NOT NULL DEFAULT CURRENT_DATE,\r\n"
        		+ "  FOREIGN KEY (idBilletera) REFERENCES Billetera(idBilletera)\r\n"
        		+ ");";
        try {
            conn = ConexionDB.getConnection();
            st = conn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Recarga: " + e.getMessage());
        } finally {
            ConexionDB.close(conn, st);
        }
    }

	public void crearRecarga(Recarga nueva) {
		recargas.add(nueva);
		System.out.println("✅ Recarga agregada: " + nueva);
	}
    // ...
}