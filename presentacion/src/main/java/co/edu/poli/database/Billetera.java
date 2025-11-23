package co.edu.poli.database;



public class Billetera {
	private int idBilletera;
	private Usuario usuario;
	private double saldoActual;
	private String estado;
	


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
