package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Vinilo;

// Asumimos que esta clase fue proporcionada, la incluimos para que los managers funcionen
public class ViniloDao {

	// Inicializamos con los datos de simulación
	private List<Vinilo> vinilos = new ArrayList<>(Vinilo.Vinilo);

	public ViniloDao() {
	}

	public void crearVinilo(Vinilo nuevo) {
		vinilos.add(nuevo);
		System.out.println("Vinilo agregado: " + nuevo);
	}

	public void eliminarVinilo(int id) {
		vinilos.removeIf(v -> v.getIdVinilo() == id);
		System.out.println("Vinilo con ID " + id + " eliminado.");
	}

	public void verVinilos() {
		System.out.println("Lista de vinilos:");
		for (Vinilo v : vinilos) {
			System.out.println(v);
		}
	}

	public void actualizarVinilo(int id, String nuevoTitulo, String nuevoEstado) {
		for (Vinilo v : vinilos) {
			if (v.getIdVinilo() == id) {
				v.setTitulo(nuevoTitulo);
				// La clase Vinilo.java fue corregida para tener setEstado
				v.setEstado(nuevoEstado);
				System.out.println("Vinilo actualizado: " + v);
				return;
			}
		}
		System.out.println("Vinilo con ID " + id + " no encontrado.");
	}
	
	public Vinilo buscarVinilo(int id) {
		for (Vinilo v : vinilos) {
			if (v.getIdVinilo() == id) {
				return v;
			}
		}
		return null;
	}
	
	// Método adicional para obtener todos los Vinilos (útil para Managers)
	public List<Vinilo> obtenerTodosLosVinilos() {
        return new ArrayList<>(vinilos);
    }
}
