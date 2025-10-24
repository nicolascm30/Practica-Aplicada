package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {

	// 🔗 URL Base del servidor PostgreSQL en Supabase
	private static final String URL_BASE = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres";

	// 🔐 Credenciales del proyecto
	private static final String USER = "postgres.tsmvxvxpvysvlhiskvfd";
	private static final String PASS = "Poli2025*000";

	/**
	 * Establece y retorna la conexión a la base de datos Supabase (PostgreSQL).
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			// Cargar el driver de PostgreSQL
			Class.forName("org.postgresql.Driver");

			// Conexión usando URL, usuario y contraseña
			connection = DriverManager.getConnection(URL_BASE, USER, PASS);

			System.out.println("✅ Conexión exitosa a la base de datos Supabase.");

		} catch (ClassNotFoundException e) {
			System.err.println(
					"❌ Error: No se encontró el driver de PostgreSQL. Asegúrate de tener el JAR en el classpath.");
		} catch (SQLException e) {
			System.err.println("❌ Error de conexión a la base de datos: " + e.getMessage());
			System.err.println("👉 Verifica las credenciales, host o puerto en ConexionDB.java");
		}
		return connection; // Retorna null si la conexión falló
	}

	// --- MÉTODOS DE CIERRE ---

	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		close(rs);
		close(ps);
		close(conn);
	}

	public static void close(Connection conn, PreparedStatement ps) {
		close(ps);
		close(conn);
	}

	public static void close(Connection conn, Statement st) {
		close(st);
		close(conn);
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// Ignorar
			}
		}
	}

	public static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// Ignorar
			}
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// Ignorar
			}
		}
	}

	// --- Método de prueba ---
	public static void main(String[] args) {
		Connection conn = ConexionDB.getConnection();
		if (conn != null) {
			System.out.println("🎯 Prueba completada: conexión establecida correctamente.");
			close(conn);
		} else {
			System.out.println("⚠️ No se pudo establecer la conexión.");
		}
	}
}
