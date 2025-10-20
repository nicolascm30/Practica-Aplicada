package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Proveedor;

public class ProveedorDao {

	// NOTA: Esta lista no se inicializa con datos de simulación ya que los Proveedores
	// son parte de Vinilo. Se mantiene vacía para agregar nuevas.
	private List<Proveedor> proveedores = new ArrayList<>();

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
				// Asumiendo que Proveedor tiene setNombreEmpresa (o se usa setNombre de Persona)
				// Usaré setNombre de Persona, ya que Proveedor extiende Persona
				p.setNombre(nuevoNombre); 
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
	
	// Método adicional para obtener todos los Proveedores (útil para Managers)
	public List<Proveedor> obtenerTodosLosProveedores() {
        return new ArrayList<>(proveedores);
    }
}
