package com.donacione.servicios;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.donaciones.model.Asignacion;
import com.donaciones.model.Donacion;
import com.donaciones.repository.AsignacionRepository;
import com.donaciones.repository.DonacionRepository;
import com.donaciones.util.DatabaseConnection;


public class AsignacionService {
	
	private AsignacionRepository repositorio;
	
	public AsignacionService() {
		this.repositorio = new AsignacionRepository();
	}
	
	public void listarAsignaciones() throws SQLException {
		
		List<Asignacion> listaAsignaciones = repositorio.findAll();
		
		for (Asignacion asignacion : listaAsignaciones) {
			System.out.println(asignacion.toString());
		}
		
	}
	
	
	public void registrarAsignacion(Scanner scanner) throws SQLException {
	    System.out.println("\n--- REGISTRO DE NUEVA ASIGNACIÓN ---");
	    System.out.print("Ingrese el ID de la donación: ");
	    int id_donacion = scanner.nextInt();

	    Connection conn = DatabaseConnection.getInstancia().conectarBase(); 
	    conn.setAutoCommit(false); 

	    try {
	        DonacionRepository repoDona = new DonacionRepository();
	        Donacion donacion = repoDona.findById(id_donacion);

	        if (donacion == null) {
	            System.out.println("❌ No se encontró una donación con ese ID.");
	            conn.rollback();
	            return;
	        }

	        String tipoAlimento = donacion.getTipoDonacion();

	        System.out.print("Ingrese el ID de la institución: ");
	        int idInstitucion = scanner.nextInt();

	        System.out.print("Ingrese la cantidad asignada: ");
	        BigDecimal cantidadAsignada = scanner.nextBigDecimal();

	        LocalDate localDate = LocalDate.now();
	        Date fechaAsignacion = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	        Asignacion asignacion = new Asignacion(0, id_donacion, idInstitucion, cantidadAsignada, fechaAsignacion);
	        repositorio.insert(asignacion);

	        String updateSQL = "UPDATE Inventario SET cantidad_disponible = cantidad_disponible - ? WHERE tipo_alimento = ?";
	        try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
	            ps.setBigDecimal(1, cantidadAsignada);
	            ps.setString(2, tipoAlimento);
	            int filas = ps.executeUpdate();

	            if (filas == 0) {
	                throw new SQLException("❌ No se encontró el inventario con ese tipo de alimento.");
	            }
	        }

	        conn.commit(); 
	        System.out.println("✅ Asignación registrada y cantidad en inventario actualizada.");

	    } catch (Exception e) {
	        conn.rollback(); 
	        System.out.println("⚠️ Error en la transacción: " + e.getMessage());
	    } finally {
	        conn.setAutoCommit(true);
	        conn.close();
	    }
	}
	
	
	

}
