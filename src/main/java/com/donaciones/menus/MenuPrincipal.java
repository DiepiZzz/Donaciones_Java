package com.donaciones.menus;

import com.donaciones.presentacion.LoginConsola;
import com.donaciones.model.Usuario;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioLogeado = null;

        LoginConsola login = new LoginConsola();
        usuarioLogeado = login.iniciarSesion();

        if (usuarioLogeado != null) {
            System.out.println("\n--- ¡Bienvenido al sistema de Donaciones Solidarias, " + usuarioLogeado.getUsername() + "! ---");

            int opcionPrincipal;
            do {
                System.out.println("\n===== DONACIONES SOLIDARIAS =====");
                System.out.println("1. Gestión de Donantes y Donaciones");
                System.out.println("2. Gestión de Inventario y Asignaciones");
                System.out.println("3. Gestión de Entregas y Seguimiento");
                System.out.println("4. Consultas y Reportes");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                try {

                    opcionPrincipal = Integer.parseInt(scanner.nextLine());

                    switch (opcionPrincipal) {
                        case 1:

                            MenuGestiónDonantesDonaciones.mostrarMenu(scanner);
                            break;
                        case 2:
                            MenuGestiónInventarioAsignaciones.mostrarMenu(scanner);
                            break;
                        case 3:
                            MenuGestionEntregasBeneficiarios.mostrarMenu(scanner);
                            break;
                        case 4:

                            MenuReportesSeguimiento.mostrarMenu(scanner);
                            break;
                        case 0:
                            System.out.println("¡Gracias por usar el sistema! 👋");
                            break;
                        default:
                            System.out.println("Opción inválida. Por favor, intente de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese un número.");
                    opcionPrincipal = -1;
                } catch (SQLException e) {
                    System.err.println("Error de base de datos en el menú principal: " + e.getMessage());

                    opcionPrincipal = -1;
                }
            } while (opcionPrincipal != 0);

        } else {

            System.out.println("Inicio de sesión fallido o cancelado. La aplicación se cerrará.");
        }

        scanner.close();
    }
}
