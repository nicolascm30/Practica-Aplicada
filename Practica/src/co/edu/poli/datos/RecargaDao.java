package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Recarga;

public class RecargaDao {

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

    public void actualizarRecarga(int id, double nuevoValor) {
        for (Recarga r : recargas) {
            if (r.getIdRecarga() == id) {
                r.setValor(nuevoValor);
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
}
