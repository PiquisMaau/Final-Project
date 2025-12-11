package Interfaces;

import java.awt.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import CRUD.MatriculasCRUD;
import CRUD.EstudianteSCRUD;
import CRUD.CursosCRUD;
import Clases.Matriculas;
import Clases.Estudiantes;
import Clases.Cursos;

public class MatriculasPanel extends JPanel {
    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnAgregar, btnEliminar, btnBuscar;

    public MatriculasPanel() {
        initComponents();
        configurarAcciones();
        cargarTabla();
    }

    private void initComponents() {
    setLayout(new BorderLayout());

    // ------------------ PANEL SUPERIOR ------------------
    JPanel panelTop = new JPanel();
    panelTop.setBackground(new Color(46, 204, 113)); // Verde moderno
    panelTop.setPreferredSize(new Dimension(800, 60));

    JLabel titulo = new JLabel("Gestión de Matrículas");
    titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
    titulo.setForeground(Color.WHITE);
    panelTop.add(titulo);

    add(panelTop, BorderLayout.NORTH);

    // ------------------ TABLA ------------------
    tabla = new JTable();
    tabla.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Estudiante", "Cédula", "Curso (Semestre)"}
    ));

    tabla.setRowHeight(24);
    tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    tabla.getTableHeader().setBackground(new Color(39, 174, 96));
    tabla.getTableHeader().setForeground(Color.WHITE);

    scroll = new JScrollPane(tabla);
    add(scroll, BorderLayout.CENTER);

    // ------------------ PANEL DE BOTONES ------------------
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
    panelButtons.setBackground(new Color(236, 240, 241));

    btnAgregar = crearBoton("Matricular", new Color(39, 174, 96));
    btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
    btnBuscar = crearBoton("Buscar", new Color(52, 152, 219));

    panelButtons.add(btnAgregar);
    panelButtons.add(btnEliminar);
    panelButtons.add(btnBuscar);

    add(panelButtons, BorderLayout.SOUTH);
}
private JButton crearBoton(String texto, Color colorFondo) {
    JButton btn = new JButton(texto);
    btn.setFocusPainted(false);
    btn.setBackground(colorFondo);
    btn.setForeground(Color.WHITE);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btn.setPreferredSize(new Dimension(140, 35));
    btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    return btn;
}


    private void configurarAcciones() {
        btnBuscar.addActionListener(ae -> {
            String criterio = JOptionPane.showInputDialog(this, "Buscar por cédula o nombre (vacío=todos):");
            MatriculasCRUD mcrud = new MatriculasCRUD(); EstudianteSCRUD escrud = new EstudianteSCRUD(); CursosCRUD ccrud = new CursosCRUD();
            List<Matriculas> lista = mcrud.listAll(); DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); modelo.setRowCount(0);
            if (criterio==null || criterio.trim().isEmpty()) {
                for (Matriculas m: lista) {
                    Estudiantes e = escrud.read(m.getIdEstudiante()); Cursos c = ccrud.read(m.getSemestre());
                    modelo.addRow(new Object[]{m.getIdMatricula(), e!=null?e.getEstnombre():"-", e!=null?e.getEstcedula():"-", c!=null?c.getNombreCurso()+" ("+c.getSemestres()+")":"-"});
                }
                return;
            }
            String q = criterio.trim().toLowerCase();
            for (Matriculas m: lista) {
                Estudiantes e = escrud.read(m.getIdEstudiante()); Cursos c = ccrud.read(m.getSemestre());
                if (e!=null && ((e.getEstcedula()!=null && e.getEstcedula().toLowerCase().contains(q)) || (e.getEstnombre()!=null && e.getEstnombre().toLowerCase().contains(q)) || (e.getEstapellido()!=null && e.getEstapellido().toLowerCase().contains(q)))) {
                    modelo.addRow(new Object[]{m.getIdMatricula(), e.getEstnombre(), e.getEstcedula(), c!=null?c.getNombreCurso()+" ("+c.getSemestres()+")":"-"});
                }
            }
        });

        btnAgregar.addActionListener(ae -> {
    // 1. Pedir Estudiante (Puedes mantener tu input actual o mejorarlo)
    String criterio = JOptionPane.showInputDialog(this, "Ingrese cédula del estudiante a matricular:");
    if (criterio == null || criterio.trim().isEmpty()) return;

    EstudianteSCRUD escrud = new EstudianteSCRUD();
    // Busca el estudiante (asumiendo que tienes un método buscarPorCedula o usas lógica de filtrado)
    // Para simplificar, aquí buscamos en todos:
    Estudiantes found = null;
    for (Estudiantes e : escrud.readAll()) {
        if (e.getEstcedula().equals(criterio)) { found = e; break; }
    }
    
    if (found == null) {
        JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
        return;
    }

    // --- REQUISITO: VALIDAR 1 ESTUDIANTE = 1 CURSO ---
    MatriculasCRUD mcrud = new MatriculasCRUD();
    if (mcrud.existeMatricula(found.getIdEstudiantes())) { // Asegurate de tener este método en MatriculasCRUD
        JOptionPane.showMessageDialog(this, " El estudiante ya está inscrito en un curso.\nNo puede tener más de uno.");
        return;
    }

    // --- REQUISITO: COMBOBOX DE CURSOS ---
    // Creamos el combo y lo llenamos con objetos Cursos
    javax.swing.JComboBox<String> comboCursos = new javax.swing.JComboBox<>();
    CursosCRUD ccrud = new CursosCRUD();
    List<Cursos> listaCursos = ccrud.listAll();
    
    for (Cursos c : listaCursos) {
        // Mostramos Nombre y Paralelo en el combo
        comboCursos.addItem(c.getNombreCurso() + " - " + c.getParalelo() + " (Sem: " + c.getSemestres() + ")");
    }

    // Mostramos el ComboBox en una ventanita
    int result = JOptionPane.showConfirmDialog(this, comboCursos, "Seleccione el Curso", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
        int index = comboCursos.getSelectedIndex();
        Cursos cursoSeleccionado = listaCursos.get(index); // Obtenemos el objeto Curso real de la lista

        Matriculas nuevaMatricula = new Matriculas();
        nuevaMatricula.setIdEstudiante(found.getIdEstudiantes());
        nuevaMatricula.setSemestre(cursoSeleccionado.getSemestres()); // OJO: Usas 'Semestre' como ID del curso en tu código
        
        if (mcrud.create(nuevaMatricula)) {
            JOptionPane.showMessageDialog(this, "Matriculado con éxito en " + cursoSeleccionado.getNombreCurso());
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.");
        }
    }
});

        btnEliminar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow(); if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione una matrícula"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); int id = (int) modelo.getValueAt(fila,0);
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar matrícula ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf==JOptionPane.YES_OPTION) { MatriculasCRUD mcrud = new MatriculasCRUD(); if (mcrud.delete(id)) { JOptionPane.showMessageDialog(this, "Eliminado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "Error al eliminar"); }
        });
    }

    private void cargarTabla() {
        MatriculasCRUD mcrud = new MatriculasCRUD(); EstudianteSCRUD escrud = new EstudianteSCRUD(); CursosCRUD ccrud = new CursosCRUD();
        List<Matriculas> lista = mcrud.listAll(); DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); modelo.setRowCount(0);
        for (Matriculas m: lista) { Estudiantes e = escrud.read(m.getIdEstudiante()); Cursos c = ccrud.read(m.getSemestre()); modelo.addRow(new Object[]{m.getIdMatricula(), e!=null?e.getEstnombre():"-", e!=null?e.getEstcedula():"-", c!=null?c.getNombreCurso()+" ("+c.getSemestres()+")":"-"}); }
    }
}
