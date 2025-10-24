package co.edu.poli.presentacion;

import co.edu.poli.dataBase.Cancion; // Importación necesaria para usar el objeto Cancion
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
		// ✅ Se llama al método para asegurar que la tabla existe.
		cancionDao.crearTablasSiNoExisten();
		System.out.println("--------------------------------------------------------------------------");

		int opcion;

		do {
			System.out.println("\n🎵 Bienvenido a la Tienda de Música POO 🎵");
			System.out.println("Seleccione una opción:");
			System.out.println("1. Registrar usuario (Comprador/Vendedor)");
			System.out.println("2. Agregar canción");
			System.out.println("3. Comprar canción");
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
				// Validación básica para evitar que el scanner lea la siguiente línea como entero
				if (!sc.hasNextInt()) {
					System.out.println("❌ Cédula no válida. Volviendo al menú.");
					sc.nextLine();
					break;
				}
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
				System.out.println("--- AGREGAR CANCIÓN ---");
				// Mensaje de TODO más limpio.
				System.out.println("⚠️ Implementación de la lógica de inserción de canción pendiente en MP3Dao/ViniloDao.");
				break;

			case 3:
				System.out.println("--- COMPRAR CANCIÓN ---");

				System.out.print("Ingrese cédula del comprador (ej: 1010): ");
				if (!sc.hasNextInt()) {
					System.out.println("❌ Cédula no válida. Volviendo al menú.");
					sc.nextLine();
					break;
				}
				int cedulaUser = sc.nextInt();
				sc.nextLine();

				System.out.print("Ingrese ID de la canción: ");
				if (!sc.hasNextInt()) {
					System.out.println("❌ ID de canción no válido. Volviendo al menú.");
					sc.nextLine();
					break;
				}
				int idCancion = sc.nextInt();
				sc.nextLine();

				Usuario comprador = usuarioDao.buscarUsuario(cedulaUser);
				// ✅ FIX: Se recupera la lógica de buscar la canción en la DB
				Cancion cancionComprada = cancionDao.buscarCancion(idCancion); 

				if (comprador != null && cancionComprada != null) {
					System.out.println("✅ El usuario " + comprador.getNombre() + " (Cédula: " + cedulaUser + ") está registrado.");
					System.out.println("✅ Se ha 'comprado' la canción: " + cancionComprada.getTitulo() + " (Implementación de compra pendiente en Manager).");
				} else {
					// Mensaje de error más descriptivo
					if (comprador == null) {
					    System.out.println("❌ Error: Usuario con cédula " + cedulaUser + " no encontrado en la DB.");
					}
					if (cancionComprada == null) {
					    System.out.println("❌ Error: Canción con ID " + idCancion + " no encontrada en la DB.");
					}
				}
				break;

			case 4:
				// ✅ Llama a la función de ver canciones (ahora de la DB)
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