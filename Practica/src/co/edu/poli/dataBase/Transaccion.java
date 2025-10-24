package co.edu.poli.dataBase;



public class Transaccion {
	private int idTransaccion;
	private Billetera billeteraOrigen;
	private Billetera billeteraDestino;
	private double monto;
	private String fecha;

	public Transaccion() {
	}

	public Transaccion(int idTransaccion, Billetera billeteraOrigen, Billetera billeteraDestino, double monto,
			String fecha) {
		this.idTransaccion = idTransaccion;
		this.billeteraOrigen = billeteraOrigen;
		this.billeteraDestino = billeteraDestino;
		this.monto = monto;
		this.fecha = fecha;
	}

	public int getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(int idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public Billetera getBilleteraOrigen() {
		return billeteraOrigen;
	}

	public void setBilleteraOrigen(Billetera billeteraOrigen) {
		this.billeteraOrigen = billeteraOrigen;
	}

	public Billetera getBilleteraDestino() {
		return billeteraDestino;
	}

	public void setBilleteraDestino(Billetera billeteraDestino) {
		this.billeteraDestino = billeteraDestino;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Transaccion [idTransaccion=" + idTransaccion + ", origen=" + billeteraOrigen.getIdBilletera()
				+ ", destino=" + billeteraDestino.getIdBilletera() + ", monto=" + monto + ", fecha=" + fecha + "]";
	}

	
}
