package co.edu.poli.presentacion;

import co.edu.poli.dataBase.Cancion; // Importación necesaria para usar el objeto Cancion
import co.edu.poli.dataBase.Usuario;
import co.edu.poli.datos.CancionDao;
import co.edu.poli.datos.UsuarioDao;
import co.edu.poli.negocio.ManagerCrearUsuario;
import java.util.Scanner;

public class Main {
//fucniones agregar y comprar
    private static void inicializarCancionesPrueba(CancionDao cancionDao) {
        // Verifica si ya hay canciones para evitar duplicados
        if (cancionDao.obtenerTodasLasCanciones().isEmpty()) {
            System.out.println("📦 Insertando canciones de prueba...");

            // Cancion(id, titulo, artista, duracionSegundos)
            cancionDao.crearCancion(new Cancion(0, "Bohemian Rhapsody", "Queen", 354.0));
            cancionDao.crearCancion(new Cancion(0, "Stairway to Heaven", "Led Zeppelin", 482.0));
            cancionDao.crearCancion(new Cancion(0, "Smells Like Teen Spirit", "Nirvana", 301.0));
            cancionDao.crearCancion(new Cancion(0, "Imagine", "John Lennon", 187.0));
            
            System.out.println("✅ 4 Canciones de prueba cargadas.");
        } else {
            System.out.println("✅ Ya existen canciones en la base de datos. Saltando carga de prueba.");
        }
    }
    // --------------------------------------------------------------------------


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// Inicialización de DAOs y Managers
		UsuarioDao usuarioDao = new UsuarioDao();
		CancionDao cancionDao = new CancionDao();
		ManagerCrearUsuario managerCrearUsuario = new ManagerCrearUsuario();

		// ---- INICIALIZACIÓN DE LA BASE DE DATOS ----
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("⚙️ Inicializando Conexión y Estructura de la Base de Datos...");
		
		// 1. Crear las tablas necesarias (asumiendo que llama a la creación de todas)
		cancionDao.crearTablasSiNoExisten(); 
		
		// 2. 💡 LLAMADA AL MÉTODO DE INICIALIZACIÓN (EL FIX)
		inicializarCancionesPrueba(cancionDao); 
		
		System.out.println("--------------------------------------------------------------------------");
		// ------------------------------------------

		int opcion;

		do {
			System.out.println("\n🎵 Bienvenido a la Tienda de Música POO 🎵");
			System.out.println("Seleccione una opción:");
			System.out.println("1. Registrar usuario (Comprador/Vendedor)");
			System.out.println("2. Agregar canción");
			System.out.println("3. Comprar canción");
			System.out.println("4. Ver canciones disponibles");
			System.out.println("5. Iniciar Sesión");
			System.out.println("0. Salir");

			// Leer la opción
			if (sc.hasNextInt()) {
				opcion = sc.nextInt();
				sc.nextLine(); // Consumir el salto de línea
			} else {
				System.out.println("❌ Opción no válida. Por favor, ingrese un número.");
				sc.nextLine(); // Consumir la entrada no válida
				opcion = -1; // Reset para continuar el loop
				continue;
			}


			switch (opcion) {
				case 1:
					// Lógica simplificada de Registrar usuario
					System.out.println("--- 👤 Registrar Usuario ---");
					System.out.print("Ingrese Cédula: ");
					if (!sc.hasNextInt()) {
						System.out.println("❌ Cédula no válida.");
						sc.nextLine();
						break;
					}
					int cedula = sc.nextInt();
					sc.nextLine(); 
					
					System.out.print("Ingrese Nombre: ");
					String nombre = sc.nextLine();
					
					System.out.print("Ingrese Correo: ");
					String correo = sc.nextLine();
					
					System.out.print("Ingrese Contraseña: ");
					String contrasena = sc.nextLine();
					
					System.out.print("Ingrese Rol (1<Comprador/2<Vendedor): ");
					String rol = sc.nextLine();
					
					managerCrearUsuario.crearNuevoCliente(cedula, nombre, correo, contrasena, rol);
					break;
				case 2:
					System.out.println("--- 🎶 Agregar Canción ---");
					System.out.println("Funcionalidad de Agregar canción pendiente de implementación.");
					break;
				case 3:
					// Lógica de Comprar canción (basada en tu snippet)
					System.out.println("--- 🛒 Comprar Canción ---");
					System.out.print("Ingrese cédula del comprador: ");
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
					// Búsqueda de la canción para verificar que exista
					Cancion cancionComprada = cancionDao.buscarCancion(idCancion); 
	
					if (comprador != null && cancionComprada != null) {
						System.out.println("✅ El usuario " + comprador.getNombre() + " (Cédula: " + cedulaUser + ") está registrado.");
						System.out.println("✅ Se ha 'comprado' la canción: " + cancionComprada.getTitulo() + " (Implementación de compra pendiente en Manager).");
					} else {
						if (comprador == null) {
							System.out.println("❌ Error: Usuario con cédula " + cedulaUser + " no encontrado en la DB.");
						}
						if (cancionComprada == null) {
							System.out.println("❌ Error: Canción con ID " + idCancion + " no encontrada en la DB.");
						}
					}
					break;
				case 4:
					// Lógica de Ver canciones disponibles
					System.out.println("--- 🎧 Canciones Disponibles ---");
					cancionDao.verCanciones();
					break;
				case 5:
					System.out.println("--- 🔒 Iniciar Sesión ---");
					System.out.println("Funcionalidad de Iniciar Sesión pendiente de implementación.");
					break;
				case 0:
					System.out.println("👋 Gracias por usar la Tienda de Música POO. ¡Hasta pronto!");
					break;
				default:
					System.out.println("❌ Opción no reconocida. Intente de nuevo.");
			}
		} while (opcion != 0);

		sc.close();
	}
}