/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import Clases.Estudiantes;
import Interfaces.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Sebas
 */
public class EstudianteSCRUD extends CRUD<Estudiantes> {

    @Override
    public boolean create(Estudiantes e) {
        String sql = "INSERT INTO estudiantes (idEstudiantes, estcedula, estnombre, estapellido, estedad, esttelefono) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getIdEstudiantes());
            ps.setString(2, e.getEstcedula());
            ps.setString(3, e.getEstnombre());
            ps.setString(4, e.getEstapellido());
            ps.setInt(5, e.getEstedad());
            ps.setString(6, e.getEsttelefono());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error al insertar estudiante: " + ex.getMessage());
            return false;
        }
    }

    public void reordenarIDs() {
    String sql1 = "SET @count = 0";
    String sql2 = "UPDATE estudiantes SET idEstudiantes = @count:= @count + 1";
    String sql3 = "ALTER TABLE estudiantes AUTO_INCREMENT = 1";

    try (Connection con = Conexion.getConexion()) {
        con.prepareStatement(sql1).execute();
        con.prepareStatement(sql2).execute();
        con.prepareStatement(sql3).execute();
    } catch (SQLException e) {
        System.out.println("Error al reordenar IDs: " + e.getMessage());
    }
}

    @Override
    public Estudiantes read(int id) {
        String sql = "SELECT * FROM estudiantes WHERE idEstudiantes = ?";
        Estudiantes e = null;

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                e = new Estudiantes();
                e.setIdEstudiantes(rs.getInt("idEstudiantes"));
                e.setEstcedula(rs.getString("estcedula"));
                e.setEstnombre(rs.getString("estnombre"));
                e.setEstapellido(rs.getString("estapellido"));
                e.setEstedad(rs.getInt("estedad"));
                e.setEsttelefono(rs.getString("esttelefono"));
            }

        } catch (SQLException ex) {
            System.out.println("Error READ estudiante: " + ex.getMessage());
        }

        return e;
    }
    
   public List<Estudiantes> readAll() {
    List<Estudiantes> lista = new ArrayList<>();
    String sql = "SELECT * FROM estudiantes";

    try {
        Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Estudiantes e = new Estudiantes();
            e.setIdEstudiantes(rs.getInt("idEstudiantes"));
            e.setEstcedula(rs.getString("estcedula"));
            e.setEstnombre(rs.getString("estnombre"));
            e.setEstapellido(rs.getString("estapellido"));
            e.setEstedad(rs.getInt("estedad"));
            e.setEsttelefono(rs.getString("esttelefono"));
            lista.add(e);
        }

    } catch (SQLException ex) {
        System.out.println("Error READ ALL: " + ex.getMessage());
    }

    return lista;
}


    @Override
    public boolean update(Estudiantes e) {
        String sql = "UPDATE estudiantes SET estcedula=?, estnombre=?, estapellido=?, estedad=?, esttelefono=? WHERE idEstudiantes=?";
        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, e.getEstcedula());
            ps.setString(2, e.getEstnombre());
            ps.setString(3, e.getEstapellido());
            ps.setInt(4, e.getEstedad());
            ps.setString(5, e.getEsttelefono());
            ps.setInt(6, e.getIdEstudiantes());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error UPDATE estudiante: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM estudiantes WHERE idEstudiantes=?";
        try {
            Connection con = (Connection) Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error DELETE estudiante: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Estudiantes> listAll() {
        String sql = "SELECT * FROM estudiantes";
        List<Estudiantes> lista = new ArrayList<>();

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Estudiantes e = new Estudiantes();
                e.setIdEstudiantes(rs.getInt("idEstudiantes"));
                e.setEstcedula(rs.getString("estcedula"));
                e.setEstnombre(rs.getString("estnombre"));
                e.setEstapellido(rs.getString("estapellido"));
                e.setEstedad(rs.getInt("estedad"));
                e.setEsttelefono(rs.getString("esttelefono"));

                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error LIST estudiantes: " + ex.getMessage());
        }

        return lista;
    }
}
