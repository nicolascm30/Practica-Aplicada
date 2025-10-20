package co.edu.poli.dataBase;

import java.util.List;

public class Vinilo {
	private int idVinilo;
	private String titulo;
	private String artista;
	private double precio;
	private Proveedor proveedor;
	// FIX: Se agrega el campo 'estado' para que el ViniloDao pueda usar setEstado
	private String estado;
	private double duracionSegundos;

	public Vinilo() {
	}

	public Vinilo(int idVinilo, String titulo, String artista, double precio, Proveedor proveedor, String estado,
			double duracionSegundos) {
		this.idVinilo = idVinilo;
		this.titulo = titulo;
		this.artista = artista;
		this.precio = precio;
		this.proveedor = proveedor;
		this.estado = estado;
		this.duracionSegundos = duracionSegundos; // Inicializar duración
	}

	public Vinilo(int idVinilo, String titulo, String artista, double precio, Proveedor proveedor) {
		this(idVinilo, titulo, artista, precio, proveedor, "Disponible", 0.0); // Duración por defecto 0.0
	}

	public int getIdVinilo() {
		return idVinilo;
	}

	public double getDuracionSegundos() {
		return duracionSegundos;
	}

	public void setIdVinilo(int idVinilo) {
		this.idVinilo = idVinilo;
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

	public void setDuracionSegundos(double duracionSegundos) {
		this.duracionSegundos = duracionSegundos;
	}

	public void setArtista(String artista) {
		this.artista = artista;
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

	@Override
	public String toString() {
		return "Vinilo [idVinilo=" + idVinilo + ", titulo=" + titulo + ", artista=" + artista + ", precio=" + precio
				+ ", estado=" + estado + ", proveedor=" + proveedor.getNombreEmpresa() + "]";
	}

	// FIX: Se actualizan los datos para incluir el estado
	public static final List<Vinilo> Vinilo = List.of(
			// Vinilo(id, titulo, artista, precio, proveedor, estado, duracionSegundos)
			new Vinilo(1, "Thriller", "Michael Jackson", 250000.0,
					new Proveedor(1, "Sony Music", "Carlos Díaz", "contacto@sonymusic.com"), "Disponible", 2530.0), // 42:10
			new Vinilo(2, "Abbey Road", "The Beatles", 220000.0,
					new Proveedor(2, "Universal Records", "María López", "ventas@universal.com"), "Disponible", 2800.0), // 46:40
			new Vinilo(3, "Future Nostalgia", "Dua Lipa", 200000.0,
					new Proveedor(4, "IndieSound", "Camila Pérez", "camila@indiesound.co"), "Agotado", 2220.0), // 37:00
			new Vinilo(4, "Divide", "Ed Sheeran", 180000.0,
					new Proveedor(3, "Warner Bros", "Javier Soto", "soporte@warner.com"), "Disponible", 3600.0), // 60:00
			new Vinilo(5, "After Hours", "The Weeknd", 210000.0,
					new Proveedor(1, "Sony Music", "Carlos Díaz", "contacto@sonymusic.com"), "Disponible", 3420.0) // 57:00
	);

	// FIX: Implementación del método setEstado
	public void setEstado(String nuevoEstado) {
		this.estado = nuevoEstado;
	}
}
