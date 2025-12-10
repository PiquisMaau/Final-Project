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
public class MatriculasCRUD extends CRUD<Matriculas> {

    @Override
    public boolean create(Matriculas m) {
        String sql = "INSERT INTO matricula (idEstudiantes, Semestre) VALUES (?, ?)";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, m.getIdEstudiante());
            ps.setInt(2, m.getSemestre());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error CREATE matricula: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Matriculas read(int id) {
        String sql = "SELECT * FROM matricula WHERE idMatricula = ?";
        Matriculas m = null;

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new Matriculas();
                m.setIdMatricula(rs.getInt("idMatricula"));
                m.setIdEstudiante(rs.getInt("idEstudiantes"));
                m.setSemestre(rs.getInt("Semestre"));
            }

        } catch (SQLException ex) {
            System.out.println("Error READ matricula: " + ex.getMessage());
        }

        return m;
    }

    @Override
    public boolean update(Matriculas m) {
        String sql = "UPDATE matricula SET Semestre=? WHERE idEstudiantes=?";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, m.getSemestre());
            ps.setInt(2, m.getIdEstudiante());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error UPDATE matricula: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM matricula WHERE idMatricula=?";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error DELETE matricula: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Matriculas> listAll() {
        String sql = "SELECT * FROM matricula";
        List<Matriculas> lista = new ArrayList<>();

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Matriculas m = new Matriculas();
                m.setIdMatricula(rs.getInt("idMatricula"));
                m.setIdEstudiante(rs.getInt("idEstudiantes"));
                m.setSemestre(rs.getInt("Semestre"));

                lista.add(m);
            }

        } catch (SQLException ex) {
            System.out.println("Error LIST matricula: " + ex.getMessage());
        }

        return lista;
    }

    public boolean existeMatricula(int idEstudiante) {
        String sql = "SELECT * FROM matricula WHERE idEstudiantes = ?";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEstudiante);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException ex) {
            System.out.println("Error verificar matricula: " + ex.getMessage());
            return false;
        }
    }
}

