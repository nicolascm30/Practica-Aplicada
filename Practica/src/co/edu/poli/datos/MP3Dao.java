package co.edu.poli.datos;

import java.util.List;
import java.util.ArrayList;
import co.edu.poli.dataBase.Mp3;

public class MP3Dao {

	// Inicializamos con la lista estática
	private List<Mp3> mp3List = new ArrayList<>(Mp3.Mp3);

	public MP3Dao() {
	}

	public void crearMp3(Mp3 nuevo) {
		mp3List.add(nuevo);
		System.out.println("MP3 agregado: " + nuevo);
	}

	public void eliminarMp3(int id) {
		mp3List.removeIf(m -> m.getIdMp3() == id);
		System.out.println("MP3 con ID " + id + " eliminado.");
	}

	public void verMp3() {
		System.out.println("Lista de archivos MP3:");
		for (Mp3 m : mp3List) {
			System.out.println(m);
		}
	}

	/**
	 * Actualiza el título de la canción (dentro de Mp3) y el tamaño del archivo MP3.
	 * Se corrige la lógica de actualización del título al usar la composición (getCancion().setTitulo()).
	 * @param id ID del MP3 a actualizar.
	 * @param nuevoTitulo Nuevo título de la canción.
	 * @param nuevoTamanoMB Nuevo tamaño en MB.
	 */
	public void actualizarMp3(int id, String nuevoTitulo, double nuevoTamanoMB) { // Mejor nombre de variable
		for (Mp3 m : mp3List) {
			if (m.getIdMp3() == id) {
				// FIX: El título está en el objeto Cancion compuesto
				m.getCancion().setTitulo(nuevoTitulo); 
				m.setTamanioMB(nuevoTamanoMB);
				System.out.println("MP3 actualizado: " + m);
				return;
			}
		}
		System.out.println("MP3 con ID " + id + " no encontrado.");
	}

	public Mp3 buscarMp3(int id) {
		for (Mp3 m : mp3List) {
			if (m.getIdMp3() == id) {
				return m;
			}
		}
		return null;
	}
	
	// Método adicional para obtener todos los MP3 (útil para Managers)
	public List<Mp3> obtenerTodosLosMp3() {
        return new ArrayList<>(mp3List);
    }
}
