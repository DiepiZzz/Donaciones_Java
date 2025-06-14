package com.donaciones.servicios;

import com.donaciones.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteService {

    public ReporteService() {
        
        
    }

    
    public Map<String, Object> getInstitucionMasDonaciones() throws SQLException {
        
        String sql = "SELECT ib.nombre AS nombre_institucion, SUM(d.cantidad) AS total_donado " + 
                     "FROM donaciones d " +
                     "JOIN asignacion a ON d.id_donacion = a.id_donacion " +
                     "JOIN instituciones_beneficas ib ON a.id_institucion = ib.id_institucion " + 
                     "GROUP BY ib.id_institucion, ib.nombre " + 
                     "HAVING SUM(d.cantidad) = (SELECT MAX(total_donado_sub) FROM " +
                                "(SELECT SUM(d2.cantidad) AS total_donado_sub " +
                                "FROM donaciones d2 " +
                                "JOIN asignacion a2 ON d2.id_donacion = a2.id_donacion " +
                                "GROUP BY a2.id_institucion) AS subquery_total)";

        Map<String, Object> result = null;
        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                result = new HashMap<>();
                result.put("nombreInstitucion", rs.getString("nombre_institucion"));
                result.put("totalDonado", rs.getBigDecimal("total_donado"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la institución con más donaciones: " + e.getMessage());
            throw e;
        }
        return result;
    }

    
    public Map<String, Object> getDonanteMayorAporte() throws SQLException {
        String sql = "SELECT d.nombre AS nombre_donante, d.apellido AS apellido_donante, SUM(don.cantidad) AS total_aportado " +
                     "FROM donaciones don " +
                     "JOIN donante d ON don.id_donante = d.id_donante " +
                     "GROUP BY d.id_donante, d.nombre, d.apellido " +
                     "HAVING SUM(don.cantidad) = (SELECT MAX(total_aportado_sub) FROM " +
                                "(SELECT SUM(cantidad) AS total_aportado_sub FROM donaciones GROUP BY id_donante) AS subquery_total)";

        Map<String, Object> result = null;
        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                result = new HashMap<>();
                result.put("nombreDonante", rs.getString("nombre_donante") + " " + rs.getString("apellido_donante"));
                result.put("totalAportado", rs.getBigDecimal("total_aportado"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el donante con mayor aporte: " + e.getMessage());
            throw e;
        }
        return result;
    }

    
    public Map<String, Object> getTipoDonacionMasFrecuente() throws SQLException {
        String sql = "SELECT tipo_donacion, COUNT(*) AS frecuencia " +
                     "FROM donaciones " +
                     "GROUP BY tipo_donacion " +
                     "HAVING COUNT(*) = (SELECT MAX(frecuencia_sub) FROM " +
                                "(SELECT COUNT(*) AS frecuencia_sub FROM donaciones GROUP BY tipo_donacion) AS subquery_frecuencia)";

        Map<String, Object> result = null;
        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                result = new HashMap<>();
                result.put("tipoDonacion", rs.getString("tipo_donacion"));
                result.put("frecuencia", rs.getInt("frecuencia"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el tipo de donación más frecuente: " + e.getMessage());
            throw e;
        }
        return result;
    }

    
    public List<Map<String, Object>> getTotalEntregadoPorInstitucion() throws SQLException {
        
        String sql = "SELECT ib.nombre AS nombre_institucion, SUM(epa.cantidad_entregada_total) AS total_entregado " + 
                     "FROM ( " +
                     "    SELECT a.id_asignacion, a.id_donacion, e.cantidad_entregada AS cantidad_entregada_total " +
                     "    FROM entrega e " +
                     "    JOIN asignacion a ON e.id_asignacion = a.id_asignacion " +
                     ") AS epa " +
                     "JOIN donaciones d ON epa.id_donacion = d.id_donacion " +
                     "JOIN asignacion a_inst ON d.id_donacion = a_inst.id_donacion AND epa.id_asignacion = a_inst.id_asignacion " +
                     "JOIN instituciones_beneficas ib ON a_inst.id_institucion = ib.id_institucion " + 
                     "GROUP BY ib.id_institucion, ib.nombre " + 
                     "ORDER BY total_entregado DESC";


        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("nombreInstitucion", rs.getString("nombre_institucion"));
                row.put("totalEntregado", rs.getBigDecimal("total_entregado"));
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el total de productos entregados por institución: " + e.getMessage());
            throw e;
        }
        return results;
    }

    
    public List<Map<String, Object>> getTotalDonacionesPorTipo() throws SQLException {
        String sql = "SELECT tipo_donacion, SUM(cantidad) AS total_donado " +
                     "FROM donaciones " +
                     "GROUP BY tipo_donacion " +
                     "ORDER BY total_donado DESC";

        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("tipoDonacion", rs.getString("tipo_donacion"));
                row.put("totalDonado", rs.getBigDecimal("total_donado"));
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el total de donaciones por tipo: " + e.getMessage());
            throw e;
        }
        return results;
    }

    
    public List<Map<String, Object>> getInstitucionesConMasAsignaciones() throws SQLException {
        
        String sql = "SELECT ib.nombre AS nombre_institucion, COUNT(a.id_asignacion) AS num_asignaciones " + 
                     "FROM asignaciones a " +
                     "JOIN instituciones_beneficas ib ON a.id_institucion = ib.id_institucion " + 
                     "GROUP BY ib.id_institucion, ib.nombre " + 
                     "ORDER BY num_asignaciones DESC";

        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("nombreInstitucion", rs.getString("nombre_institucion"));
                row.put("numAsignaciones", rs.getInt("num_asignaciones"));
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener instituciones con más asignaciones: " + e.getMessage());
            throw e;
        }
        return results;
    }
}