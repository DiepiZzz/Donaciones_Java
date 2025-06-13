// Archivo: com/donaciones/repository/InventarioRepository.java
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
import java.util.Date; // Importa java.util.Date

public class InventarioRepository {

    /**
     * Inserta un nuevo registro de inventario en la base de datos.
     * El ID del inventario se generará automáticamente.
     */
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
                // Convierte java.util.Date a java.sql.Date
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

    /**
     * Busca un registro de inventario por su ID.
     */
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

    /**
     * Busca todos los registros de inventario.
     */
    public List<Inventario> findAll() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String sql = "SELECT * FROM Inventario";
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
            System.err.println("Error al obtener todos los inventarios: " + e.getMessage());
            throw e;
        }
        return inventarios;
    }

    /**
     * Actualiza un registro de inventario existente.
     */
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

    /**
     * Elimina un registro de inventario por su ID.
     */
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

    /**
     * Busca un ítem de inventario por su tipo de alimento.
     * Se usa para verificar si un tipo de alimento ya existe en el inventario.
     */
    public Inventario findByTipoAlimento(String tipoAlimento) throws SQLException {
        String sql = "SELECT * FROM Inventario WHERE tipo_alimento = ?";
        Inventario inventario = null;
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, tipoAlimento);
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
            System.err.println("Error al buscar inventario por tipo de alimento: " + e.getMessage());
            throw e;
        }
        return inventario;
    }

    /**
     * **Nuevo Método: findByDonacionId**
     * Busca un ítem de inventario asociado a una donación específica.
     * Asume que hay una relación 1:1 o 1:N donde puedes encontrar un ítem de inventario por el ID de su donación.
     * Si múltiples ítems de inventario pueden estar asociados a una misma donación, este método solo devolverá el primero.
     * Podrías necesitar devolver una List<Inventario> si ese es el caso.
     */
    public Inventario findByDonacionId(int donacionId) throws SQLException {
        String sql = "SELECT * FROM Inventario WHERE id_donacion = ?";
        Inventario inventario = null;
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, donacionId);
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
            System.err.println("Error al buscar inventario por ID de donación: " + e.getMessage());
            throw e;
        }
        return inventario;
    }

    /**
     * **Tu método 'upsertTipoAlimento'**
     * Crea un nuevo tipo de alimento si no existe, o actualiza la cantidad
     * de un tipo de alimento existente sumando la nueva cantidad.
     * Devuelve el objeto Inventario actualizado o recién creado con su ID.
     */
    public Inventario upsertTipoAlimento(Inventario inventarioNuevo) throws IllegalArgumentException, SQLException {
        if (inventarioNuevo == null) {
            throw new IllegalArgumentException("El objeto Inventario no puede ser nulo.");
        }
        if (inventarioNuevo.getTipoAlimento() == null || inventarioNuevo.getTipoAlimento().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de alimento no puede estar vacío.");
        }
        if (inventarioNuevo.getCantidadDisponible() == null || inventarioNuevo.getCantidadDisponible().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cantidad a ingresar no puede ser negativa.");
        }

        Connection conn = null;
        Inventario inventarioExistente = null;

        try {
            conn = DatabaseConnection.getInstancia().conectarBase();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión a la base de datos.");
            }

            // Intenta buscar el alimento por su tipo
            inventarioExistente = findByTipoAlimento(inventarioNuevo.getTipoAlimento());

            if (inventarioExistente != null) {
                // Si existe, actualiza su cantidad
                System.out.println("Alimento '" + inventarioNuevo.getTipoAlimento() + "' existente. Sumando cantidad.");
                BigDecimal nuevaCantidadTotal = inventarioExistente.getCantidadDisponible().add(inventarioNuevo.getCantidadDisponible());
                inventarioExistente.setCantidadDisponible(nuevaCantidadTotal);

                // Reutiliza el método 'update' existente
                update(inventarioExistente);
                System.out.println("Cantidad de '" + inventarioNuevo.getTipoAlimento() + "' actualizada a: " + nuevaCantidadTotal);
                return inventarioExistente;
            } else {
                // Si no existe, inserta un nuevo registro
                System.out.println("Alimento '" + inventarioNuevo.getTipoAlimento() + "' no encontrado. Creando nuevo registro.");
                // Aquí, el id_donacion es crucial para un nuevo registro de inventario
                if (inventarioNuevo.getIdDonacion() <= 0) {
                     throw new IllegalArgumentException("ID de Donación es requerido para insertar un nuevo ítem de inventario.");
                }

                // Reutiliza el método 'insert' existente
                insert(inventarioNuevo);
                System.out.println("Nuevo ítem de inventario '" + inventarioNuevo.getTipoAlimento() + "' insertado con cantidad: " + inventarioNuevo.getCantidadDisponible() + ". ID: " + inventarioNuevo.getIdInventario());
                return inventarioNuevo;
            }
        } catch (SQLException e) {
            System.err.println("Error en la operación upsertTipoAlimento: " + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación en upsertTipoAlimento: " + e.getMessage());
            throw e;
        }
    }
}