package com.donaciones.repository;

import com.donaciones.model.Asignacion;
import com.donaciones.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class AsignacionRepository {

    public void insert(Asignacion asignacion) throws SQLException {
        String sql = "INSERT INTO Asignaciones (id_donacion, id_institucion, cantidad_asignada, fecha_asignacion) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, asignacion.getIdDonacion());
                pstmt.setInt(2, asignacion.getIdInstitucion());
                pstmt.setBigDecimal(3, asignacion.getCantidadAsignada());
                pstmt.setDate(4, new java.sql.Date(asignacion.getFechaAsignacion().getTime()));

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            asignacion.setIdAsignacion(generatedKeys.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar asignación: " + e.getMessage());
            throw e;
        }
    }

    public Asignacion findById(int id) throws SQLException {
        String sql = "SELECT * FROM Asignaciones WHERE id_asignacion = ?";
        Asignacion asignacion = null;
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
                        asignacion = new Asignacion(
                            rs.getInt("id_asignacion"),
                            rs.getInt("id_donacion"),
                            rs.getInt("id_institucion"),
                            rs.getBigDecimal("cantidad_asignada"),
                            rs.getDate("fecha_asignacion")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar asignación por ID: " + e.getMessage());
            throw e;
        }
        return asignacion;
    }

    public List<Asignacion> findAll() throws SQLException {
        String sql = "SELECT * FROM Asignaciones";
        List<Asignacion> asignaciones = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Asignacion asignacion = new Asignacion(
                        rs.getInt("id_asignacion"),
                        rs.getInt("id_donacion"),
                        rs.getInt("id_institucion"),
                        rs.getBigDecimal("cantidad_asignada"),
                        rs.getDate("fecha_asignacion")
                    );
                    asignaciones.add(asignacion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las asignaciones: " + e.getMessage());
            throw e;
        }
        return asignaciones;
    }

    public void update(Asignacion asignacion) throws SQLException {
        String sql = "UPDATE Asignaciones SET id_donacion = ?, id_institucion = ?, cantidad_asignada = ?, fecha_asignacion = ? WHERE id_asignacion = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, asignacion.getIdDonacion());
                pstmt.setInt(2, asignacion.getIdInstitucion());
                pstmt.setBigDecimal(3, asignacion.getCantidadAsignada());
                pstmt.setDate(4, new java.sql.Date(asignacion.getFechaAsignacion().getTime()));
                pstmt.setInt(5, asignacion.getIdAsignacion());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar asignación: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Asignaciones WHERE id_asignacion = ?";
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
            System.err.println("Error al eliminar asignación: " + e.getMessage());
            throw e;
        }
    }
}