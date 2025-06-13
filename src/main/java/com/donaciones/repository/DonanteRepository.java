package com.donaciones.repository;

import com.donaciones.model.Donante;
import com.donaciones.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DonanteRepository {

    public void insert(Donante donante) throws SQLException {
        String sql = "INSERT INTO Donantes (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase(); // Obtener la conexión
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar donante: " + e.getMessage());
            throw e; // Relanza la excepción para que la capa superior la maneje
        }
    }

    public Donante findById(int id) throws SQLException {
        String sql = "SELECT * FROM Donantes WHERE id_donante = ?";
        Donante donante = null;
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
                        donante = new Donante(
                            rs.getInt("id_donante"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("telefono")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar donante por ID: " + e.getMessage());
            throw e;
        }
        return donante;
    }

    public List<Donante> findAll() throws SQLException {
        String sql = "SELECT * FROM Donantes";
        List<Donante> donantes = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (Statement stmt = conn.createStatement();
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los donantes: " + e.getMessage());
            throw e;
        }
        return donantes;
    }

    public void update(Donante donante) throws SQLException {
        String sql = "UPDATE Donantes SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE id_donante = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, donante.getNombre());
                pstmt.setString(2, donante.getApellido());
                pstmt.setString(3, donante.getEmail());
                pstmt.setString(4, donante.getTelefono());
                pstmt.setInt(5, donante.getIdDonante());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar donante: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Donantes WHERE id_donante = ?";
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
            System.err.println("Error al eliminar donante: " + e.getMessage());
            throw e;
        }
    }
}