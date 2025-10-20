package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Proveedor;

public class ProveedorDao {

    private List<Proveedor> proveedores = new ArrayList<>(Proveedor.Proveedor);

    public ProveedorDao() {
    }

    public void crearProveedor(Proveedor nuevo) {
        proveedores.add(nuevo);
        System.out.println("Proveedor agregado: " + nuevo);
    }

    public void eliminarProveedor(int id) {
        proveedores.removeIf(p -> p.getIdProveedor() == id);
        System.out.println("Proveedor con ID " + id + " eliminado.");
    }

    public void verProveedores() {
        System.out.println("Lista de proveedores:");
        for (Proveedor p : proveedores) {
            System.out.println(p);
        }
    }

    public void actualizarProveedor(int id, String nuevoNombre, String nuevoCorreo) {
        for (Proveedor p : proveedores) {
            if (p.getIdProveedor() == id) {
                p.setNombreEmpresa(nuevoNombre);
                p.setCorreo(nuevoCorreo);
                System.out.println("proveedor actualizado: " + p);
                return;
            }
        }
        System.out.println("Proveedor con ID " + id + " no encontrado.");
    }

    public Proveedor buscarProveedor(int id) {
        for (Proveedor p : proveedores) {
            if (p.getIdProveedor() == id) {
                return p;
            }
        }
        return null;
    }
}
