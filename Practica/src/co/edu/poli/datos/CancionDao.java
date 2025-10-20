package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Cancion;

public class CancionDao {

	// NOTA: Esta lista no se inicializa con datos de simulación ya que las Canciones
	// son parte de Mp3 o Vinilo. Se mantiene vacía para agregar nuevas.
	private List<Cancion> canciones = new ArrayList<>();

	public CancionDao() {
	}

	public void crearCancion(Cancion nueva) {
		canciones.add(nueva);
		System.out.println("Canción agregada: " + nueva);
	}

	/**
	 * Elimina una canción por su ID.
	 * Se corrige el getter de 'getIdCancion' a 'getId'.
	 * @param id ID de la canción a eliminar.
	 */
	public void eliminarCancion(int id) {
		canciones.removeIf(c -> c.getId() == id); // FIX: Usar getId()
		System.out.println("Canción con ID " + id + " eliminada.");
	}

	public void verCanciones() {
		System.out.println("Lista de canciones:");
		for (Cancion c : canciones) {
			System.out.println(c);
		}
	}

	/**
	 * Actualiza el título y la duración de una canción.
	 * Se corrige el getter de 'getIdCancion' a 'getId' y el setter de 'setDuracion' a 'setDuracionSegundos'.
	 * @param id ID de la canción a actualizar.
	 * @param nuevoTitulo Nuevo título.
	 * @param nuevaDuracion Nueva duración.
	 */
	public void actualizarCancion(int id, String nuevoTitulo, double nuevaDuracion) {
		for (Cancion c : canciones) {
			if (c.getId() == id) { // FIX: Usar getId()
				c.setTitulo(nuevoTitulo);
				c.setDuracionSegundos(nuevaDuracion); // FIX: Usar setDuracionSegundos()
				System.out.println("Canción actualizada: " + c);
				return;
			}
		}
		System.out.println("Canción con ID " + id + " no encontrada.");
	}

	/**
	 * Busca una canción por su ID.
	 * Se corrige el getter de 'getIdCancion' a 'getId'.
	 * @param id ID de la canción a buscar.
	 * @return Cancion encontrada o null.
	 */
	public Cancion buscarCancion(int id) {
		for (Cancion c : canciones) {
			if (c.getId() == id) { // FIX: Usar getId()
				return c;
			}
		}
		return null;
	}
	
	// Método adicional para obtener todas las canciones (útil para Managers)
	public List<Cancion> obtenerTodasLasCanciones() {
        return new ArrayList<>(canciones);
    }
}
