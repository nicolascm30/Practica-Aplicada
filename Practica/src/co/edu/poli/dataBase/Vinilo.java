package co.edu.poli.dataBase;


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


	
	// FIX: Implementación del método setEstado
	public void setEstado(String nuevoEstado) {
		this.estado = nuevoEstado;
	}
}
