package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.dataBase.Billetera;

public class BilleteraDao {

	// 💡 FIX: Se elimina la inicialización con la lista estática
	private List<Billetera> billeteras = new ArrayList<>();

	public BilleteraDao() {
	}

    // 💡 Método para crear la tabla Billetera
    public void crearTablaBilletera() {
        Connection conn = null;
        Statement st = null;
        String sql = "CREATE TABLE IF NOT EXISTS Billetera (\r\n"
        		+ "  idBilletera SERIAL PRIMARY KEY,\r\n"
        		+ "  cedulaUsuario INT NOT NULL,\r\n"
        		+ "  saldoActual DOUBLE PRECISION NOT NULL,\r\n"
        		+ "  estado VARCHAR(50) NOT NULL,\r\n"
        		+ "  FOREIGN KEY (cedulaUsuario) REFERENCES Usuario(cedula)\r\n"
        		+ ");";
        try {
            conn = ConexionDB.getConnection();
            st = conn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Billetera: " + e.getMessage());
        } finally {
            ConexionDB.close(conn, st); // FIX: Ahora usa el método close de ConexionDB
        }
    }

	public void crearBilletera(Billetera nueva) {
		billeteras.add(nueva);
		System.out.println("✅ Billetera creada: " + nueva.getIdBilletera());
	}
    // ... (El resto de los métodos deben ser migrados a JDBC)
}