package co.edu.poli.negocio;

import co.edu.poli.database.Billetera;
import co.edu.poli.database.Usuario;
import co.edu.poli.datos.BilleteraDao;
import co.edu.poli.datos.UsuarioDao;

/**
 * Manager para la lógica de creación de usuarios y su billetera asociada.
 */
public class ManagerCrearUsuario {

    private UsuarioDao usuarioDao;
    private BilleteraDao billeteraDao;
    
    // ❌ ELIMINAR ESTA LÍNEA (causa el conflicto de IDs)
    // private static int nextBilleteraId = 6; 

    public ManagerCrearUsuario() {
        // Inicialización de los DAOs (simulando inyección de dependencias)
        this.usuarioDao = new UsuarioDao();
        this.billeteraDao = new BilleteraDao();
    }
    // ... (rest of the class)

    public Usuario crearNuevoCliente(int cedula, String nombre, String correo, String contrasena, String rol) {
        if (usuarioDao.buscarUsuario(cedula) != null) {
            System.out.println("❌ Error: Ya existe un usuario con la cédula " + cedula);
            return null;
        }

        // 1. Crear Usuario
        Usuario nuevoUsuario = new Usuario(cedula, nombre, correo, contrasena, rol);
        usuarioDao.crearUsuario(nuevoUsuario);

        // 2. Crear Billetera asociada
        Billetera nuevaBilletera = new Billetera(
            // ✅ CORRECCIÓN: Usamos 0 o -1 como ID temporal. 
            // El DAO lo ignorará y la DB generará el ID correcto.
            0, 
            nuevoUsuario, 
            0.0, 
            "Activa"
        );
        billeteraDao.crearBilletera(nuevaBilletera);
        
        System.out.println("✅ Creación exitosa: Usuario " + cedula + " y Billetera asociada.");
        return nuevoUsuario;
    }
}