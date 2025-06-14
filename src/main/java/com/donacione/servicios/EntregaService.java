package com.donacione.servicios;

import com.donaciones.model.Asignacion;
import com.donaciones.model.Entrega;
import com.donaciones.model.Inventario;
import com.donaciones.repository.AsignacionRepository;
import com.donaciones.repository.BeneficiarioRepository;
import com.donaciones.repository.EntregaRepository; 
import com.donaciones.repository.InventarioRepository; 
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EntregaService {

    private EntregaRepository entregaRepository;
    private AsignacionRepository asignacionRepository;
    private BeneficiarioRepository beneficiarioRepository;
    private InventarioRepository inventarioRepository;

    public EntregaService() {
        this.entregaRepository = new EntregaRepository();
        this.asignacionRepository = new AsignacionRepository();
        this.beneficiarioRepository = new BeneficiarioRepository();
        this.inventarioRepository = new InventarioRepository();
    }

    
    public void registrarEntrega(Entrega entrega) throws IllegalArgumentException, SQLException {
        if (entrega == null) {
            throw new IllegalArgumentException("El objeto Entrega no puede ser nulo.");
        }
        if (entrega.getCantidadEntregada() == null || entrega.getCantidadEntregada().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad entregada debe ser mayor que cero.");
        }
        if (entrega.getFechaEntrega() == null || entrega.getFechaEntrega().after(new Date())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser futura.");
        }

        Asignacion asignacion = asignacionRepository.findById(entrega.getIdAsignacion());
        if (asignacion == null) {
            throw new IllegalArgumentException("La asignación con ID " + entrega.getIdAsignacion() + " no existe.");
        }
        if (beneficiarioRepository.findById(entrega.getIdBeneficiario()) == null) {
            throw new IllegalArgumentException("El beneficiario con ID " + entrega.getIdBeneficiario() + " no existe.");
        }

       
        Inventario inventarioAsociado = inventarioRepository.findByDonacionId(asignacion.getIdDonacion());

        if (inventarioAsociado != null) {
            if (inventarioAsociado.getCantidadDisponible().compareTo(entrega.getCantidadEntregada()) < 0) {
                throw new IllegalArgumentException("No hay suficiente cantidad en inventario (" + inventarioAsociado.getCantidadDisponible() + ") para esta entrega de " + entrega.getCantidadEntregada() + ".");
            }
            BigDecimal nuevaCantidad = inventarioAsociado.getCantidadDisponible().subtract(entrega.getCantidadEntregada());
            inventarioAsociado.setCantidadDisponible(nuevaCantidad);
            inventarioRepository.update(inventarioAsociado);
            System.out.println("Cantidad de " + entrega.getCantidadEntregada() + " descontada del inventario (ID: " + inventarioAsociado.getIdInventario() + ").");
        } else {
             System.out.println("Advertencia: No se encontró inventario asociado para descontar la cantidad de la entrega.");
        }

        entregaRepository.insert(entrega);
        System.out.println("Entrega registrada con éxito. ID: " + entrega.getIdEntrega());
    }

    public Entrega buscarEntregaPorId(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de entrega inválido.");
        }
        return entregaRepository.findById(id);
    }

    public List<Entrega> obtenerTodasLasEntregas() throws SQLException {
        return entregaRepository.findAll();
    }

   
    public List<Entrega> obtenerEntregasPorBeneficiario(int idBeneficiario) throws IllegalArgumentException, SQLException {
        if (idBeneficiario <= 0) {
            throw new IllegalArgumentException("ID de beneficiario inválido para consulta de entregas.");
        }
        return entregaRepository.findByBeneficiarioId(idBeneficiario);
    }

    
    public void actualizarEntrega(Entrega entrega) throws IllegalArgumentException, SQLException {
        if (entrega == null) {
            throw new IllegalArgumentException("El objeto Entrega no puede ser nulo.");
        }
        if (entrega.getIdEntrega() <= 0) {
            throw new IllegalArgumentException("ID de entrega inválido para actualización.");
        }
        Entrega oldEntrega = entregaRepository.findById(entrega.getIdEntrega());
        if (oldEntrega == null) {
            throw new IllegalArgumentException("La entrega con ID " + entrega.getIdEntrega() + " no existe.");
        }

        BigDecimal oldCantidad = oldEntrega.getCantidadEntregada();
        BigDecimal newCantidad = entrega.getCantidadEntregada();

        if (newCantidad.compareTo(oldCantidad) != 0) { // If the quantity has changed
            Asignacion asignacion = asignacionRepository.findById(entrega.getIdAsignacion());
            if (asignacion == null) {
                throw new IllegalArgumentException("La asignación con ID " + entrega.getIdAsignacion() + " no existe para la entrega.");
            }
            Inventario inventarioAsociado = inventarioRepository.findByDonacionId(asignacion.getIdDonacion());

            if (inventarioAsociado != null) {
                BigDecimal diferencia = newCantidad.subtract(oldCantidad); // Positive if increasing, negative if decreasing
                // If the difference is positive (new quantity is greater than old),
                // we check if there's enough in inventory to cover the increase.
                if (diferencia.compareTo(BigDecimal.ZERO) > 0 && inventarioAsociado.getCantidadDisponible().compareTo(diferencia) < 0) {
                    throw new IllegalArgumentException("No hay suficiente inventario para aumentar la entrega en " + diferencia + " unidades.");
                }
                // Adjust the available quantity in inventory
                inventarioAsociado.setCantidadDisponible(inventarioAsociado.getCantidadDisponible().subtract(diferencia));
                inventarioRepository.update(inventarioAsociado);
                System.out.println("Inventario ajustado en " + diferencia + " para la entrega ID " + entrega.getIdEntrega() + ".");
            } else {
                System.out.println("Advertencia: No se encontró inventario asociado para actualizar la cantidad.");
            }
        }
        if (entrega.getCantidadEntregada() == null || entrega.getCantidadEntregada().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad entregada debe ser mayor que cero.");
        }
        if (entrega.getFechaEntrega() == null || entrega.getFechaEntrega().after(new Date())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser futura.");
        }
        if (asignacionRepository.findById(entrega.getIdAsignacion()) == null) {
            throw new IllegalArgumentException("La asignación con ID " + entrega.getIdAsignacion() + " no existe.");
        }
        if (beneficiarioRepository.findById(entrega.getIdBeneficiario()) == null) {
            throw new IllegalArgumentException("El beneficiario con ID " + entrega.getIdBeneficiario() + " no existe.");
        }

        entregaRepository.update(entrega);
        System.out.println("Entrega con ID " + entrega.getIdEntrega() + " actualizada con éxito.");
    }

    
    public void eliminarEntrega(int id) throws IllegalArgumentException, SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de entrega inválido para eliminación.");
        }
        Entrega entrega = entregaRepository.findById(id);
        if (entrega == null) {
            throw new IllegalArgumentException("La entrega con ID " + id + " no existe.");
        }

        // Business Logic: Return the quantity to inventory when deleting the delivery
        Asignacion asignacion = asignacionRepository.findById(entrega.getIdAsignacion());
        if (asignacion != null) {
            Inventario inventarioAsociado = inventarioRepository.findByDonacionId(asignacion.getIdDonacion());
            if (inventarioAsociado != null) {
                BigDecimal nuevaCantidad = inventarioAsociado.getCantidadDisponible().add(entrega.getCantidadEntregada());
                inventarioAsociado.setCantidadDisponible(nuevaCantidad);
                inventarioRepository.update(inventarioAsociado);
                System.out.println("Cantidad de " + entrega.getCantidadEntregada() + " devuelta al inventario (ID: " + inventarioAsociado.getIdInventario() + ").");
            } else {
                System.out.println("Advertencia: No se encontró inventario asociado para revertir la cantidad.");
            }
        } else {
            System.out.println("Advertencia: Asignación asociada no encontrada para la entrega ID " + id + ".");
        }

        

        entregaRepository.delete(id);
        System.out.println("Entrega con ID " + id + " eliminada con éxito.");
    }
}