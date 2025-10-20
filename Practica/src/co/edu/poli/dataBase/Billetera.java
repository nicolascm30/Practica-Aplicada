package co.edu.poli.dataBase;

import java.util.List;

public class Billetera {
	private int idBilletera;
	private Usuario usuario;
	private double saldoActual;
	private String estado;
	
	// Lista estática de simulación de datos
	public static final List<Billetera> Billetera = List.of(
			new Billetera(1, Usuario.Usuario.get(0), 250000.0, "Activa"), // Carlos Pérez
			new Billetera(2, Usuario.Usuario.get(1), 100000.0, "Activa"), // Laura Gómez
			new Billetera(3, Usuario.Usuario.get(2), 0.0, "Activa"), // Andrés Rojas (para prueba de saldo insuficiente)
			new Billetera(4, Usuario.Usuario.get(3), 50000.0, "Activa"), // Valentina Torres
			new Billetera(5, Usuario.Usuario.get(4), 120000.0, "Bloqueada")); // Santiago Ruiz (para prueba de bloqueo)

	public Billetera() {
	}

	public Billetera(int idBilletera, Usuario usuario, double saldoActual, String estado) {
		this.idBilletera = idBilletera;
		this.usuario = usuario;
		this.saldoActual = saldoActual;
		this.estado = estado;
	}

	public int getIdBilletera() {
		return idBilletera;
	}

	public void setIdBilletera(int idBilletera) {
		this.idBilletera = idBilletera;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public double getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(double saldoActual) {
		this.saldoActual = saldoActual;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Billetera [idBilletera=" + idBilletera + ", usuario=" + usuario.getNombre() + ", saldoActual=" + saldoActual + ", estado=" + estado + "]";
	}
}
