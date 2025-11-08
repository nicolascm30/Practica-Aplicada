package co.edu.poli.dataBase;

public class Proveedor extends Persona {
	private int idProveedor;
	private String nombreEmpresa;

	public Proveedor() {
	}

	public Proveedor(int idProveedor, String nombreEmpresa, String contacto, String correo) {

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

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	@Override
	public String toString() {
		return "Proveedor [idProveedor=" + idProveedor + ", nombreEmpresa=" + nombreEmpresa + ", contacto="
				+ getNombre() + "]";
	}
}
