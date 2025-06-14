package com.donaciones.servicios;

import com.donaciones.util.DatabaseConnection;
import com.donaciones.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt; 

public class AuthService {

    
    public Usuario autenticar(String username, String password) throws SQLException {
        String sql = "SELECT id_usuario, username, password FROM usuarios WHERE username = ?";
        Usuario usuarioAutenticado = null;

        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");
                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        usuarioAutenticado = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("username"),
                            rs.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de autenticación: " + e.getMessage());
            throw e;
        }
        return usuarioAutenticado;
    }


    
    public boolean crearUsuario(String username, String password) throws SQLException {
        
        if (existeUsuario(username)) { 
            System.out.println("El nombre de usuario '" + username + "' ya está en uso. Por favor, elija otro.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            throw e;
        }
    }


    
    public boolean existeUsuario(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        try (Connection conn = DatabaseConnection.getInstancia().conectarBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de usuario: " + e.getMessage());
            throw e; 
        }
        return false; 
    }
}