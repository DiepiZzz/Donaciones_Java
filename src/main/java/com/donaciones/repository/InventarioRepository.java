package com.donaciones.repository;

import com.donaciones.model.Inventario;
import com.donaciones.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class InventarioRepository {

    public void insert(Inventario inventario) throws SQLException {
        String sql = "INSERT INTO Inventario (tipo_alimento, cantidad_disponible, fecha_caducidad, id_donacion) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar en inventario: " + e.getMessage());
            throw e;
        }
    }

    public Inventario findById(int id) throws SQLException {
        String sql = "SELECT * FROM Inventario WHERE id_inventario = ?";
        Inventario inventario = null;
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
                        inventario = new Inventario(
                            rs.getInt("id_inventario"),
                            rs.getString("tipo_alimento"),
                            rs.getBigDecimal("cantidad_disponible"),
                            rs.getDate("fecha_caducidad"),
                            rs.getInt("id_donacion")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar inventario por ID: " + e.getMessage());
            throw e;
        }
        return inventario;
    }

    public List<Inventario> findAll() throws SQLException {
        String sql = "SELECT * FROM Inventario";
        List<Inventario> inventarios = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (Statement stmt = conn.createStatement();
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todo el inventario: " + e.getMessage());
            throw e;
        }
        return inventarios;
    }

    public void update(Inventario inventario) throws SQLException {
        String sql = "UPDATE Inventario SET tipo_alimento = ?, cantidad_disponible = ?, fecha_caducidad = ?, id_donacion = ? WHERE id_inventario = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, inventario.getTipoAlimento());
                pstmt.setBigDecimal(2, inventario.getCantidadDisponible());
                pstmt.setDate(3, inventario.getFechaCaducidad() != null ? new java.sql.Date(inventario.getFechaCaducidad().getTime()) : null);
                pstmt.setInt(4, inventario.getIdDonacion());
                pstmt.setInt(5, inventario.getIdInventario());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar inventario: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Inventario WHERE id_inventario = ?";
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
            System.err.println("Error al eliminar inventario: " + e.getMessage());
            throw e;
        }
    }
}