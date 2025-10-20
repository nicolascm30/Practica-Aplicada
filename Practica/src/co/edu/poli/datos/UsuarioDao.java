package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Usuario;

public class UsuarioDao {


    private List<Usuario> usuarios = new ArrayList<>(Usuario.Usuario);

    public UsuarioDao() {
    }

    public void crearUsuario(Usuario nuevo) {
        usuarios.add(nuevo);
        System.out.println("Usuario creado: " + nuevo);
    }

    public void verUsuarios() {
        System.out.println(" Lista de usuarios:");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public void eliminarUsuario(int cedula) {
        usuarios.removeIf(u -> u.getCedula() == cedula);
        System.out.println("Usuario con cédula " + cedula + " eliminado.");
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
}
