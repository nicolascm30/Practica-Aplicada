package co.edu.poli.negocio;

import co.edu.poli.dataBase.Billetera;
import co.edu.poli.dataBase.Usuario;
import co.edu.poli.datos.BilleteraDao;
import co.edu.poli.datos.UsuarioDao;

/**
 * Manager para la lógica de creación de usuarios y su billetera asociada.
 */
public class ManagerCrearUsuario {

    private UsuarioDao usuarioDao;
    private BilleteraDao billeteraDao;
    
    // Contador para generar IDs únicos de billetera
    private static int nextBilleteraId = 6; 

    public ManagerCrearUsuario() {
        // Inicialización de los DAOs (simulando inyección de dependencias)
        this.usuarioDao = new UsuarioDao();
        this.billeteraDao = new BilleteraDao();
    }

    public UsuarioDao crearUsuarioDao() {
        return this.usuarioDao;
    }

    public BilleteraDao crearBilleteraDao() {
        return this.billeteraDao;
    }

    /**
     * Crea un nuevo usuario y su billetera asociada.
     * @param cedula Cédula del nuevo usuario.
     * @param nombre Nombre del nuevo usuario.
     * @param correo Correo del nuevo usuario.
     * @param contrasena Contraseña.
     * @param rol Rol (ej: "Cliente").
     * @return El usuario creado, o null si la cédula ya existe.
     */
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
            nextBilleteraId++, 
            nuevoUsuario, 
            0.0, 
            "Activa"
        );
        billeteraDao.crearBilletera(nuevaBilletera);
        
        System.out.println("✅ Creación exitosa para " + nombre + " (Billetera ID: " + nuevaBilletera.getIdBilletera() + ")");
        return nuevoUsuario;
    }
}
