package com.donacione.servicios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.donaciones.util.DatabaseConnection;

public class InventarioService {
	
	 public void mostrarInventarioResumen() {

	        try (Connection conn = DatabaseConnection.getInstancia().conectarBase()) {

	            System.out.println("\n📦 INVENTARIO ACTUAL (Vista)");
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM vista_inventario");

	            while (rs.next()) {
	                System.out.printf("- %s | %.2f unidades | Vence: %s%n",
	                        rs.getString("tipo_alimento"),
	                        rs.getBigDecimal("cantidad_disponible"),
	                        rs.getDate("fecha_caducidad"));
	            }

	            System.out.println("\n📊 RESUMEN DEL INVENTARIO");
	            rs = stmt.executeQuery("SELECT COUNT(*) AS total_productos, SUM(cantidad_disponible) AS total_unidades FROM Inventario");
	            if (rs.next()) {
	                System.out.printf("👉 Productos diferentes: %d%n", rs.getInt("total_productos"));
	                System.out.printf("👉 Total de unidades: %.2f%n", rs.getDouble("total_unidades"));
	            }

	            rs = stmt.executeQuery("SELECT tipo_alimento, cantidad_disponible FROM Inventario ORDER BY cantidad_disponible DESC LIMIT 1");
	            if (rs.next()) {
	                System.out.printf("🔝 Más cantidad: %s (%.2f unidades)%n", rs.getString("tipo_alimento"), rs.getDouble("cantidad_disponible"));
	            }

	            rs = stmt.executeQuery("SELECT tipo_alimento, cantidad_disponible FROM Inventario ORDER BY cantidad_disponible ASC LIMIT 1");
	            if (rs.next()) {
	                System.out.printf("🔻 Menor cantidad: %s (%.2f unidades)%n", rs.getString("tipo_alimento"), rs.getDouble("cantidad_disponible"));
	            }

	        } catch (SQLException e) {
	            System.out.println("❌ Error al mostrar el inventario: " + e.getMessage());
	        }
	    }
}


