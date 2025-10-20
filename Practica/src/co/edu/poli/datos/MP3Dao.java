package co.edu.poli.datos;

import java.util.List;
import java.util.ArrayList;
import co.edu.poli.dataBase.Mp3;

public class MP3Dao {

    private List<Mp3> mp3List = new ArrayList<>();

    public MP3Dao() {}

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

    public void actualizarMp3(int id, String nuevoTitulo, double nuevoTamano) {
        for (Mp3 m : mp3List) {
            if (m.getIdMp3() == id) {
                m.setTitulo(nuevoTitulo);
                m.setTamanioMB(nuevoTamano);
                System.out.println(" MP3 actualizado: " + m);
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
}
