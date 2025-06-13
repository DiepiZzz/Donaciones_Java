// Archivo: com/donaciones/repository/InstitucionBeneficaRepository.java
package com.donaciones.repository;

import com.donaciones.model.InstitucionBenefica;
import com.donaciones.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InstitucionBeneficaRepository {

    public void insert(InstitucionBenefica institucion) throws SQLException {
        String sql = "INSERT INTO Instituciones_Beneficas (nombre, direccion, contacto, telefono) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, institucion.getNombre());
                pstmt.setString(2, institucion.getDireccion());
                pstmt.setString(3, institucion.getContacto());
                pstmt.setString(4, institucion.getTelefono());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            institucion.setIdInstitucion(generatedKeys.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar institución benéfica: " + e.getMessage());
            throw e;
        }
    }

    public InstitucionBenefica findById(int id) throws SQLException {
        String sql = "SELECT * FROM Instituciones_Beneficas WHERE id_institucion = ?";
        InstitucionBenefica institucion = null;
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
                        institucion = new InstitucionBenefica(
                            rs.getInt("id_institucion"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("contacto"),
                            rs.getString("telefono")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar institución benéfica por ID: " + e.getMessage());
            throw e;
        }
        return institucion;
    }

    public List<InstitucionBenefica> findAll() throws SQLException {
        String sql = "SELECT * FROM Instituciones_Beneficas";
        List<InstitucionBenefica> instituciones = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    InstitucionBenefica institucion = new InstitucionBenefica(
                        rs.getInt("id_institucion"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("contacto"),
                        rs.getString("telefono")
                    );
                    instituciones.add(institucion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las instituciones benéficas: " + e.getMessage());
            throw e;
        }
        return instituciones;
    }

    public void update(InstitucionBenefica institucion) throws SQLException {
        String sql = "UPDATE Instituciones_Beneficas SET nombre = ?, direccion = ?, contacto = ?, telefono = ? WHERE id_institucion = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, institucion.getNombre());
                pstmt.setString(2, institucion.getDireccion());
                pstmt.setString(3, institucion.getContacto());
                pstmt.setString(4, institucion.getTelefono());
                pstmt.setInt(5, institucion.getIdInstitucion());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar institución benéfica: " + e.getMessage());
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Instituciones_Beneficas WHERE id_institucion = ?";
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
            System.err.println("Error al eliminar institución benéfica: " + e.getMessage());
            throw e;
        }
    }
}