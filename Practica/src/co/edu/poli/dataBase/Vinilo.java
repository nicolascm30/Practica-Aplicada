package co.edu.poli.dataBase;

 public class Vinilo extends Cancion {
	private int cantidadDeCanciones;
	private String disquera;
	private String tamano;
	private int stock;
	private String color;

	public Vinilo() {
	}

	// Getters y Setters
	public int getCantidadDeCanciones() {
		return cantidadDeCanciones;
	}

	public void setCantidadDeCanciones(int cantidadDeCanciones) {
		this.cantidadDeCanciones = cantidadDeCanciones;
	}

	public String getDisquera() {
		return disquera;
	}

	public void setDisquera(String disquera) {
		this.disquera = disquera;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}