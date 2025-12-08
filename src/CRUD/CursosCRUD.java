/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import Clases.*;
import Interfaces.Conexion;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Sebas
 */
public class CursosCRUD extends CRUD<Cursos> {

     @Override
    public boolean create(Cursos c) {
        String sql = "INSERT INTO cursos (Semestre, nombreCurso, paralelo) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getSemestres());
            ps.setString(2, c.getNombreCurso());
            ps.setString(3, c.getParalelo());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error CREATE curso: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Cursos read(int id) {
        String sql = "SELECT * FROM cursos WHERE Semestre = ?";
        Cursos c = null;

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cursos();
                c.setSemestres(rs.getInt("Semestre"));
                c.setNombreCurso(rs.getString("nombreCurso"));
                c.setParalelo(rs.getString("paralelo"));
            }

        } catch (SQLException ex) {
            System.out.println("Error READ curso: " + ex.getMessage());
        }

        return c;
    }

    @Override
    public boolean update(Cursos c) {
        String sql = "UPDATE cursos SET nombreCurso=?, paralelo=? WHERE Semestre=?";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombreCurso());
            ps.setString(2, c.getParalelo());
            ps.setInt(3, c.getSemestres());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error UPDATE curso: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM cursos WHERE Semestre=?";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error DELETE curso: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Cursos> listAll() {
        String sql = "SELECT * FROM cursos";
        List<Cursos> lista = new ArrayList<>();

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cursos c = new Cursos();
                c.setSemestres(rs.getInt("Semestre"));
                c.setNombreCurso(rs.getString("nombreCurso"));
                c.setParalelo(rs.getString("paralelo"));

                lista.add(c);
            }

        } catch (SQLException ex) {
            System.out.println("Error LIST cursos: " + ex.getMessage());
        }

        return lista;
    }
}

