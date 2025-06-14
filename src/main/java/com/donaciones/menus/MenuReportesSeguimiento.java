
package com.donaciones.menus;


import com.donaciones.service.SeguimientoService; 
import com.donaciones.servicios.ReporteService;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuReportesSeguimiento {

    public static void mostrarMenu(Scanner scanner) throws SQLException {
        ReporteService reporteService = new ReporteService();
        SeguimientoService seguimientoService = new SeguimientoService(); 
        int opcion;

        do {
            System.out.println("\n--- MENÚ DE REPORTES Y SEGUIMIENTO ---");
            System.out.println("1. Reporte Total de Donaciones por Tipo");
            System.out.println("2. Reporte de Instituciones con más Asignaciones");
            System.out.println("3. Seguimiento de Entregas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("¡Entrada inválida! Por favor, ingrese un número.");
                scanner.nextLine(); 
                opcion = -1; 
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Reporte: Total de Donaciones por Tipo ---");
                    try {
                        List<Map<String, Object>> donacionesPorTipo = reporteService.getTotalDonacionesPorTipo();
                        if (donacionesPorTipo != null && !donacionesPorTipo.isEmpty()) {
                            System.out.println("-------------------------------------");
                            System.out.printf("%-20s | %-15s%n", "Tipo de Donación", "Cantidad Total");
                            System.out.println("-------------------------------------");
                            for (Map<String, Object> fila : donacionesPorTipo) {
                                System.out.printf("%-20s | %-15s%n", fila.get("tipoDonacion"), fila.get("totalDonado"));
                            }
                            System.out.println("-------------------------------------");
                        } else {
                            System.out.println("No hay datos de donaciones para generar el reporte.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al generar el reporte de donaciones por tipo: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\n--- Reporte: Instituciones con Más Asignaciones ---");
                    try {
                        List<Map<String, Object>> institucionesConMasAsignaciones = reporteService.getInstitucionesConMasAsignaciones();
                        if (institucionesConMasAsignaciones != null && !institucionesConMasAsignaciones.isEmpty()) {
                            System.out.println("-------------------------------------------------");
                            System.out.printf("%-25s | %-15s%n", "Institución", "Nro. Asignaciones");
                            System.out.println("-------------------------------------------------");
                            for (Map<String, Object> fila : institucionesConMasAsignaciones) {
                                System.out.printf("%-25s | %-15d%n", fila.get("nombreInstitucion"), fila.get("numAsignaciones"));
                            }
                            System.out.println("-------------------------------------------------");
                        } else {
                            System.out.println("No hay datos de asignaciones para generar el reporte.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error al generar el reporte de instituciones con más asignaciones: " + e.getMessage());
                    }
                    break;
                case 3:
                    
                    System.out.println("\n--- SUBMENÚ: SEGUIMIENTO DE ENTREGAS ---");
                    int subOpcionSeguimiento;
                    do {
                        System.out.println("  1. Registrar nuevo seguimiento para una entrega");
                        System.out.println("  2. Consultar historial de seguimiento de una entrega");
                        
                        
                        
                        System.out.println("  0. Volver al menú anterior");
                        System.out.print("  Seleccione una opción de seguimiento: ");

                        try {
                            subOpcionSeguimiento = scanner.nextInt();
                            scanner.nextLine(); 
                        } catch (InputMismatchException e) {
                            System.out.println("  ¡Entrada inválida! Por favor, ingrese un número.");
                            scanner.nextLine(); 
                            subOpcionSeguimiento = -1;
                        }

                        switch (subOpcionSeguimiento) {
                            case 1:
                                seguimientoService.registrarSeguimientoMenu(scanner);
                                break;
                            case 2:
                                seguimientoService.consultarHistorialSeguimientoMenu(scanner);
                                break;
                            case 0:
                                System.out.println("  Volviendo al menú de reportes y seguimiento...");
                                break;
                            default:
                                System.out.println("  Opción de seguimiento inválida. Intente de nuevo.");
                        }
                    } while (subOpcionSeguimiento != 0);
                    break; 
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("¡Opción inválida! Por favor, seleccione una opción del menú.");
            }
        } while (opcion != 0);
    }
}