package com.donaciones.menus;

import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcionPrincipal;

        do {
            System.out.println("===== DONACIONES SOLIDARIAS =====");
            System.out.println("1. Gestión de Donantes y Donaciones");
            System.out.println("2. Gestión de Inventario y Asignaciones");
            System.out.println("3. Gestión de Entregas y Seguimiento");
            System.out.println("4. Consultas y Reportes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcionPrincipal = scanner.nextInt();

            switch (opcionPrincipal) {
                case 1:
                    MenuGestiónDonantesDonaciones.mostrarMenu(scanner);
                    break;
                case 2:
                    // Llamar a menú de inventario
                    break;
                case 3:
                    // Llamar a menú de entregas
                    break;
                case 4:
                    // Llamar a menú de consultas
                    break;
                case 0:
                    System.out.println("¡Gracias por usar el sistema! 👋");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcionPrincipal != 0);

        scanner.close();
    }
}

