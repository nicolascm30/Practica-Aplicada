package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Usuario;

// Asumimos que esta clase fue proporcionada, la incluimos para que los managers funcionen
public class UsuarioDao {

    // Inicializamos con los datos de simulación
    private List<Usuario> usuarios = new ArrayList<>(Usuario.Usuario);

    public UsuarioDao() {
    }

    public void crearUsuario(Usuario nuevo) {
        usuarios.add(nuevo);
        System.out.println("✅ Usuario creado: " + nuevo.getNombre());
    }

    public void verUsuarios() {
        System.out.println(" Lista de usuarios:");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public void eliminarUsuario(int cedula) {
        boolean removed = usuarios.removeIf(u -> u.getCedula() == cedula);
        if (removed) {
            System.out.println("Usuario con cédula " + cedula + " eliminado.");
        } else {
            System.out.println("Usuario con cédula " + cedula + " no encontrado.");
        }
    }

    public void actualizarUsuario(int cedula, String nuevoNombre, String nuevoCorreo) {
        for (Usuario u : usuarios) {
            if (u.getCedula() == cedula) {
                u.setNombre(nuevoNombre);
                u.setCorreo(nuevoCorreo);
                System.out.println("Usuario actualizado: " + u);
                return;
            }
        }
        System.out.println("Usuario con cédula " + cedula + " no encontrado.");
    }

    public Usuario buscarUsuario(int cedula) {
        for (Usuario u : usuarios) {
            if (u.getCedula() == cedula) {
                return u;
            }
        }
        return null;
    }
    
    /**
     * Busca un usuario por su correo. Necesario para el Manager de Seguridad.
     * @param correo Correo del usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    public Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null;
    }
    
    // Método adicional para obtener todos los Usuarios (útil para Managers)
	public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(usuarios);
    }
}
