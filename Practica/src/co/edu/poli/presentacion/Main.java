package co.edu.poli.presentacion;

// import co.edu.poli.dataBase.Cancion; // Ya no se usa la variable
import co.edu.poli.dataBase.Usuario;
import co.edu.poli.datos.CancionDao;
import co.edu.poli.datos.UsuarioDao;
import co.edu.poli.negocio.ManagerCrearUsuario;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		UsuarioDao usuarioDao = new UsuarioDao();
		CancionDao cancionDao = new CancionDao();
		ManagerCrearUsuario managerCrearUsuario = new ManagerCrearUsuario();

		System.out.println("--------------------------------------------------------------------------");
		System.out.println("⚙️ Inicializando Conexión y Estructura de la Base de Datos...");
		cancionDao.crearTablasSiNoExisten();
		System.out.println("--------------------------------------------------------------------------");

		int opcion;

		do {
			System.out.println("\n🎵 Bienvenido a la Tienda de Música POO 🎵");
			System.out.println("Seleccione una opción:");
			System.out.println("1. Registrar usuario (Comprador/Vendedor)");
			System.out.println("2. Agregar canción (Simulación)");
			System.out.println("3. Comprar canción (Simulación)");
			System.out.println("4. Ver canciones disponibles");
			System.out.println("5. Salir");

			if (sc.hasNextInt()) {
				opcion = sc.nextInt();
				sc.nextLine();
			} else {
				System.out.println("❌ Error: Por favor, ingrese un número válido del menú.");
				sc.nextLine();
				opcion = 0;
			}

			switch (opcion) {
			case 1:
				System.out.println("--- REGISTRO DE USUARIO ---");
				System.out.print("Ingrese cédula: ");
				int cedulaReg = sc.nextInt();
				sc.nextLine();
				System.out.print("Ingrese nombre: ");
				String nombreReg = sc.nextLine();
				System.out.print("Ingrese correo: ");
				String correoReg = sc.nextLine();
				System.out.print("Ingrese contraseña: ");
				String passReg = sc.nextLine();
				String rolReg = "Comprador";

				managerCrearUsuario.crearNuevoCliente(cedulaReg, nombreReg, correoReg, passReg, rolReg);
				break;

			case 2:
				System.out.println("--- AGREGAR CANCIÓN (Simulación de inserción en DB) ---");
				System.out.println(
						"Se necesita implementar la lógica completa en los DAOs (MP3Dao/ViniloDao) usando JDBC.");
				break;

			case 3:
				System.out.println("--- COMPRAR CANCIÓN (Simulación de búsqueda en DB) ---");

				System.out.print("Ingrese cédula del comprador (use 1010 para prueba): ");
				if (!sc.hasNextInt()) {
					System.out.println("❌ Cédula no válida. Volviendo al menú.");
					sc.nextLine();
					break;
				}
				int cedulaUser = sc.nextInt();
				sc.nextLine();

				System.out.print("Ingrese ID de la canción (cualquier número): ");
				if (!sc.hasNextInt()) {
					System.out.println("❌ ID de canción no válido. Volviendo al menú.");
					sc.nextLine();
					break;
				}
				int idCancion = sc.nextInt();
				sc.nextLine();

				Usuario comprador = usuarioDao.buscarUsuario(cedulaUser);

				// 💡 FIX: Se elimina la variable "cancionComprada" que no se usaba (Warning)
				// Cancion cancionComprada = new Cancion(idCancion, "Canción Sim.", "Artista
				// Sim.", 180.0);

				if (comprador != null) {
					System.out.println(
							"✅ El usuario " + comprador.getNombre() + " (Cédula: " + cedulaUser + ") está registrado.");
					System.out.println("✅ Se ha 'comprado' la canción con ID " + idCancion
							+ " (Implementación de compra pendiente en Manager).");
				} else {
					System.out.println("❌ Error: Usuario con cédula " + cedulaUser + " no encontrado en la DB.");
				}
				break;

			case 4:
				cancionDao.verCanciones();
				break;

			case 5:
				System.out.println("¡Hasta pronto! 👋");
				break;

			default:
				if (opcion != 0) {
					System.out.println("Opción no válida.");
				}
				break;
			}
		} while (opcion != 5);

		sc.close();
	}
}