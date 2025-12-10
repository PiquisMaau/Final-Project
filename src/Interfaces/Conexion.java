/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

/**
 *
 * @author Sebas
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/segundo_proyecto_final";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";

    private static Connection conexion;

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(" Conexión exitosa a MySQL");
            }
        } catch (SQLException e) {
            System.out.println(" Error SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(" No se encontró el Driver de MySQL");
        }
        return conexion;
    }
}

