package co.edu.poli.dataBase;

import java.util.List;
// Se importa Billetera para poder usar la lista estática Billetera.Billetera

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

	// FIX (Error 3): Se añade la lista estática de simulación de datos para que TransaccionDao pueda inicializarse
	public static final List<Transaccion> Transaccion = List.of(
			// Usamos la lista estática de Billeteras para simular transacciones
			new Transaccion(1, Billetera.Billetera.get(0), Billetera.Billetera.get(1), 10000.0, "2025-10-15"), // Carlos a Laura
			new Transaccion(2, Billetera.Billetera.get(1), Billetera.Billetera.get(3), 5000.0, "2025-10-16"), // Laura a Valentina
			new Transaccion(3, Billetera.Billetera.get(3), Billetera.Billetera.get(0), 20000.0, "2025-10-17")  // Valentina a Carlos
	);
	// FIN FIX
}
