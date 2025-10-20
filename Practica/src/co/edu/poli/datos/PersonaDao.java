package co.edu.poli.datos;

import java.util.ArrayList;
import java.util.List;
import co.edu.poli.dataBase.Persona;

public class PersonaDao {

	// NOTA: Esta lista solo se usa para simular un repositorio general de Personas.
	// Las subclases (Usuario, Administrador, Proveedor) tienen sus propios DAOs y listas.
	private List<Persona> personas = new ArrayList<>();


    public PersonaDao() {
    }

    public void crearPersona(Persona nueva) {
        personas.add(nueva);
        System.out.println("Persona creada: " + nueva);
    }

    public void eliminarPersona(int cedula) {
        personas.removeIf(p -> p.getCedula() == cedula);
        System.out.println("Persona con cédula " + cedula + " eliminada.");
    }

    public void verPersonas() {
        System.out.println("Lista de personas:");
        for (Persona p : personas) {
            System.out.println(p);
        }
    }

    public void actualizarPersona(int cedula, String nuevoNombre, String nuevoCorreo) {
        for (Persona p : personas) {
            if (p.getCedula() == cedula) {
                p.setNombre(nuevoNombre);
                p.setCorreo(nuevoCorreo);
                System.out.println("Persona actualizada: " + p);
                return;
            }
        }
        System.out.println("Persona con cédula " + cedula + " no encontrada.");
    }

    public Persona buscarPersona(int cedula) {
        for (Persona p : personas) {
            if (p.getCedula() == cedula) {
                return p;
            }
        }
        return null;
    }
    
    // Método adicional para obtener todas las Personas (útil para Managers)
	public List<Persona> obtenerTodasLasPersonas() {
        return new ArrayList<>(personas);
    }
}
