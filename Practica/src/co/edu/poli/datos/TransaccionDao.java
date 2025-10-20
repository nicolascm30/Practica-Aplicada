package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Transaccion;

// Asumimos que esta clase fue proporcionada, la incluimos para que los managers funcionen
public class TransaccionDao {

	// Inicializamos con los datos de simulación
	private List<Transaccion> transacciones = new ArrayList<>(Transaccion.Transaccion);

	public TransaccionDao() {
	}

	public void crearTransaccion(Transaccion nueva) {
		transacciones.add(nueva);
		System.out.println("✅ Transacción registrada: " + nueva);
	}

	public void eliminarTransaccion(int id) {
		transacciones.removeIf(t -> t.getIdTransaccion() == id);
		System.out.println("Transacción con ID " + id + " eliminada.");
	}

	public void verTransacciones() {
		System.out.println("Lista de transacciones:");
		for (Transaccion t : transacciones) {
			System.out.println(t);
		}
	}

	public void actualizarTransaccion(int id, double nuevoMonto) {
		for (Transaccion t : transacciones) {
			if (t.getIdTransaccion() == id) {
				t.setMonto(nuevoMonto);
				System.out.println("Transacción actualizada: " + t);
				return;
			}
		}
		System.out.println("Transacción con ID " + id + " no encontrada.");
	}

	public Transaccion buscarTransaccion(int id) {
		for (Transaccion t : transacciones) {
			if (t.getIdTransaccion() == id) {
				return t;
			}
		}
		return null;
	}
	
	// Método adicional para obtener todas las Transacciones (útil para Managers)
	public List<Transaccion> obtenerTodasLasTransacciones() {
        return new ArrayList<>(transacciones);
    }
}
