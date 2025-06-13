// Archivo: com/donaciones/repository/EntregaRepository.java
package com.donaciones.repository;

import com.donaciones.model.Entrega;
import com.donaciones.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date; // Importa java.util.Date si tu modelo Entrega usa java.util.Date

public class EntregaRepository {

    /**
     * Inserta un nuevo registro de entrega en la base de datos.
     * El ID de la entrega se generará automáticamente.
     */
    public void insert(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO Entrega (id_asignacion, id_beneficiario, cantidad_entregada, fecha_entrega) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, entrega.getIdAsignacion());
                pstmt.setInt(2, entrega.getIdBeneficiario());
                pstmt.setBigDecimal(3, entrega.getCantidadEntregada());
                // Convierte java.util.Date a java.sql.Date
                pstmt.setDate(4, entrega.getFechaEntrega() != null ? new java.sql.Date(entrega.getFechaEntrega().getTime()) : null);

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            entrega.setIdEntrega(generatedKeys.getInt(1)); // Asigna el ID generado al objeto
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar entrega: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca un registro de entrega por su ID.
     */
    public Entrega findById(int id) throws SQLException {
        String sql = "SELECT * FROM Entrega WHERE id_entrega = ?";
        Entrega entrega = null;
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
                        entrega = new Entrega(
                            rs.getInt("id_entrega"),
                            rs.getInt("id_asignacion"),
                            rs.getInt("id_beneficiario"),
                            rs.getBigDecimal("cantidad_entregada"),
                            rs.getDate("fecha_entrega")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar entrega por ID: " + e.getMessage());
            throw e;
        }
        return entrega;
    }

    /**
     * Busca todos los registros de entrega.
     */
    public List<Entrega> findAll() throws SQLException {
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT * FROM Entrega";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Entrega entrega = new Entrega(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_asignacion"),
                        rs.getInt("id_beneficiario"),
                        rs.getBigDecimal("cantidad_entregada"),
                        rs.getDate("fecha_entrega")
                    );
                    entregas.add(entrega);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las entregas: " + e.getMessage());
            throw e;
        }
        return entregas;
    }

    /**
     * Actualiza un registro de entrega existente.
     */
    public void update(Entrega entrega) throws SQLException {
        String sql = "UPDATE Entrega SET id_asignacion = ?, id_beneficiario = ?, cantidad_entregada = ?, fecha_entrega = ? WHERE id_entrega = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, entrega.getIdAsignacion());
                pstmt.setInt(2, entrega.getIdBeneficiario());
                pstmt.setBigDecimal(3, entrega.getCantidadEntregada());
                pstmt.setDate(4, entrega.getFechaEntrega() != null ? new java.sql.Date(entrega.getFechaEntrega().getTime()) : null);
                pstmt.setInt(5, entrega.getIdEntrega());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar entrega: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Elimina un registro de entrega por su ID.
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Entrega WHERE id_entrega = ?";
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
            System.err.println("Error al eliminar entrega: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Busca todas las entregas asociadas a un beneficiario específico.
     * @param idBeneficiario El ID del beneficiario.
     * @return Una lista de objetos Entrega.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Entrega> findByBeneficiarioId(int idBeneficiario) throws SQLException {
        List<Entrega> entregas = new ArrayList<>();
        String sql = "SELECT * FROM Entrega WHERE id_beneficiario = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idBeneficiario);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Entrega entrega = new Entrega(
                            rs.getInt("id_entrega"),
                            rs.getInt("id_asignacion"),
                            rs.getInt("id_beneficiario"),
                            rs.getBigDecimal("cantidad_entregada"),
                            rs.getDate("fecha_entrega")
                        );
                        entregas.add(entrega);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar entregas por ID de beneficiario: " + e.getMessage());
            throw e;
        }
        return entregas;
    }
}