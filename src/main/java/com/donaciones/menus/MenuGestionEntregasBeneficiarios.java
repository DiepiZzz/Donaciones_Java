// Archivo: com/donaciones/menus/MenuGestionEntregasBeneficiarios.java
package com.donaciones.menus;

import com.donaciones.model.Beneficiario;
import com.donaciones.model.Entrega;
import com.donacione.servicios.BeneficiarioService; // Importa tu servicio de Beneficiario
import com.donacione.servicios.EntregaService;     // Importa tu servicio de Entrega
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException; // Para manejar errores de formato de fecha
import java.text.SimpleDateFormat; // Para parsear fechas
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuGestionEntregasBeneficiarios {

    // Instancias de los servicios
    private static BeneficiarioService beneficiarioService = new BeneficiarioService();
    private static EntregaService entregaService = new EntregaService();
    private static Scanner scanner; // Usamos el scanner que se pasa desde el main

    public static void mostrarMenu(Scanner sc) {
        scanner = sc; // Asigna el scanner pasado al estático de la clase
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE ENTREGAS Y BENEFICIARIOS ---");
            System.out.println("1. Registrar Entrega");
            System.out.println("2. Registrar Beneficiario");
            System.out.println("3. Consultar Entregas por Beneficiario");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            // Manejo de entrada no numérica
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next(); // Consumir la entrada inválida
                System.out.print("Seleccione una opción: ");
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea después de leer el entero

            switch (opcion) {
                case 1:
                    registrarEntrega();
                    break;
                case 2:
                    registrarBeneficiario();
                    break;
                case 3:
                    consultarEntregasPorBeneficiario();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Métodos para cada opción del menú ---

    private static void registrarEntrega() {
        System.out.println("\n--- REGISTRAR NUEVA ENTREGA ---");
        try {
            System.out.print("ID de la Asignación (id_asignacion): ");
            int idAsignacion = scanner.nextInt();
            scanner.nextLine();

            System.out.print("ID del Beneficiario (id_beneficiario): ");
            int idBeneficiario = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Cantidad Entregada: ");
            BigDecimal cantidadEntregada = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.print("Fecha de Entrega (YYYY-MM-DD): ");
            String fechaStr = scanner.nextLine();
            Date fechaEntrega = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); // No permitir fechas inválidas como 2023-02-30
                fechaEntrega = sdf.parse(fechaStr);
            } catch (ParseException e) {
                System.err.println("Formato de fecha inválido. Use YYYY-MM-DD.");
                return; // Salir del método si la fecha es inválida
            }

            Entrega nuevaEntrega = new Entrega(0, idAsignacion, idBeneficiario, cantidadEntregada, fechaEntrega);
            entregaService.registrarEntrega(nuevaEntrega);
            System.out.println("Entrega registrada con éxito.");

        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de base de datos al registrar entrega: " + e.getMessage());
            // Opcional: e.printStackTrace(); para ver el stack trace completo
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado al registrar la entrega: " + e.getMessage());
        }
    }

    private static void registrarBeneficiario() {
        System.out.println("\n--- REGISTRAR NUEVO BENEFICIARIO ---");
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();

            Beneficiario nuevoBeneficiario = new Beneficiario(0, nombre, apellido, direccion, telefono);
            beneficiarioService.registrarBeneficiario(nuevoBeneficiario);
            System.out.println("Beneficiario registrado con éxito.");

        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de base de datos al registrar beneficiario: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado al registrar el beneficiario: " + e.getMessage());
        }
    }

    private static void consultarEntregasPorBeneficiario() {
        System.out.println("\n--- CONSULTAR ENTREGAS POR BENEFICIARIO ---");
        try {
            System.out.print("ID del Beneficiario a consultar: ");
            int idBeneficiario = scanner.nextInt();
            scanner.nextLine();

            // Primero, verificar si el beneficiario existe
            Beneficiario beneficiario = beneficiarioService.buscarBeneficiarioPorId(idBeneficiario);
            if (beneficiario == null) {
                System.out.println("No se encontró ningún beneficiario con ID: " + idBeneficiario);
                return;
            }
            System.out.println("Entregas para el beneficiario: " + beneficiario.getNombre() + " " + beneficiario.getApellido());
            System.out.println("--------------------------------------------------------------------------------------------------");


            // Para consultar entregas por beneficiario, necesitarás un nuevo método en EntregaRepository.
            // Por ahora, listaremos todas y filtraremos en memoria (NO RECOMENDADO PARA GRANDES CANTIDADES DE DATOS).
            // Lo ideal es tener un método `entregaService.obtenerEntregasPorBeneficiario(idBeneficiario);`

            List<Entrega> todasLasEntregas = entregaService.obtenerTodasLasEntregas();
            boolean foundEntregas = false;
            for (Entrega entrega : todasLasEntregas) {
                if (entrega.getIdBeneficiario() == idBeneficiario) {
                    System.out.printf("ID Entrega: %d, ID Asignación: %d, Cantidad: %.2f, Fecha: %s\n",
                                      entrega.getIdEntrega(),
                                      entrega.getIdAsignacion(),
                                      entrega.getCantidadEntregada(),
                                      new SimpleDateFormat("yyyy-MM-dd").format(entrega.getFechaEntrega()));
                    foundEntregas = true;
                }
            }

            if (!foundEntregas) {
                System.out.println("No se encontraron entregas para este beneficiario.");
            }
            System.out.println("--------------------------------------------------------------------------------------------------");


        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de base de datos al consultar entregas: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado al consultar entregas: " + e.getMessage());
        }
    }
    
    
}
