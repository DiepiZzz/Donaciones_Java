
package com.donaciones.repository;

import com.donaciones.model.Donacion;
import com.donaciones.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class DonacionRepository {

    public void insert(Donacion donacion) {
        String sql = "INSERT INTO Donaciones (tipo_donacion, cantidad, fecha_donacion, id_donante) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, donacion.getTipoDonacion());
            pstmt.setBigDecimal(2, donacion.getCantidad());
            pstmt.setDate(3, new java.sql.Date(donacion.getFechaDonacion().getTime()));
            pstmt.setInt(4, donacion.getIdDonante());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        donacion.setIdDonacion(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar donación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Donacion findById(int id) {
        String sql = "SELECT * FROM Donaciones WHERE id_donacion = ?";
        Donacion donacion = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    donacion = new Donacion(
                        rs.getInt("id_donacion"),
                        rs.getString("tipo_donacion"),
                        rs.getBigDecimal("cantidad"),
                        rs.getDate("fecha_donacion"), 
                        rs.getInt("id_donante")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar donación por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return donacion;
    }

    public List<Donacion> findAll() {
        String sql = "SELECT * FROM Donaciones";
        List<Donacion> donaciones = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Donacion donacion = new Donacion(
                    rs.getInt("id_donacion"),
                    rs.getString("tipo_donacion"),
                    rs.getBigDecimal("cantidad"),
                    rs.getDate("fecha_donacion"),
                    rs.getInt("id_donante")
                );
                donaciones.add(donacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las donaciones: " + e.getMessage());
            e.printStackTrace();
        }
        return donaciones;
    }

    public void update(Donacion donacion) {
        String sql = "UPDATE Donaciones SET tipo_donacion = ?, cantidad = ?, fecha_donacion = ?, id_donante = ? WHERE id_donacion = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, donacion.getTipoDonacion());
            pstmt.setBigDecimal(2, donacion.getCantidad());
            pstmt.setDate(3, new java.sql.Date(donacion.getFechaDonacion().getTime()));
            pstmt.setInt(4, donacion.getIdDonante());
            pstmt.setInt(5, donacion.getIdDonacion());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar donación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Donaciones WHERE id_donacion = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar donación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
