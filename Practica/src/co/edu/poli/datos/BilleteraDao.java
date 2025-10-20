package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Billetera;

// Asumimos que esta clase fue proporcionada, la incluimos para que los managers funcionen
public class BilleteraDao {

	// Inicializamos con los datos de simulación
	private List<Billetera> billeteras = new ArrayList<>(Billetera.Billetera);

	public BilleteraDao() {
	}

	public void crearBilletera(Billetera nueva) {
		billeteras.add(nueva);
		System.out.println("✅ Billetera creada: " + nueva.getIdBilletera());
	}

	public void eliminarBilletera(int idBilletera) {
		billeteras.removeIf(b -> b.getIdBilletera() == idBilletera);
		System.out.println("Billetera con ID " + idBilletera + " eliminada.");
	}

	public void verBilleteras() {
		System.out.println("Lista de billeteras:");
		for (Billetera b : billeteras) {
			System.out.println(b);
		}
	}

	public void actualizarBilletera(int idBilletera, double nuevoSaldo, String nuevoEstado) {
		for (Billetera b : billeteras) {
			if (b.getIdBilletera() == idBilletera) {
				b.setSaldoActual(nuevoSaldo);
				b.setEstado(nuevoEstado);
				System.out.println("Billetera actualizada: " + b);
				return;
			}
		}
		System.out.println("Billetera con ID " + idBilletera + " no encontrada.");
	}

	public Billetera buscarBilletera(int idBilletera) {
		for (Billetera b : billeteras) {
			if (b.getIdBilletera() == idBilletera) {
				return b;
			}
		}
		return null;
	}
	
	// Método adicional para obtener todas las Billeteras (útil para Managers)
	public List<Billetera> obtenerTodasLasBilleteras() {
        return new ArrayList<>(billeteras);
    }
}
