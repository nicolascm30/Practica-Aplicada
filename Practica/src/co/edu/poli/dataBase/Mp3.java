package co.edu.poli.dataBase;

import java.util.List;

public class Mp3 {
    private int idMp3;
    private String formato;
    private double tamanioMB;
    private Cancion cancion;

    public Mp3() {
    }

    public Mp3(int idMp3, String formato, double tamanioMB, Cancion cancion) {
        this.idMp3 = idMp3;
        this.formato = formato;
        this.tamanioMB = tamanioMB;
        this.cancion = cancion;
    }

    public int getIdMp3() {
        return idMp3;
    }

    public void setIdMp3(int idMp3) {
        this.idMp3 = idMp3;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public double getTamanioMB() {
        return tamanioMB;
    }

    public void setTamanioMB(double tamanioMB) {
        this.tamanioMB = tamanioMB;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    @Override
    public String toString() {
        return "Mp3 [idMp3=" + idMp3 + ", formato=" + formato + ", tamanioMB=" + tamanioMB + ", cancion=" + cancion.getTitulo() + "]";
    }

    public static final List<Mp3> Mp3 = List.of(
        new Mp3(1, "mp3", 5.2, new Cancion(1, "Blinding Lights", "The Weeknd", 3.2)),
        new Mp3(2, "mp3", 4.8, new Cancion(2, "As It Was", "Harry Styles", 2.8)),
        new Mp3(3, "mp3", 6.0, new Cancion(3, "Levitating", "Dua Lipa", 3.4)),
        new Mp3(4, "mp3", 5.0, new Cancion(4, "Bad Guy", "Billie Eilish", 3.1)),
        new Mp3(5, "mp3", 6.5, new Cancion(5, "Shape of You", "Ed Sheeran", 4.0))
    );
}
