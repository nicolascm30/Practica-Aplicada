package co.edu.poli.dataBase;

import java.util.List;

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
        return "Billetera [idBilletera=" + idBilletera + ", usuario=" + usuario.getNombre() + 
               ", saldoActual=" + saldoActual + ", estado=" + estado + "]";
    }

    public static final List<Billetera> Billetera = List.of(
        new Billetera(1, new Usuario(1010, "Carlos Pérez", "carlos.perez@gmail.com"), 250000.0, "Activa"),
        new Billetera(2, new Usuario(1011, "Laura Gómez", "laura.gomez@gmail.com"), 152000.5, "Activa"),
        new Billetera(3, new Usuario(1012, "Andrés Rojas", "andres.rojas@hotmail.com"), 0.0, "Bloqueada"),
        new Billetera(4, new Usuario(1013, "Valentina Torres", "valentina.torres@gmail.com"), 34000.0, "Activa"),
        new Billetera(5, new Usuario(1014, "Santiago Ruiz", "santiago.ruiz@yahoo.com"), 98000.0, "Suspendida")
    );
}
