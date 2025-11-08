package co.edu.poli.dataBase;

public class Cancion {
	private int id;
	private String titulo;
	private String artista;
	private double duracionSegundos;

	public Cancion() {
	}

	public Cancion(int id, String titulo, String artista, double duracionSegundos) {
		this.id = id;
		this.titulo = titulo;
		this.artista = artista;
		this.duracionSegundos = duracionSegundos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public double getDuracionSegundos() {
		return duracionSegundos;
	}

	public void setDuracionSegundos(double duracionSegundos) {
		this.duracionSegundos = duracionSegundos;
	}

	// FIX (Error 2): Se añade el método setDuracion(double) para corregir la
	// llamada
	// en Main.java y CancionDao.java
	public void setDuracion(double duracion) {
		this.duracionSegundos = duracion;
	}
	// FIN FIX

	@Override
	public String toString() {
		return "Cancion [id=" + id + ", titulo=" + titulo + ", artista=" + artista + ", duracion=" + duracionSegundos
				+ "s]";
	}
}
