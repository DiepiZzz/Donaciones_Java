
package com.donaciones.repository;

import com.donaciones.model.Beneficiario;
import com.donaciones.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficiarioRepository {

    public void insert(Beneficiario beneficiario) {
        String sql = "INSERT INTO Beneficiarios (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, beneficiario.getNombre());
            pstmt.setString(2, beneficiario.getApellido());
            pstmt.setString(3, beneficiario.getDireccion());
            pstmt.setString(4, beneficiario.getTelefono());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        beneficiario.setIdBeneficiario(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar beneficiario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Beneficiario findById(int id) {
        String sql = "SELECT * FROM Beneficiarios WHERE id_beneficiario = ?";
        Beneficiario beneficiario = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    beneficiario = new Beneficiario(
                        rs.getInt("id_beneficiario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar beneficiario por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return beneficiario;
    }

    public List<Beneficiario> findAll() {
        String sql = "SELECT * FROM Beneficiarios";
        List<Beneficiario> beneficiarios = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Beneficiario beneficiario = new Beneficiario(
                    rs.getInt("id_beneficiario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
                );
                beneficiarios.add(beneficiario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los beneficiarios: " + e.getMessage());
            e.printStackTrace();
        }
        return beneficiarios;
    }

    public void update(Beneficiario beneficiario) {
        String sql = "UPDATE Beneficiarios SET nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE id_beneficiario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, beneficiario.getNombre());
            pstmt.setString(2, beneficiario.getApellido());
            pstmt.setString(3, beneficiario.getDireccion());
            pstmt.setString(4, beneficiario.getTelefono());
            pstmt.setInt(5, beneficiario.getIdBeneficiario());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar beneficiario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Beneficiarios WHERE id_beneficiario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar beneficiario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
