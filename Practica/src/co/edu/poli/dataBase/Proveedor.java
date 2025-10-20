package co.edu.poli.dataBase;

/**
 * Entidad Proveedor, extiende de Persona y añade el nombre de la empresa.
 */
public class Proveedor extends Persona {
	private int idProveedor;
    private String nombreEmpresa; // <- CORRECCIÓN: Campo necesario para el Vinilo.toString()

	public Proveedor() {}
	
	/**
	 * Constructor usado en la lista estática de Vinilo: 
     * (idProveedor, nombreEmpresa, contacto, correo)
	 */
	public Proveedor(int idProveedor, String nombreEmpresa, String contacto, String correo) {
		// La cédula no aplica para el proveedor, 'contacto' se mapea a 'nombre' de Persona.
		super(0, contacto, correo); 
		this.idProveedor = idProveedor;
        this.nombreEmpresa = nombreEmpresa;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

    // <- CORRECCIÓN: Método getNombreEmpresa() requerido por Vinilo.java
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

	@Override
	public String toString() {
		return "Proveedor [idProveedor=" + idProveedor + ", nombreEmpresa=" + nombreEmpresa + ", contacto=" + getNombre() + "]";
	}
}
