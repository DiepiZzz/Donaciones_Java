package com.donaciones; 

import com.donaciones.util.DatabaseConnection; 
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane; 

public class MainTestConnection {

    public static void main(String[] args) {
        System.out.println("Intentando obtener una conexión a la base de datos...");
        Connection testConnection = null;

        try {
            DatabaseConnection dbInstance = DatabaseConnection.getInstancia();

            testConnection = dbInstance.conectarBase();

            if (testConnection != null && !testConnection.isClosed()) {
                System.out.println("¡Conexión a la base de datos exitosa!");
                JOptionPane.showMessageDialog(null, "¡Conexión a la base de datos exitosa!");


            } else {
                String errorMessage = "Error: No se pudo establecer la conexión a la base de datos o la conexión está cerrada.";
                System.err.println(errorMessage);
                JOptionPane.showMessageDialog(null, errorMessage, "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            // Captura las excepciones SQL que podrían venir de conectarBase()
            String errorMessage = "Error de SQL al intentar conectar: " + e.getMessage();
            System.err.println(errorMessage);
            JOptionPane.showMessageDialog(null, errorMessage, "Error de SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            String errorMessage = "Error inesperado: " + e.getMessage();
            System.err.println(errorMessage);
            JOptionPane.showMessageDialog(null, errorMessage, "Error General", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            
             try {
                 if (testConnection != null && !testConnection.isClosed()) {
                   testConnection.close();
                     System.out.println("Conexión de prueba cerrada.");
                }
            } catch (SQLException e) {
                 System.err.println("Error al cerrar la conexión de prueba: " + e.getMessage());
            }
        }
    }
}