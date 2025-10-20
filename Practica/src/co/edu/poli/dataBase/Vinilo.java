package co.edu.poli.dataBase;

import java.util.List;

public class Vinilo {
    private int idVinilo;
    private String titulo;
    private String artista;
    private double precio;
    private Proveedor proveedor;

    public Vinilo() {
    }

    public Vinilo(int idVinilo, String titulo, String artista, double precio, Proveedor proveedor) {
        this.idVinilo = idVinilo;
        this.titulo = titulo;
        this.artista = artista;
        this.precio = precio;
        this.proveedor = proveedor;
    }

    public int getIdVinilo() {
        return idVinilo;
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

    @Override
    public String toString() {
        return "Vinilo [idVinilo=" + idVinilo + ", titulo=" + titulo + ", artista=" + artista + ", precio=" + precio +
               ", proveedor=" + proveedor.getNombreEmpresa() + "]";
    }

    public static final List<Vinilo> Vinilo = List.of(
        new Vinilo(1, "Thriller", "Michael Jackson", 250000.0, new Proveedor(1, "Sony Music", "Carlos Díaz", "contacto@sonymusic.com")),
        new Vinilo(2, "Abbey Road", "The Beatles", 220000.0, new Proveedor(2, "Universal Records", "María López", "ventas@universal.com")),
        new Vinilo(3, "Future Nostalgia", "Dua Lipa", 200000.0, new Proveedor(4, "IndieSound", "Camila Pérez", "camila@indiesound.co")),
        new Vinilo(4, "Divide", "Ed Sheeran", 210000.0, new Proveedor(5, "LatinBeats", "Juan Torres", "juan.torres@latinbeats.com")),
        new Vinilo(5, "After Hours", "The Weeknd", 230000.0, new Proveedor(3, "Warner Music", "Andrés Rojas", "andres.rojas@warner.com"))
    );

	public void setEstado(String nuevoEstado) {
		// TODO Auto-generated method stub
		
	}
}
