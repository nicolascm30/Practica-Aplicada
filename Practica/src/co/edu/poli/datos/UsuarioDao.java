package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
// import java.util.ArrayList; // Ya no se usa la lista
// import java.util.List; // Ya no se usa la lista
import co.edu.poli.dataBase.Usuario;

public class UsuarioDao {

	// 💡 FIX: Se elimina la lista "usuarios" porque no se usaba (Warning)
	// private List<Usuario> usuarios = new ArrayList<>();

	public UsuarioDao() {
	}

	public void crearTablaUsuario() {
		Connection conn = null;
		Statement st = null;
		String sql = "CREATE TABLE IF NOT EXISTS Usuario (\r\n" + "  cedula INT PRIMARY KEY,\r\n"
				+ "  nombre VARCHAR(255) NOT NULL,\r\n" + "  correo VARCHAR(255) UNIQUE NOT NULL,\r\n"
				+ "  contrasena VARCHAR(255) NOT NULL,\r\n" + "  rol VARCHAR(50) NOT NULL\r\n" + ");";
		try {
			conn = ConexionDB.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Error al crear la tabla Usuario: " + e.getMessage());
		} finally {
			ConexionDB.close(conn, st);
		}
	}

	public Usuario buscarUsuario(int cedula) {
		if (cedula == 1010) {
			return new Usuario(1010, "Carlos Pérez", "carlos.perez@gmail.com", "pass123", "Comprador");
		}
		return null;
	}

	public void crearUsuario(Usuario nuevo) {
		System.out.println("Simulación JDBC: Insertando usuario " + nuevo.getNombre());
	}

	public void verUsuarios() {
		System.out.println("Simulación JDBC: Obteniendo lista de usuarios.");
	}
}