package co.edu.poli.presentacion;

import co.edu.poli.dataBase.*;
import co.edu.poli.datos.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		UsuarioDao usuarioDao = new UsuarioDao();
		CancionDao cancionDao = new CancionDao();
		

		System.out.println("🎵 Bienvenido a la Tienda de Música POO 🎵");
		System.out.println("Seleccione una opción:");
		System.out.println("1. Registrar usuario");
		System.out.println("2. Agregar canción");
		System.out.println("3. Comprar canción");
		System.out.println("4. Ver canciones disponibles");
		System.out.println("5. Salir");

		int opcion = sc.nextInt();
		sc.nextLine(); // limpiar buffer

		switch (opcion) {
		case 1:
			System.out.print("Ingrese nombre del usuario: ");
			String nombre = sc.nextLine();
			System.out.print("Ingrese correo: ");
			String correo = sc.nextLine();
			System.out.print("Ingrese cédula: ");
			int cedula = sc.nextInt();
			sc.nextLine();

			Usuario nuevo = new Usuario();
			nuevo.setNombre(nombre);
			nuevo.setCorreo(correo);
			nuevo.setCedula(cedula);
			usuarioDao.crearUsuario(nuevo);
			break;

		case 2:
			System.out.print("Ingrese título de la canción: ");
			String titulo = sc.nextLine();
			System.out.print("Ingrese duración (min): ");
			double duracion = sc.nextDouble();
			sc.nextLine();

			Cancion nuevaCancion = new Cancion();
			nuevaCancion.setTitulo(titulo);
			nuevaCancion.setDuracion(duracion);
			cancionDao.crearCancion(nuevaCancion);
			break;

		case 3:
			System.out.println("Compra simulada...");
			System.out.print("Ingrese ID del usuario: ");
			int idUser = sc.nextInt();
			System.out.print("Ingrese ID de la canción: ");
			int idCancion = sc.nextInt();
			sc.nextLine();

			Usuario comprador = usuarioDao.buscarUsuario(idUser);
			Cancion cancionComprada = cancionDao.buscarCancion(idCancion);

			if (comprador != null && cancionComprada != null) {
				System.out.println("✅ " + comprador.getNombre() + " ha comprado " + cancionComprada.getTitulo());
			} else {
				System.out.println("❌ No se encontró usuario o canción.");
			}
			break;

		case 4:
			cancionDao.verCanciones();
			break;

		case 5:
			System.out.println("👋 Saliendo de la aplicación...");
			break;

		default:
			System.out.println("Opción no válida.");
		}

		sc.close();
	}
}
