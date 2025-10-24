package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Proveedor;

public class ProveedorDao {

	// Esta lista se mantiene para la simulación de 'crearProveedor'
	private List<Proveedor> proveedores = new ArrayList<>();

	public ProveedorDao() {
	}

    public void crearTablaProveedor() {
        Connection conn = null;
        Statement st = null;
        String sql = "CREATE TABLE IF NOT EXISTS Proveedor (\r\n"
        		+ "  idProveedor SERIAL PRIMARY KEY,\r\n"
        		+ "  nombreEmpresa VARCHAR(255) NOT NULL,\r\n"
        		+ "  contacto VARCHAR(255) NOT NULL,\r\n"
        		+ "  correo VARCHAR(255) UNIQUE NOT NULL\r\n"
        		+ ");";
        try {
            conn = ConexionDB.getConnection();
            st = conn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Proveedor: " + e.getMessage());
        } finally {
            ConexionDB.close(conn, st);
        }
    }

	public void crearProveedor(Proveedor nuevo) {
		proveedores.add(nuevo);
		System.out.println("Proveedor agregado: " + nuevo);
	}

    // 💡 FIX: Método añadido para ManegerReportes
	public void verProveedores() {
		System.out.println("Simulación JDBC: Mostrando proveedores...");
        // Aquí iría la lógica SELECT * FROM Proveedor
        if (proveedores.isEmpty()) {
            System.out.println("(No hay proveedores en la lista de simulación)");
        }
		for (Proveedor p : proveedores) {
			System.out.println(p);
		}
	}
}