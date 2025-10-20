package co.edu.poli.negocio;

import co.edu.poli.datos.UsuarioDao;
import co.edu.poli.datos.AdministradorDao;
import co.edu.poli.datos.ProveedorDao;
import co.edu.poli.datos.TransaccionDao;

public class ManegerReportes {

	private UsuarioDao usuarioDao;
	private AdministradorDao administradorDao;
	private ProveedorDao proveedorDao;
	private TransaccionDao transaccionDao;

	public ManegerReportes() {
		usuarioDao = new UsuarioDao();
		administradorDao = new AdministradorDao();
		proveedorDao = new ProveedorDao();
		transaccionDao = new TransaccionDao();
	}

	public void reporteUsuarios() {
		System.out.println("📊 Reporte de Usuarios:");
		usuarioDao.verUsuarios();
	}

	public void reporteAdministradores() {
		System.out.println("📊 Reporte de Administradores:");
		administradorDao.verAdministrador();
	}

	public void reporteProveedores() {
		System.out.println("📊 Reporte de Proveedores:");
		proveedorDao.verProveedores();
	}

	public void reporteTransacciones() {
		System.out.println("📊 Reporte de Transacciones:");
		transaccionDao.verTransacciones();
	}
}
