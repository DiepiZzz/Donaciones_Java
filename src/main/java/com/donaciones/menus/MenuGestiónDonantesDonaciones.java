package com.donaciones.menus;

import java.sql.SQLException;
import java.util.Scanner;

import com.donacione.servicios.DonacionesService;
import com.donacione.servicios.DonanteService;

public class MenuGestiónDonantesDonaciones {
	

	    public static void mostrarMenu(Scanner scanner) throws SQLException {
			DonanteService service = new DonanteService();
			DonacionesService serviceDonacion = new DonacionesService();
	        int opcion;

	        do {
	            System.out.println("\n--- GESTIÓN DE DONANTES Y DONACIONES ---");
	            System.out.println("1. Registrar nuevo donante");
	            System.out.println("2. Registrar nueva donación");
	            System.out.println("3. Listar donantes");
	            System.out.println("4. Listar donaciones");
	            System.out.println("0. Volver al menú principal");
	            System.out.print("Seleccione una opción: ");
	            opcion = scanner.nextInt();

	            switch (opcion) {
	                case 1:
	                	System.out.println("");
	                    System.out.println("Registrando nuevo donante...");
	                    service.registrarDonante();
	                    MenuGestiónDonantesDonaciones.mostrarMenu(scanner);
	                    break;
	                case 2:
	                    // Aquí llamas a tu método para registrar donación
	                	System.out.println("");
	                    System.out.println("Registrando nueva donación...");
	                    MenuGestiónDonantesDonaciones.mostrarMenu(scanner);
	                    break;
	                case 3:
	                	System.out.println("");
	                    System.out.println("Listado de donantes:");
	                    service.listarDonantes();
	                    MenuGestiónDonantesDonaciones.mostrarMenu(scanner);
	                    break;
	                case 4:
	                	System.out.println("");
	                    System.out.println("Listado de donaciones:");
	                    serviceDonacion.listarDonaciones();
	                    MenuGestiónDonantesDonaciones.mostrarMenu(scanner);
	                    break;
	                case 0:
	                	System.out.println("");
	                    System.out.println("Volviendo al menú principal...");
	                    MenuPrincipal.main(null);
	                    break;
	                default:
	                    System.out.println("Opción inválida.");
	            }
	        } while (opcion != 0);
	    }
}

