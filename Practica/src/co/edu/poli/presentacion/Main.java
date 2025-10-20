package co.edu.poli.presentacion;

import co.edu.poli.dataBase.*;
import co.edu.poli.datos.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		UsuarioDao usuarioDao = new UsuarioDao();
		CancionDao cancionDao = new CancionDao();
		
        // Muestra las canciones de simulación al inicio.
        cancionDao.verCanciones(); 

        int opcion;
        
        // 💡 BUCLE PRINCIPAL: Permite volver al menú después de cada operación
        do {
            System.out.println("\n🎵 Bienvenido a la Tienda de Música POO 🎵");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Agregar canción");
            System.out.println("3. Comprar canción");
            System.out.println("4. Ver canciones disponibles");
            System.out.println("5. Salir");

            // Manejo de entrada no válida para la opción del menú
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine(); // limpiar buffer
            } else {
                System.out.println("❌ Error: Por favor, ingrese un número válido del menú.");
                sc.nextLine(); // Consumir la entrada no válida
                opcion = 0; 
                continue;
            }


            switch (opcion) {
            case 1:
                System.out.print("Ingrese nombre del usuario: ");
                String nombre = sc.nextLine();
                System.out.print("Ingrese correo: ");
                String correo = sc.nextLine();
                System.out.print("Ingrese cédula: ");
                
                if (sc.hasNextInt()) {
                    int cedula = sc.nextInt();
                    sc.nextLine(); 

                    Usuario nuevo = new Usuario();
                    nuevo.setNombre(nombre);
                    nuevo.setCorreo(correo);
                    nuevo.setCedula(cedula);
                    // Aquí faltaría pedir Contraseña y Rol para un Usuario completo.
                    usuarioDao.crearUsuario(nuevo);
                } else {
                    System.out.println("❌ Cédula no válida. Volviendo al menú.");
                    sc.nextLine(); 
                }
                
                break;

            case 2:
                // --- LÓGICA DE AGREGAR CANCIÓN ---
                System.out.print("Ingrese título de la canción: ");
                String titulo = sc.nextLine();
                
                System.out.print("Ingrese artista de la canción: "); 
                String artista = sc.nextLine();
                
                // 💡 CORRECCIÓN DE FORMATO: Pedir M:SS y parsear
                System.out.print("Ingrese duración (formato M:SS, ej. 3:21): ");
                String duracionM_SS = sc.nextLine();
                
                double duracionSegundos = 0.0;
                
                try {
                    String[] partes = duracionM_SS.split(":");
                    if (partes.length == 2) {
                        int minutos = Integer.parseInt(partes[0].trim());
                        int segundos = Integer.parseInt(partes[1].trim());
                        
                        // Validación básica
                        if (minutos < 0 || segundos < 0 || segundos >= 60) {
                             throw new NumberFormatException("Los segundos deben ser entre 0 y 59.");
                        }
                        
                        // Conversión a segundos totales
                        duracionSegundos = (minutos * 60.0) + segundos;
                        
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error: Formato de duración incorrecto. Debe ser M:SS (ej. 3:21). Volviendo al menú.");
                    break; // Salir del case 2 y volver al menú
                }


                System.out.print("Ingrese el tipo (MP3/VINILO): ");
                String tipo = sc.nextLine().toUpperCase();
                
                int nuevoId = cancionDao.getNextId();
                
                // Lógica de asignación de ID simplificada
                if (tipo.equals("VINILO") && nuevoId < 101) {
                    nuevoId = 106; // Asegura que el Vinilo empiece después del último de simulación (105)
                } 
                
                Cancion nuevaCancion = new Cancion(
                    nuevoId, 
                    titulo, 
                    artista, 
                    duracionSegundos // Segundos totales
                );
                
                cancionDao.crearCancion(nuevaCancion); 
                
                break;

            case 3:
                System.out.println("Compra simulada...");
                System.out.print("Ingrese cédula del usuario: ");
                // Uso seguro del Scanner para la cédula
                if (!sc.hasNextInt()) {
                    System.out.println("❌ Cédula no válida. Volviendo al menú.");
                    sc.nextLine();
                    break;
                }
                int cedulaUser = sc.nextInt();
                
                System.out.print("Ingrese ID de la canción: ");
                // Uso seguro del Scanner para el ID
                if (!sc.hasNextInt()) {
                    System.out.println("❌ ID de canción no válido. Volviendo al menú.");
                    sc.nextLine();
                    break;
                }
                int idCancion = sc.nextInt();
                sc.nextLine(); // limpiar buffer

                Usuario comprador = usuarioDao.buscarUsuario(cedulaUser);
                Cancion cancionComprada = cancionDao.buscarCancion(idCancion);

                if (comprador != null && cancionComprada != null) {
                    System.out.println("✅ " + comprador.getNombre() + " ha 'comprado' " + cancionComprada.getTitulo() + "!");
                } else {
                    System.out.println("❌ Error: Usuario o Canción no encontrados.");
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
        // Condición de salida: el bucle se ejecuta mientras la opción no sea 5
        } while (opcion != 5);

		sc.close();
	}
}