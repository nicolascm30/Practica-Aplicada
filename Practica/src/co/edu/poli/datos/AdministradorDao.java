package co.edu.poli.datos;

import co.edu.poli.presentacion.Administrador; // Importamos la entidad de la capa de presentación.
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Administrador.
 */
public class AdministradorDao {

    // Lista de simulación (Administrador es de capa Presentación, pero simulamos un DAO)
    private List<Administrador> administradores = new ArrayList<>();
    private static int nextId = 1;

    public AdministradorDao() {
    	// Inicializamos con un dato de prueba
        Administrador admin = new Administrador();
        admin.setIdAdmin(nextId++);
        admin.setCedula(90001);
        admin.setNombre("Neo Anderson");
        admin.setCorreo("neo.admin@poli.edu.co");
        admin.setRango(1);	
        administradores.add(admin);
    }

    public void crearAdministrador(Administrador nuevo) {
        nuevo.setIdAdmin(nextId++);
        administradores.add(nuevo);
        System.out.println("✅ Administrador creado: " + nuevo.getNombre());
    }

    public void eliminarAdministrador(int id) {
        boolean removed = administradores.removeIf(a -> a.getIdAdmin() == id);
        if (removed) {
            System.out.println("Administrador con ID " + id + " eliminado.");
        } else {
            System.out.println("Administrador con ID " + id + " no encontrado.");
        }
    }

    // 💡 Implementación necesaria para ManegerReportes
    public void verAdministrador() {
        if (administradores.isEmpty()) {
            System.out.println("No hay administradores registrados.");
        } else {
            for (Administrador a : administradores) {
                System.out.println("ID: " + a.getIdAdmin() + ", Nombre: " + a.getNombre() + ", Rango: " + a.getRango());
            }
        }
    }

    public void actualizarAdministrador(int id, String nuevoNombre) {
        for (Administrador a : administradores) {
            if (a.getIdAdmin() == id) {
                a.setNombre(nuevoNombre);
                System.out.println("Administrador actualizado: " + a.getNombre());
                return;
            }
        }
        System.out.println("Administrador con ID " + id + " no encontrado.");
    }
}