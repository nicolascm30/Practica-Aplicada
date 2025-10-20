package co.edu.poli.dataBase;

import java.util.Date;

public class Mp3 extends Cancion {
	private Date tamanoDelArchivo;
	private String clasificacionDeSonido;

	public Mp3() {
	}

	public void calcularReproducciones(Usuario ganador) {
		// TODO: implementar lógica
	}

	// Getters y Setters
	public Date getTamanoDelArchivo() {
		return tamanoDelArchivo;
	}

	public void setTamanoDelArchivo(Date tamanoDelArchivo) {
		this.tamanoDelArchivo = tamanoDelArchivo;
	}

	public String getClasificacionDeSonido() {
		return clasificacionDeSonido;
	}

	public void setClasificacionDeSonido(String clasificacionDeSonido) {
		this.clasificacionDeSonido = clasificacionDeSonido;
	}
}
