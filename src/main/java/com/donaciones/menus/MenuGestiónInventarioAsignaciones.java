package com.donaciones.menus;

import java.util.Scanner;

import com.donacione.servicios.InventarioService;

public class MenuGestiónInventarioAsignaciones {
	public static void mostrarMenu(Scanner scanner) {
		InventarioService service = new InventarioService();
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE INVENTARIO Y ASIGNACIONES ---");
            System.out.println("1. Ver inventario disponible");
            System.out.println("2. Registrar nueva asignación");
            System.out.println("3. Listar asignaciones realizadas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                	System.out.println("");
                    System.out.println("Mostrando inventario...");
                    service.mostrarInventarioResumen();
                    break;
                case 2:
                    // Llamar método para registrar asignación
                    System.out.println("Registrando nueva asignación...");
                    break;
                case 3:
                    // Llamar método para listar asignaciones //
                    System.out.println("Listado de asignaciones:");
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

}
