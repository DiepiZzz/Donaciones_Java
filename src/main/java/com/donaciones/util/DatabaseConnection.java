package com.donaciones.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    private static Connection conexion;
    private static DatabaseConnection instancia;
    private static final String URL = "jdbc:mysql://localhost:3306/donaciones"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 
    
    private DatabaseConnection(){
        
    }

    public Connection conectarBase(){
   
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            return conexion;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,"Error" + e);
        }
        return conexion;
    }

    public void cerrarBase() throws SQLException{
        try{
            conexion.close();
            JOptionPane.showConfirmDialog(null, "Close exitoso");
            
        }catch (Exception e){
            JOptionPane.showConfirmDialog(null,"Error" + e);
            conexion.close();
        }finally{
            conexion.close();
        }
    }

    public static DatabaseConnection getInstancia(){
        if(instancia == null){
            instancia = new DatabaseConnection();
        }
        return instancia;
    }
}
