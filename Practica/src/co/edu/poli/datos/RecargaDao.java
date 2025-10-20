package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Recarga;

public class RecargaDao {

	// Inicializamos con los datos de simulación
	private List<Recarga> recargas = new ArrayList<>(Recarga.Recarga);

	public RecargaDao() {
	}

	public void crearRecarga(Recarga nueva) {
		recargas.add(nueva);
		System.out.println("✅ Recarga agregada: " + nueva);
	}

	public void eliminarRecarga(int id) {
		recargas.removeIf(r -> r.getIdRecarga() == id);
		System.out.println("Recarga con ID " + id + " eliminada.");
	}

	public void verRecargas() {
		System.out.println("Lista de recargas:");
		for (Recarga r : recargas) {
			System.out.println(r);
		}
	}

	/**
	 * Actualiza el monto de una recarga.
	 * Se corrige el setter de 'setValor' a 'setMonto'.
	 * @param id ID de la recarga.
	 * @param nuevoMonto Nuevo monto de la recarga.
	 */
	public void actualizarRecarga(int id, double nuevoMonto) {
		for (Recarga r : recargas) {
			if (r.getIdRecarga() == id) {
				r.setMonto(nuevoMonto); // FIX: Usar setMonto() en lugar de setValor()
				System.out.println("Recarga actualizada: " + r);
				return;
			}
		}
		System.out.println("Recarga con ID " + id + " no encontrada.");
	}

	public Recarga buscarRecarga(int id) {
		for (Recarga r : recargas) {
			if (r.getIdRecarga() == id) {
				return r;
			}
		}
		return null;
	}
	
	// Método adicional para obtener todas las recargas (útil para Managers)
	public List<Recarga> obtenerTodasLasRecargas() {
        return new ArrayList<>(recargas);
    }
}
