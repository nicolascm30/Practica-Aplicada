package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Cancion;

public class CancionDao {

    private List<Cancion> canciones = new ArrayList<>(Cancion.Cancion);

    public CancionDao() {
    }

    public void crearCancion(Cancion nueva) {
        canciones.add(nueva);
        System.out.println("Canción agregada: " + nueva);
    }

    public void eliminarCancion(int id) {
        canciones.removeIf(c -> c.getIdCancion() == id);
        System.out.println("Canción con ID " + id + " eliminada.");
    }

    public void verCanciones() {
        System.out.println("Lista de canciones:");
        for (Cancion c : canciones) {
            System.out.println(c);
        }
    }

    public void actualizarCancion(int id, String nuevoTitulo, double nuevaDuracion) {
        for (Cancion c : canciones) {
            if (c.getIdCancion() == id) {
                c.setTitulo(nuevoTitulo);
                c.setDuracion(nuevaDuracion);
                System.out.println("Canción actualizada: " + c);
                return;
            }
        }
        System.out.println("Canción con ID " + id + " no encontrada.");
    }

    public Cancion buscarCancion(int id) {
        for (Cancion c : canciones) {
            if (c.getIdCancion() == id) {
                return c;
            }
        }
        return null;
    }
}
