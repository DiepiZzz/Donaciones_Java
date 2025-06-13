// Archivo: com/donaciones/repository/SeguimientoRepository.java
package com.donaciones.repository;

import com.donaciones.model.Seguimiento;
import com.donaciones.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoRepository {

    public void insert(Seguimiento seguimiento) throws SQLException {
        String sql = "INSERT INTO Seguimiento (id_entrega, observaciones, fecha_seguimiento) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar seguimiento: " + e.getMessage());
            throw e;
        }
    }

    public Seguimiento findById(int id) throws SQLException {
        String sql = "SELECT * FROM Seguimiento WHERE id_seguimiento = ?";
        Seguimiento seguimiento = null;
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
                        seguimiento = new Seguimiento(
                            rs.getInt("id_seguimiento"),
                            rs.getInt("id_entrega"),
                            rs.getString("observaciones"),
                            rs.getDate("fecha_seguimiento")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar seguimiento por ID: " + e.getMessage());
            throw e;
        }
        return seguimiento;
    }

    public List<Seguimiento> findAll() throws SQLException {
        String sql = "SELECT * FROM Seguimiento";
        List<Seguimiento> seguimientos = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (Statement stmt = conn.createStatement();
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los seguimientos: " + e.getMessage());
            throw e;
        }
        return seguimientos;
    }

    public void update(Seguimiento seguimiento) throws SQLException {
        String sql = "UPDATE Seguimiento SET id_entrega = ?, observaciones = ?, fecha_seguimiento = ? WHERE id_seguimiento = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, seguimiento.getIdEntrega());
                pstmt.setString(2, seguimiento.getObservaciones());
                pstmt.setDate(3, new java.sql.Date(seguimiento.getFechaSeguimiento().getTime()));
                pstmt.setInt(4, seguimiento.getIdSeguimiento());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar seguimiento: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Seguimiento WHERE id_seguimiento = ?";
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
            System.err.println("Error al eliminar seguimiento: " + e.getMessage());
            throw e;
        }
    }
}