package co.edu.poli.database;

import java.util.List;

public class Vinilo {
	private int idVinilo;
	// ❌ ANTES: private String titulo; private String artista; private double
	// duracionSegundos;
	// ✅ FIX 1: Usamos composición para la canción
	private Cancion cancion;
	private double precio;
	private Proveedor proveedor;
	private String estado;

	public Vinilo() {
	}

	/**
	 * ✅ FIX 2: Constructor CORREGIDO (5 argumentos: Vinilo(int, Cancion, double,
	 * Proveedor, String)) Se eliminan los argumentos duplicados (titulo, artista,
	 * duracionSegundos).
	 */
	public Vinilo(int idVinilo, Cancion cancion, double precio, Proveedor proveedor, String estado) {
		this.idVinilo = idVinilo;
		this.cancion = cancion;
		this.precio = precio;
		this.proveedor = proveedor;
		this.estado = estado;
	}

	// El constructor anterior de 7 argumentos ya no es necesario y se elimina.

	// Métodos auxiliares para la clave foránea y el DAO
	public int getIdCancion() {
		return cancion.getId();
	}

	// Getters y Setters (actualizados para Cancion)
	public int getIdVinilo() {
		return idVinilo;
	}

	public void setIdVinilo(int idVinilo) {
		this.idVinilo = idVinilo;
	}

	public Cancion getCancion() { // Nuevo getter para el objeto Cancion
		return cancion;
	}

	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		// Se accede a título y artista a través del objeto cancion
		return "Vinilo [idVinilo=" + idVinilo + ", titulo=" + cancion.getTitulo() + ", artista=" + cancion.getArtista()
				+ ", precio=" + precio + ", estado=" + estado + ", proveedor=" + proveedor.getNombreEmpresa() + "]";
	}

	// ✅ FIX 3: Actualizar la lista estática (Línea 46 en el DAO)
	// Se cambia la forma de inicializar a 5 argumentos (el segundo es un objeto
	// Cancion)
	public static final List<Vinilo> Vinilo = List.of(
			// Vinilo(id, Cancion(id, titulo, artista, duracionSegundos), precio, Proveedor,
			// estado)
			new Vinilo(1, new Cancion(1, "Thriller", "Michael Jackson", 2530.0), // Objeto Cancion
					250000.0, new Proveedor(1, "Sony Music", "Carlos Díaz", "contacto@sonymusic.com"), "Disponible"),
			new Vinilo(2, new Cancion(2, "Abbey Road", "The Beatles", 2800.0), 220000.0,
					new Proveedor(2, "Universal Records", "María López", "ventas@universal.com"), "Disponible"),
			new Vinilo(3, new Cancion(3, "Future Nostalgia", "Dua Lipa", 2220.0), 200000.0,
					new Proveedor(4, "IndieSound", "Camila Pérez", "camila@indiesound.co"), "Agotado"));
}