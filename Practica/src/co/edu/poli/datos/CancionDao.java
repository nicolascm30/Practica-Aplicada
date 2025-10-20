package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Cancion;
import co.edu.poli.dataBase.Mp3;
import co.edu.poli.dataBase.Vinilo;

public class CancionDao {

	private List<Cancion> canciones = new ArrayList<>();
    
    // 💡 Método auxiliar para formatear segundos a MM:SS
    private String formatDuration(double totalSegundos) {
        // Redondea a segundos completos 
        int segundos = (int) Math.round(totalSegundos); 
        if (segundos < 0) return "0:00"; 
        
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;
        
        // Formato: %d para minutos, %02d para segundos (con cero inicial si es menor a 10)
        return String.format("%d:%02d", minutos, segundosRestantes);
    }
    
    // Método para obtener el siguiente ID (busca el máximo ID existente y suma 1)
    public int getNextId() {
        int maxId = 0;
        for (Cancion c : canciones) {
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        return maxId + 1;
    }

	public CancionDao() {
        // INICIALIZACIÓN DE DATOS

        // Carga MP3s (ID < 100)
        try {
            for (Mp3 mp3 : Mp3.Mp3) {
                canciones.add(mp3.getCancion());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar MP3s de simulación: " + e.getMessage());
        }

        // Carga Vinilos (ID >= 100)
        try {
            for (Vinilo vinilo : Vinilo.Vinilo) {
                int viniloId = vinilo.getIdVinilo() + 100;
                
                // OBTENER LA DURACIÓN DESDE EL OBJETO VINILO
                Cancion cancionVinilo = new Cancion(
                    viniloId, 
                    vinilo.getTitulo(),
                    vinilo.getArtista(),
                    vinilo.getDuracionSegundos() // Usa la duración corregida del Vinilo
                );
                canciones.add(cancionVinilo);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar Vinilos de simulación: " + e.getMessage());
        }
	}

	public void crearCancion(Cancion nueva) {
		canciones.add(nueva);
		// Usamos el formato correcto para el mensaje de salida
		String duracionFormateada = formatDuration(nueva.getDuracionSegundos());
		System.out.println("Canción agregada: Cancion [id=" + nueva.getId() + ", titulo=" + nueva.getTitulo() + ", artista=" + nueva.getArtista() + ", duracion=" + duracionFormateada + "]");
	}


	public void verCanciones() {
		System.out.println("\n--- LISTA DE CANCIONES DISPONIBLES ---");
		// Encabezado para la tabla
		System.out.println("ID   | Título                         | Artista            | Duración | Tipo");
		System.out.println("-----|--------------------------------|--------------------|----------|-----");
        
		for (Cancion c : canciones) {
			String tipo = c.getId() < 100 ? "MP3" : "Vinilo";
			
			// Usar la función de formato MM:SS
			String duracionFormateada = formatDuration(c.getDuracionSegundos());
			
			// Formato de impresión con ancho ajustado
			String formato = String.format(
			    "%-4d | %-30s | %-20s | %-8s | %s",
                c.getId(), 
                c.getTitulo(), 
                c.getArtista(), 
                duracionFormateada, 
                tipo);
                
			System.out.println(formato);
		}
        System.out.println("--------------------------------------------------------------------------");
	}

	public Cancion buscarCancion(int id) {
		for (Cancion c : canciones) {
			if (c.getId() == id) { 
				return c;
			}
		}
		return null;
	}

    /**
	 * Elimina una canción por su ID.
	 * @param id ID de la canción a eliminar.
	 */
	public void eliminarCancion(int id) {
		boolean removed = canciones.removeIf(c -> c.getId() == id); 
        if (removed) {
		    System.out.println("Canción con ID " + id + " eliminada.");
        } else {
            System.out.println("Canción con ID " + id + " no encontrada.");
        }
	}

	/**
	 * Actualiza el título y la duración de una canción.
	 * @param id ID de la canción a actualizar.
	 * @param nuevoTitulo Nuevo título.
	 * @param nuevaDuracionSegundos Nueva duración en segundos.
	 */
	public void actualizarCancion(int id, String nuevoTitulo, double nuevaDuracionSegundos) { 
		for (Cancion c : canciones) {
			if (c.getId() == id) { 
				c.setTitulo(nuevoTitulo);
				c.setDuracionSegundos(nuevaDuracionSegundos); 
				String duracionFormateada = formatDuration(nuevaDuracionSegundos);
				System.out.println("Canción actualizada: Cancion [id=" + c.getId() + ", titulo=" + c.getTitulo() + ", artista=" + c.getArtista() + ", duracion=" + duracionFormateada + "]");
				return;
			}
		}
		System.out.println("Canción con ID " + id + " no encontrada.");
	}
}