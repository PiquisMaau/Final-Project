/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import Clases.*;
import Interfaces.Conexion;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Sebas
 */
public class UsuarioCRUD {

   public Usuario login(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username=? AND password=?";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                return u;
            }

        } catch (SQLException ex) {
            System.out.println("Error LOGIN usuario: " + ex.getMessage());
        }

        return null;
    }

    public List<Usuario> listAll() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (SQLException ex) {
            System.out.println("Error LIST usuarios: " + ex.getMessage());
        }
        return lista;
    }

    public boolean create(Usuario u) {
        String sql = "INSERT INTO usuarios (idUsuario, username, password, rol) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getIdUsuario());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRol());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error CREATE usuario: " + ex.getMessage());
            return false;
        }
    }

    public boolean update(Usuario u) {
        String sql = "UPDATE usuarios SET username=?, password=?, rol=? WHERE idUsuario=?";
        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRol());
            ps.setInt(4, u.getIdUsuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error UPDATE usuario: " + ex.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM usuarios WHERE idUsuario=?";
        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error DELETE usuario: " + ex.getMessage());
            return false;
        }
    }
}

