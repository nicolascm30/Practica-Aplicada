package co.edu.poli.dataBase;

public class Billetera {
	private int idBilletera;
	private Usuario usuario;
	private double saldoActual;
	private String estado;
//llave 333
	public Billetera() {
	}

	public double consultarSaldo() {
		return saldoActual;
	}

	public void verTransaccion(Transaccion tr) {
		// TODO: mostrar detalle de transacción
	}

	// Getters y Setters
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
}