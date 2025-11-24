package co.edu.poli.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {

    // 🔗 URL correcta con puerto del pooler + SSL
    private static final String URL_BASE =
            "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require";

    // 🔐 Credenciales nuevas
    private static final String USER = "postgres.wuuszgucbboemzeraewf";
    private static final String PASS = "Musica205**";

    /**
     * Establece y retorna la conexión a la base de datos Supabase (PostgreSQL).
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(URL_BASE, USER, PASS);

            System.out.println("✅ Conexión exitosa a la base de datos Supabase.");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver PostgreSQL no encontrado.");
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
        return connection;
    }

    // ---- MÉTODOS DE CIERRE ----

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
        if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    }

    public static void close(Statement st) {
        if (st != null) try { st.close(); } catch (SQLException ignored) {}
    }

    public static void close(Connection conn) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
    }

    public static void main(String[] args) {
        Connection conn = ConexionDB.getConnection();
        if (conn != null) {
            System.out.println("🎯 Prueba completada: conexión establecida.");
            close(conn);
        } else {
            System.out.println("⚠️ No se pudo establecer la conexión.");
        }
    }
}
