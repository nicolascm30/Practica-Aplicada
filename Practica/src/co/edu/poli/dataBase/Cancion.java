package co.edu.poli.dataBase;

import java.util.List;

public class Cancion {
    private int idCancion;
    private String titulo;
    private String artista;
    private double duracion;

    public Cancion() {
    }

    public Cancion(int idCancion, String titulo, String artista, double duracion) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Cancion [idCancion=" + idCancion + ", titulo=" + titulo + ", artista=" + artista + ", duracion=" + duracion + "]";
    }

    public static final List<Cancion> Cancion = List.of(
        new Cancion(1, "Blinding Lights", "The Weeknd", 3.2),
        new Cancion(2, "As It Was", "Harry Styles", 2.8),
        new Cancion(3, "Levitating", "Dua Lipa", 3.4),
        new Cancion(4, "Bad Guy", "Billie Eilish", 3.1),
        new Cancion(5, "Shape of You", "Ed Sheeran", 4.0)
    );
}

