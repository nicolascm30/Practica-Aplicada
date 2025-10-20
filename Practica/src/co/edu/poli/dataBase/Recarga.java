package co.edu.poli.dataBase;

import java.util.List;

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
        return "Recarga [idRecarga=" + idRecarga + ", billetera=" + billetera.getIdBilletera() +
               ", monto=" + monto + ", fecha=" + fecha + "]";
    }

    public static final List<Recarga> Recarga = List.of(
        new Recarga(1, new Billetera(1, new Usuario(1010, "Carlos Pérez", "carlos.perez@gmail.com"), 250000.0, "Activa"), 50000.0, "2025-10-01"),
        new Recarga(2, new Billetera(2, new Usuario(1011, "Laura Gómez", "laura.gomez@gmail.com"), 152000.5, "Activa"), 30000.0, "2025-09-29"),
        new Recarga(3, new Billetera(3, new Usuario(1012, "Andrés Rojas", "andres.rojas@hotmail.com"), 0.0, "Bloqueada"), 10000.0, "2025-08-15"),
        new Recarga(4, new Billetera(4, new Usuario(1013, "Valentina Torres", "valentina.torres@gmail.com"), 34000.0, "Activa"), 75000.0, "2025-10-10"),
        new Recarga(5, new Billetera(5, new Usuario(1014, "Santiago Ruiz", "santiago.ruiz@yahoo.com"), 98000.0, "Suspendida"), 20000.0, "2025-10-17")
    );

	public void setValor(double nuevoValor) {
		// TODO Auto-generated method stub
		
	}
}
