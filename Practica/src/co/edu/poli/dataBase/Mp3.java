package co.edu.poli.dataBase;


public class Mp3 {

	private int idMp3;
	private String formato;
	private double tamanioMB;
	private double precio;
	private Cancion cancion;

	public Mp3() {
	}

	public Mp3(int idMp3, String formato, double tamanioMB, double precio, Cancion cancion) {
		this.idMp3 = idMp3;
		this.formato = formato;
		this.tamanioMB = tamanioMB;
		this.precio = precio;
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Cancion getCancion() {
		return cancion;
	}

	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}

	@Override
	public String toString() {
		return "Mp3 [idMp3=" + idMp3 + ", formato=" + formato + ", tamanioMB=" + tamanioMB + " MB, precio=$" + precio
				+ ", cancion=" + cancion.getTitulo() + "]";
	}

	

	public void setTitulo(String nuevoTitulo) {
		if (this.cancion != null) {
			this.cancion.setTitulo(nuevoTitulo);
		}
	}
}
