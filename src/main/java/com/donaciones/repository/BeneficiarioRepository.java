// Archivo: com/donaciones/repository/BeneficiarioRepository.java
package com.donaciones.repository;

import com.donaciones.model.Beneficiario;
import com.donaciones.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BeneficiarioRepository {

    public void insert(Beneficiario beneficiario) throws SQLException {
        String sql = "INSERT INTO Beneficiarios (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar beneficiario: " + e.getMessage());
            throw e;
        }
    }

    public Beneficiario findById(int id) throws SQLException {
        String sql = "SELECT * FROM Beneficiarios WHERE id_beneficiario = ?";
        Beneficiario beneficiario = null;
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar beneficiario por ID: " + e.getMessage());
            throw e;
        }
        return beneficiario;
    }

    public List<Beneficiario> findAll() throws SQLException {
        String sql = "SELECT * FROM Beneficiarios";
        List<Beneficiario> beneficiarios = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (Statement stmt = conn.createStatement();
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los beneficiarios: " + e.getMessage());
            throw e;
        }
        return beneficiarios;
    }

    public void update(Beneficiario beneficiario) throws SQLException {
        String sql = "UPDATE Beneficiarios SET nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE id_beneficiario = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, beneficiario.getNombre());
                pstmt.setString(2, beneficiario.getApellido());
                pstmt.setString(3, beneficiario.getDireccion());
                pstmt.setString(4, beneficiario.getTelefono());
                pstmt.setInt(5, beneficiario.getIdBeneficiario());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar beneficiario: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Beneficiarios WHERE id_beneficiario = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar beneficiario: " + e.getMessage());
            throw e;
        }
    }
}