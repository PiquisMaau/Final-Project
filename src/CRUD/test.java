/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import Clases.*;
import Clases.Estudiantes;
import java.util.Date;

/**
 *
 * @author Sebas
 */
public class test {

    public static void main(String[] args) {

        EstudianteSCRUD crud = new EstudianteSCRUD();

        // ✅ CREATE
        Estudiantes nuevo = new Estudiantes();
        nuevo.setEstcedula("0102030405");
        nuevo.setEstnombre("Juan");
        nuevo.setEstapellido("Pérez");
        nuevo.setEstedad(20);
        nuevo.setEsttelefono("0999999999");

        if (crud.create(nuevo)) {
            System.out.println("✅ Estudiante creado correctamente.");
        } else {
            System.out.println("❌ Error al crear estudiante.");
        }

        System.out.println("\n📋 Lista de estudiantes:");
        for (Estudiantes e : new EstudianteSCRUD().readAll()) {
            System.out.println("ID: " + e.getIdEstudiantes()
                    + ", Nombre: " + e.getEstnombre()
                    + ", Apellido: " + e.getEstapellido());
        }
        System.out.println("\n📋 Estudiante por ID:");
        Estudiantes e = new EstudianteSCRUD().read(2); // Usa un ID válido

        if (e != null) {
            System.out.println("ID: " + e.getIdEstudiantes()
                    + ", Nombre: " + e.getEstnombre()
                    + ", Apellido: " + e.getEstapellido());
        } else {
            System.out.println("❌ Estudiante no encontrado.");
        }

        // ✅ UPDATE
        Estudiantes actualizado = new Estudiantes();
        actualizado.setIdEstudiantes(1); // Asegúrate que este ID exista
        actualizado.setEstcedula("0102030405");
        actualizado.setEstnombre("Juan Carlos");
        actualizado.setEstapellido("Pérez Gómez");
        actualizado.setEstedad(21);
        actualizado.setEsttelefono("0988888888");

        if (crud.update(actualizado)) {
            System.out.println("✅ Estudiante actualizado correctamente.");
        } else {
            System.out.println("❌ Error al actualizar estudiante.");
        }

        // ✅ DELETE
        int idEliminar = 1; // Asegúrate que este ID exista
        if (crud.delete(idEliminar)) {
            System.out.println("✅ Estudiante eliminado correctamente.");
        } else {
            System.out.println("❌ Error al eliminar estudiante.");
        }
    
    
    MatriculasCRUD crudM = new MatriculasCRUD();

        System.out.println("===== TEST CRUD MATRÍCULAS =====");

        // ✅ CREATE
        Matriculas nueva = new Matriculas();
        nueva.setIdEstudiante(1); // Debe existir en la BD
        nueva.setSemestre(1);      // Debe existir en la BD

        boolean creado = crudM.create(nueva);
        System.out.println(creado ? "✅ CREATE OK" : "❌ CREATE ERROR");

        // ✅ LIST ALL
        System.out.println("\n📋 LISTA DE MATRÍCULAS:");
        for (Matriculas m : crudM.listAll()) {
            System.out.println(
                "ID: " + m.getIdMatricula() +
                ", Estudiante: " + m.getIdEstudiante() +
                ", Curso: " + m.getSemestre()
            );
        }

        // ✅ READ BY ID
        int idBuscarM = 1; // Cambia según tu BD
        Matriculas buscada = crudM.read(idBuscarM);

        if (buscada != null) {
            System.out.println("\n🔍 MATRÍCULA ENCONTRADA:");
            System.out.println("ID: " + buscada.getIdMatricula() +
                               ", Estudiante: " + buscada.getIdEstudiante() +
                               ", Curso: " + buscada.getSemestre());
        } else {
            System.out.println("\n❌ No existe matrícula con ID " + idBuscarM);
        }

        // ✅ UPDATE
        Matriculas actualizar = new Matriculas();
        actualizar.setIdEstudiante(1); // WHERE idEstudiante = ?
        actualizar.setSemestre(2);      // Nuevo curso

        boolean actualizadoM = crudM.update(actualizar);
        System.out.println(actualizadoM ? "✅ UPDATE OK" : "❌ UPDATE ERROR");

        // ✅ EXISTE MATRÍCULA
        boolean existe = crudM.existeMatricula(1);
        System.out.println(existe ? "\n✅ El estudiante 1 SÍ está matriculado" 
                                  : "\n❌ El estudiante 1 NO está matriculado");

        // ✅ DELETE
        int idEliminarM = 1; // Cambia según tu BD
        boolean eliminado = crud.delete(idEliminarM);
        System.out.println(eliminado ? "✅ DELETE OK" : "❌ DELETE ERROR");
    
    
     UsuarioCRUD crudU = new UsuarioCRUD();

        System.out.println("===== TEST LOGIN =====");

        // Datos de prueba (deben existir en la BD)
        String username = "admin";
        String password = "1234";

        Usuario u = crudU.login(username, password);

        if (u != null) {
            System.out.println("✅ LOGIN EXITOSO");
            System.out.println("ID: " + u.getIdUsuario());
            System.out.println("Usuario: " + u.getUsername());
            System.out.println("Rol: " + u.getRol());
        } else {
            System.out.println("❌ LOGIN FALLIDO — Usuario o contraseña incorrectos");
        }
        CursosCRUD crudC = new CursosCRUD();

        System.out.println("===== TEST CREATE CURSO =====");

        Cursos nuevoC = new Cursos();
        nuevoC.setNombreCurso("Estructura de Datos");
        nuevoC.setParalelo("A");

        boolean creadoC = crudC.create(nuevoC);

        if (creadoC) {
            System.out.println("✅ Curso creado correctamente.");
        } else {
            System.out.println("❌ Error al crear curso.");
        }
    }
    
    }
    


