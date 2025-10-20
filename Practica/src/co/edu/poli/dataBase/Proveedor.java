package co.edu.poli.dataBase;

import java.util.List;

public class Proveedor {
    private int idProveedor;
    private String nombreEmpresa;
    private String contacto;
    private String correo;

    public Proveedor() {
    }

    public Proveedor(int idProveedor, String nombreEmpresa, String contacto, String correo) {
        this.idProveedor = idProveedor;
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.correo = correo;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Proveedor [idProveedor=" + idProveedor + ", nombreEmpresa=" + nombreEmpresa + ", contacto=" + contacto + ", correo=" + correo + "]";
    }

    public static final List<Proveedor> Proveedor = List.of(
        new Proveedor(1, "Sony Music", "Carlos Díaz", "contacto@sonymusic.com"),
        new Proveedor(2, "Universal Records", "María López", "ventas@universal.com"),
        new Proveedor(3, "Warner Music", "Andrés Rojas", "andres.rojas@warner.com"),
        new Proveedor(4, "IndieSound", "Camila Pérez", "camila@indiesound.co"),
        new Proveedor(5, "LatinBeats", "Juan Torres", "juan.torres@latinbeats.com")
    );
}
