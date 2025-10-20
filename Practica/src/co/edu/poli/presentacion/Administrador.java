package co.edu.poli.presentacion;

import co.edu.poli.dataBase.Persona;
import co.edu.poli.dataBase.Cancion;
import co.edu.poli.dataBase.Mp3;

public class Administrador extends Persona {

    private int idAdmin;
    private int rango;

    public Administrador() {}

    // Métodos (placeholders)
    public void cancelarPedido(Cancion apuesta) {
        // TODO: implementar lógica para cancelar pedido
    }

    public void gestionDeTienda(Mp3 rifa) {
        // TODO: implementar gestión de la tienda
    }

    public void auditoriaYReportes(Cancion evento) {
        // TODO: implementar auditoría y generación de reportes
    }

    public void realizarReembolso(Cancion reembolso) {
        // TODO: implementar lógica de reembolso
    }

    public void gestionDeProveedores() {
        // TODO: implementar gestión de proveedores
    }

    // Getters y Setters
    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getRango() {
        return rango;
    }
    
    public void setRango(int rango) {
        this.rango = rango;
    }
}