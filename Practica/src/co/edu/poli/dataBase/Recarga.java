package co.edu.poli.dataBase;

public class Recarga {
	private int idRecarga;
	private Billetera billetera;
	private double monto;
	private String fecha;

	public Recarga() {
	}

	public Recarga(int idRecarga, Billetera billetera, double monto, String fecha) {
		this.idRecarga = idRecarga;
		this.billetera = billetera;
		this.monto = monto;
		this.fecha = fecha;
	}

	public int getIdRecarga() {
		return idRecarga;
	}

	public void setIdRecarga(int idRecarga) {
		this.idRecarga = idRecarga;
	}

	public Billetera getBilletera() {
		return billetera;
	}

	public void setBilletera(Billetera billetera) {
		this.billetera = billetera;
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
		return "Recarga [idRecarga=" + idRecarga + ", billetera=" + billetera.getIdBilletera() + ", monto=" + monto
				+ ", fecha=" + fecha + "]";
	}



	// FIX: Implementación del método setValor
	public void setValor(double nuevoValor) {
		this.monto = nuevoValor;
	}
}
