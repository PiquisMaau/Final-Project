/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;


/**
 *
 * @author Sebas
 */
public class TestConexion {

      public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(" Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.out.println(" No se encontró el driver MySQL.");
        }
    }
}



