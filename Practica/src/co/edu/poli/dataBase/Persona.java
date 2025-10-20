package co.edu.poli.dataBase;

import java.util.List;

public class Persona {
    private int cedula;
    private String nombre;
    private String correo;


    public Persona() {
    }


    public Persona(int cedula, String nombre, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
    }

    public boolean iniciarSesion() {
      
        return false;
    }

  
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Persona [cedula=" + cedula + ", nombre=" + nombre + ", correo=" + correo + "]";
    }


    public static final List<Persona> PERSONAS = List.of(
        new Persona(1001234567, "Laura Gómez", "laura.gomez@gmail.com"),
        new Persona(1002345678, "Andrés Martínez", "andres.martinez@hotmail.com"),
        new Persona(1003456789, "Valentina López", "valentina.lopez@yahoo.com"),
        new Persona(1004567890, "Juan Torres", "juan.torres@outlook.com"),
        new Persona(1005678901, "Camila Rodríguez", "camila.rodriguez@gmail.com"),
        new Persona(1006789012, "Sebastián Castro", "sebastian.castro@gmail.com"),
        new Persona(1007890123, "Natalia Hernández", "natalia.hernandez@icloud.com"),
        new Persona(1008901234, "Daniel Pérez", "daniel.perez@hotmail.com"),
        new Persona(1009012345, "Mariana Jiménez", "mariana.jimenez@gmail.com"),
        new Persona(1010123456, "Felipe Rojas", "felipe.rojas@yahoo.com")
    );
}


