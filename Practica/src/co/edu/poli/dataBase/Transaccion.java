package co.edu.poli.dataBase;

import java.util.List;

public class Transaccion {
    private int idTransaccion;
    private Billetera billeteraOrigen;
    private Billetera billeteraDestino;
    private double monto;
    private String fecha;

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, Billetera billeteraOrigen, Billetera billeteraDestino, double monto, String fecha) {
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
        return "Transaccion [idTransaccion=" + idTransaccion + ", origen=" + billeteraOrigen.getIdBilletera() +
               ", destino=" + billeteraDestino.getIdBilletera() + ", monto=" + monto + ", fecha=" + fecha + "]";
    }

    public static final List<Transaccion> Transaccion = List.of(
        new Transaccion(1, new Billetera(1, new Usuario(1010, "Carlos Pérez", "carlos.perez@gmail.com"), 250000, "Activa"),
                           new Billetera(2, new Usuario(1011, "Laura Gómez", "laura.gomez@gmail.com"), 152000, "Activa"),
                           30000.0, "2025-09-21"),
        new Transaccion(2, new Billetera(2, new Usuario(1011, "Laura Gómez", "laura.gomez@gmail.com"), 152000, "Activa"),
                           new Billetera(4, new Usuario(1013, "Valentina Torres", "valentina.torres@gmail.com"), 34000, "Activa"),
                           15000.0, "2025-10-03"),
        new Transaccion(3, new Billetera(5, new Usuario(1014, "Santiago Ruiz", "santiago.ruiz@yahoo.com"), 98000, "Suspendida"),
                           new Billetera(3, new Usuario(1012, "Andrés Rojas", "andres.rojas@hotmail.com"), 0, "Bloqueada"),
                           20000.0, "2025-09-10"),
        new Transaccion(4, new Billetera(4, new Usuario(1013, "Valentina Torres", "valentina.torres@gmail.com"), 34000, "Activa"),
                           new Billetera(1, new Usuario(1010, "Carlos Pérez", "carlos.perez@gmail.com"), 250000, "Activa"),
                           50000.0, "2025-10-15")
    );
}
