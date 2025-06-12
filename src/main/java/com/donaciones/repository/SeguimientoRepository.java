
package com.donaciones.repository;

import com.donaciones.model.Seguimiento;
import com.donaciones.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoRepository {

    public void insert(Seguimiento seguimiento) {
        String sql = "INSERT INTO Seguimiento (id_entrega, observaciones, fecha_seguimiento) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, seguimiento.getIdEntrega());
            pstmt.setString(2, seguimiento.getObservaciones());
            pstmt.setDate(3, new java.sql.Date(seguimiento.getFechaSeguimiento().getTime()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        seguimiento.setIdSeguimiento(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar seguimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Seguimiento findById(int id) {
        String sql = "SELECT * FROM Seguimiento WHERE id_seguimiento = ?";
        Seguimiento seguimiento = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    seguimiento = new Seguimiento(
                        rs.getInt("id_seguimiento"),
                        rs.getInt("id_entrega"),
                        rs.getString("observaciones"),
                        rs.getDate("fecha_seguimiento")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar seguimiento por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return seguimiento;
    }

    public List<Seguimiento> findAll() {
        String sql = "SELECT * FROM Seguimiento";
        List<Seguimiento> seguimientos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Seguimiento seguimiento = new Seguimiento(
                    rs.getInt("id_seguimiento"),
                    rs.getInt("id_entrega"),
                    rs.getString("observaciones"),
                    rs.getDate("fecha_seguimiento")
                );
                seguimientos.add(seguimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los seguimientos: " + e.getMessage());
            e.printStackTrace();
        }
        return seguimientos;
    }

    public void update(Seguimiento seguimiento) {
        String sql = "UPDATE Seguimiento SET id_entrega = ?, observaciones = ?, fecha_seguimiento = ? WHERE id_seguimiento = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seguimiento.getIdEntrega());
            pstmt.setString(2, seguimiento.getObservaciones());
            pstmt.setDate(3, new java.sql.Date(seguimiento.getFechaSeguimiento().getTime()));
            pstmt.setInt(4, seguimiento.getIdSeguimiento());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar seguimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Seguimiento WHERE id_seguimiento = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar seguimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
