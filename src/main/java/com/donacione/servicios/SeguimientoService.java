
package com.donaciones.service;

import com.donaciones.model.Seguimiento;
import com.donaciones.model.Entrega; 
import com.donaciones.repository.SeguimientoRepository;
import com.donaciones.repository.EntregaRepository; 
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner; 

public class SeguimientoService {

    private SeguimientoRepository seguimientoRepository;
    private EntregaRepository entregaRepository; 

    public SeguimientoService() {
        this.seguimientoRepository = new SeguimientoRepository();
        this.entregaRepository = new EntregaRepository();
    }

    
    public void registrarNuevoSeguimiento(int idEntrega, String observaciones)
            throws IllegalArgumentException, SQLException {
        if (idEntrega <= 0) {
            throw new IllegalArgumentException("ID de entrega inválido. Debe ser un número positivo.");
        }
        if (observaciones == null || observaciones.trim().isEmpty()) {
            throw new IllegalArgumentException("Las observaciones del seguimiento no pueden estar vacías.");
        }

        
        Entrega entregaExistente = entregaRepository.findById(idEntrega);
        if (entregaExistente == null) {
            throw new IllegalArgumentException("La entrega con ID " + idEntrega + " no existe.");
        }

        
        Seguimiento nuevoSeguimiento = new Seguimiento(0, idEntrega, observaciones.trim(), new Date());
        seguimientoRepository.insert(nuevoSeguimiento);
        System.out.println("Seguimiento para la entrega " + idEntrega + " registrado con éxito. ID de seguimiento: " + nuevoSeguimiento.getIdSeguimiento());
    }

    
    public List<Seguimiento> obtenerHistorialSeguimientoPorEntrega(int idEntrega)
            throws IllegalArgumentException, SQLException {
        if (idEntrega <= 0) {
            throw new IllegalArgumentException("ID de entrega inválido. Debe ser un número positivo.");
        }
        
        
        
        return seguimientoRepository.findAll().stream() 
            .filter(s -> s.getIdEntrega() == idEntrega)
            .collect(java.util.stream.Collectors.toList());
    }
    
    


    
    public void registrarSeguimientoMenu(Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID de la entrega a la que desea hacer seguimiento: ");
        int idEntrega;
        try {
            idEntrega = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.err.println("Entrada inválida. Por favor, ingrese un número entero para el ID de entrega.");
            scanner.nextLine(); 
            return;
        }
        scanner.nextLine(); 

        System.out.print("Ingrese las observaciones para este seguimiento: ");
        String observaciones = scanner.nextLine();

        try {
            registrarNuevoSeguimiento(idEntrega, observaciones);
            System.out.println("Seguimiento registrado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de base de datos al registrar seguimiento: " + e.getMessage());
        }
    }

    
    public void consultarHistorialSeguimientoMenu(Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID de la entrega cuyo historial de seguimiento desea consultar: ");
        int idEntrega;
        try {
            idEntrega = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.err.println("Entrada inválida. Por favor, ingrese un número entero para el ID de entrega.");
            scanner.nextLine(); 
            return;
        }
        scanner.nextLine(); 

        try {
            List<Seguimiento> historial = obtenerHistorialSeguimientoPorEntrega(idEntrega);
            if (historial.isEmpty()) {
                System.out.println("No se encontró historial de seguimiento para la entrega con ID " + idEntrega + ".");
            } else {
                System.out.println("\n--- Historial de Seguimiento para Entrega " + idEntrega + " ---");
                for (Seguimiento s : historial) {
                    System.out.println("ID Seguimiento: " + s.getIdSeguimiento() +
                                       ", Observaciones: " + s.getObservaciones() +
                                       ", Fecha: " + s.getFechaSeguimiento());
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de base de datos al consultar historial de seguimiento: " + e.getMessage());
        }
    }
}