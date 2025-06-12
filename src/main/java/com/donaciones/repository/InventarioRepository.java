
package com.donaciones.repository;

import com.donaciones.model.Inventario;
import com.donaciones.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class InventarioRepository {

    public void insert(Inventario inventario) {
        String sql = "INSERT INTO Inventario (tipo_alimento, cantidad_disponible, fecha_caducidad, id_donacion) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, inventario.getTipoAlimento());
            pstmt.setBigDecimal(2, inventario.getCantidadDisponible());
            pstmt.setDate(3, inventario.getFechaCaducidad() != null ? new java.sql.Date(inventario.getFechaCaducidad().getTime()) : null);
            pstmt.setInt(4, inventario.getIdDonacion());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        inventario.setIdInventario(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar en inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Inventario findById(int id) {
        String sql = "SELECT * FROM Inventario WHERE id_inventario = ?";
        Inventario inventario = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inventario = new Inventario(
                        rs.getInt("id_inventario"),
                        rs.getString("tipo_alimento"),
                        rs.getBigDecimal("cantidad_disponible"),
                        rs.getDate("fecha_caducidad"),
                        rs.getInt("id_donacion")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar inventario por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return inventario;
    }

    public List<Inventario> findAll() {
        String sql = "SELECT * FROM Inventario";
        List<Inventario> inventarios = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Inventario inventario = new Inventario(
                    rs.getInt("id_inventario"),
                    rs.getString("tipo_alimento"),
                    rs.getBigDecimal("cantidad_disponible"),
                    rs.getDate("fecha_caducidad"),
                    rs.getInt("id_donacion")
                );
                inventarios.add(inventario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todo el inventario: " + e.getMessage());
            e.printStackTrace();
        }
        return inventarios;
    }

    public void update(Inventario inventario) {
        String sql = "UPDATE Inventario SET tipo_alimento = ?, cantidad_disponible = ?, fecha_caducidad = ?, id_donacion = ? WHERE id_inventario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inventario.getTipoAlimento());
            pstmt.setBigDecimal(2, inventario.getCantidadDisponible());
            pstmt.setDate(3, inventario.getFechaCaducidad() != null ? new java.sql.Date(inventario.getFechaCaducidad().getTime()) : null);
            pstmt.setInt(4, inventario.getIdDonacion());
            pstmt.setInt(5, inventario.getIdInventario());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Inventario WHERE id_inventario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
