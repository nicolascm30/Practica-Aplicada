package co.edu.poli.datos;

import co.edu.poli.dataBase.Billetera;
import co.edu.poli.dataBase.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * DAO para la entidad Billetera, migrado a JDBC para persistencia.
 */
public class BilleteraDao {

    // 💡 Eliminamos la lista de simulación (ArrayList)
    
    // Se necesita una instancia de UsuarioDao para gestionar la composición
    private UsuarioDao usuarioDao; 

    public BilleteraDao() {
        // Inicialización del DAO compuesto
        this.usuarioDao = new UsuarioDao();
    }

    /**
     * Crea la tabla Billetera si no existe.
     */
    public void crearTablaBilletera() {
        Connection conn = null;
        Statement st = null;
        // Definición de la tabla: idBilletera (PK/SERIAL), cedulaUsuario (FK), saldoActual, estado
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
            System.out.println("✅ Tabla 'Billetera' creada/verificada.");
        } catch (SQLException e) {
            System.err.println("❌ Error al crear la tabla Billetera: " + e.getMessage());
        } finally {
            // Uso de cierre individual para recursos, patrón robusto
            ConexionDB.close(st);
            ConexionDB.close(conn);
        }
    }

    /**
     * Inserta una nueva billetera en la base de datos.
     * El idBilletera es autogenerado por la DB (SERIAL).
     * @param nueva El objeto Billetera a guardar.
     */
	public void crearBilletera(Billetera nueva) {
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // 1. Obtener la clave foránea (cedulaUsuario)
        int cedulaUsuario = nueva.getUsuario().getCedula();
        
        String sql = "INSERT INTO Billetera (cedulaUsuario, saldoActual, estado) VALUES (?, ?, ?)";
        try {
            conn = ConexionDB.getConnection();
            if (conn != null) {
                // Solicitamos la clave generada por la base de datos
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                ps.setInt(1, cedulaUsuario);
                ps.setDouble(2, nueva.getSaldoActual());
                ps.setString(3, nueva.getEstado());
                
                int affectedRows = ps.executeUpdate();
                
                if (affectedRows > 0) {
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        // Asignamos el ID generado al objeto Java
                        nueva.setIdBilletera(rs.getInt(1)); 
                    }
                    System.out.println("✅ Billetera creada (DB): ID " + nueva.getIdBilletera() + ", Cédula Usuario: " + cedulaUsuario);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error insertando billetera: " + e.getMessage());
        } finally {
            ConexionDB.close(rs);
            ConexionDB.close(ps);
            ConexionDB.close(conn);
        }
	}
    
    /**
     * Elimina una billetera por su ID.
     * @param idBilletera ID de la Billetera a eliminar.
     */
    public void eliminarBilletera(int idBilletera) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM Billetera WHERE idBilletera = ?";
        try {
            conn = ConexionDB.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, idBilletera);
                
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("✅ Billetera con ID " + idBilletera + " eliminada.");
                } else {
                    System.out.println("❌ Billetera con ID " + idBilletera + " no encontrada.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error eliminando billetera: " + e.getMessage());
        } finally {
            ConexionDB.close(ps);
            ConexionDB.close(conn);
        }
    }
    
    /**
     * Lista todas las billeteras de la base de datos (versión simplificada).
     */
    public void verBilleteras() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT idBilletera, cedulaUsuario, saldoActual, estado FROM Billetera ORDER BY idBilletera";

        System.out.println("📊 Lista de Billeteras (DB):");
        try {
            conn = ConexionDB.getConnection();
            if (conn != null) {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int id = rs.getInt("idBilletera");
                    int cedula = rs.getInt("cedulaUsuario");
                    double saldo = rs.getDouble("saldoActual");
                    String estado = rs.getString("estado");
                    
                    System.out.println(String.format("  - ID: %d, Cédula Usuario: %d, Saldo: $%,.2f, Estado: %s", id, cedula, saldo, estado));
                }
                
                if (!found) {
                    System.out.println("No hay billeteras registradas.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error listando billeteras: " + e.getMessage());
        } finally {
            ConexionDB.close(rs);
            ConexionDB.close(stmt);
            ConexionDB.close(conn);
        }
    }

    /**
     * Actualiza el saldo y el estado de una billetera por su ID.
     * @param idBilletera ID de la Billetera a actualizar.
     * @param nuevoSaldo Nuevo saldo de la billetera.
     * @param nuevoEstado Nuevo estado de la billetera.
     */
    public void actualizarBilletera(int idBilletera, double nuevoSaldo, String nuevoEstado) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE Billetera SET saldoActual = ?, estado = ? WHERE idBilletera = ?";
        try {
            conn = ConexionDB.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                
                ps.setDouble(1, nuevoSaldo);
                ps.setString(2, nuevoEstado);
                ps.setInt(3, idBilletera);
                
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("✅ Billetera con ID " + idBilletera + " actualizada. Nuevo Saldo: " + nuevoSaldo + ", Estado: " + nuevoEstado);
                } else {
                    System.out.println("❌ Billetera con ID " + idBilletera + " no encontrada.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error actualizando billetera: " + e.getMessage());
        } finally {
            ConexionDB.close(ps);
            ConexionDB.close(conn);
        }
    }
    
    /**
     * Busca una Billetera por su ID, cargando también su Usuario asociado.
     * @param idBilletera ID de la billetera a buscar.
     * @return El objeto Billetera encontrado o null.
     */
    public Billetera buscarBilletera(int idBilletera) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT idBilletera, cedulaUsuario, saldoActual, estado FROM Billetera WHERE idBilletera = ?";
        Billetera billetera = null;

        try {
            conn = ConexionDB.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, idBilletera);
                rs = ps.executeQuery();

                if (rs.next()) {
                    // Mapear la fila del ResultSet a un objeto Billetera
                    Billetera b = new Billetera();
                    b.setIdBilletera(rs.getInt("idBilletera"));
                    b.setSaldoActual(rs.getDouble("saldoActual"));
                    b.setEstado(rs.getString("estado"));

                    // IMPORTANTE: Cargar el objeto Usuario usando el UsuarioDao
                    int cedulaUsuario = rs.getInt("cedulaUsuario");
                    Usuario usuario = usuarioDao.buscarUsuario(cedulaUsuario); 
                    
                    if (usuario != null) {
                        b.setUsuario(usuario);
                        billetera = b;
                    } else {
						System.err.println("❌ Error de integridad: Usuario asociado (Cédula: " + cedulaUsuario
								+ ") no encontrado.");
					}
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error buscando billetera: " + e.getMessage());
        } finally {
            ConexionDB.close(rs);
            ConexionDB.close(ps);
            ConexionDB.close(conn);
        }
        return billetera; 
    }
}