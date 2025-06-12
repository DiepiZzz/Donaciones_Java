package com.donaciones; // Ajusta tu paquete base

import com.donaciones.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class MainTestConnection {

    public static void main(String[] args) {
        System.out.println("Intentando conectar a la base de datos...");
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("¡Conexión a la base de datos exitosa!");
                
            } else {
                System.out.println("Error: No se pudo establecer la conexión (connection es null).");
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL al intentar conectar: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) { 
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
