package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Transaccion;

public class TransaccionDao {

	// Esta lista se mantiene para la simulación de 'crearTransaccion'
	private List<Transaccion> transacciones = new ArrayList<>();

	public TransaccionDao() {
	}

	public void crearTablaTransaccion() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Transaccion (\r\n" + "  idTransaccion SERIAL PRIMARY KEY,\r\n"
				+ "  idBilleteraOrigen INT NOT NULL,\r\n" + "  idBilleteraDestino INT NOT NULL,\r\n"
				+ "  monto DOUBLE PRECISION NOT NULL,\r\n" + "  fecha DATE NOT NULL DEFAULT CURRENT_DATE,\r\n"
				+ "  FOREIGN KEY (idBilleteraOrigen) REFERENCES Billetera(idBilletera),\r\n"
				+ "  FOREIGN KEY (idBilleteraDestino) REFERENCES Billetera(idBilletera)\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Error al crear la tabla Transaccion: " + e.getMessage());
		} finally {
			ConexionDB.close(conn, st);
		}
	}

	public void crearTransaccion(Transaccion nueva) {
		transacciones.add(nueva);
		System.out.println("✅ Transacción registrada: " + nueva);
	}

	// 💡 FIX: Método añadido para ManegerReportes
	public void verTransacciones() {
		System.out.println("Simulación JDBC: Mostrando transacciones...");
		// Aquí iría la lógica SELECT * FROM Transaccion
		if (transacciones.isEmpty()) {
			System.out.println("(No hay transacciones en la lista de simulación)");
		}
		for (Transaccion t : transacciones) {
			System.out.println(t);
		}
	}
}