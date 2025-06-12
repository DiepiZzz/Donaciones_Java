
package com.donaciones.repository;

import com.donaciones.model.Donante;
import com.donaciones.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonanteRepository {

    public void insert(Donante donante) {
        String sql = "INSERT INTO Donantes (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, donante.getNombre());
            pstmt.setString(2, donante.getApellido());
            pstmt.setString(3, donante.getEmail());
            pstmt.setString(4, donante.getTelefono());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        donante.setIdDonante(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar donante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Donante findById(int id) {
        String sql = "SELECT * FROM Donantes WHERE id_donante = ?";
        Donante donante = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    donante = new Donante(
                        rs.getInt("id_donante"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar donante por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return donante;
    }

    public List<Donante> findAll() {
        String sql = "SELECT * FROM Donantes";
        List<Donante> donantes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Donante donante = new Donante(
                    rs.getInt("id_donante"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("telefono")
                );
                donantes.add(donante);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los donantes: " + e.getMessage());
            e.printStackTrace();
        }
        return donantes;
    }

    public void update(Donante donante) {
        String sql = "UPDATE Donantes SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE id_donante = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, donante.getNombre());
            pstmt.setString(2, donante.getApellido());
            pstmt.setString(3, donante.getEmail());
            pstmt.setString(4, donante.getTelefono());
            pstmt.setInt(5, donante.getIdDonante());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar donante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Donantes WHERE id_donante = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar donante: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
