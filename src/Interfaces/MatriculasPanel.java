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
import javax.swing.JTextField;
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
            String criterio = JOptionPane.showInputDialog(this, "Ingrese cédula o nombre del estudiante:");
            if (criterio==null || criterio.trim().isEmpty()) return;
            EstudianteSCRUD escrud = new EstudianteSCRUD(); List<Estudiantes> estList = escrud.readAll();
            Estudiantes found = null; String c = criterio.trim().toLowerCase();
            for (Estudiantes e: estList) if ((e.getEstcedula()!=null && e.getEstcedula().toLowerCase().contains(c)) || (e.getEstnombre()!=null && e.getEstnombre().toLowerCase().contains(c)) || (e.getEstapellido()!=null && e.getEstapellido().toLowerCase().contains(c))) { found=e; break; }
            if (found==null) { JOptionPane.showMessageDialog(this, "Estudiante no encontrado"); return; }
            String cursoCrit = JOptionPane.showInputDialog(this, "Ingrese semestre del curso o nombre del curso:"); if (cursoCrit==null || cursoCrit.trim().isEmpty()) return;
            CursosCRUD ccrud = new CursosCRUD(); List<Cursos> cursos = ccrud.listAll(); Cursos foundC=null; String cc = cursoCrit.trim().toLowerCase();
            for (Cursos ccx: cursos) if ((String.valueOf(ccx.getSemestres()).equals(cc)) || (ccx.getNombreCurso()!=null && ccx.getNombreCurso().toLowerCase().contains(cc))) { foundC=ccx; break; }
            if (foundC==null) { JOptionPane.showMessageDialog(this, "Curso no encontrado"); return; }
            MatriculasCRUD mcrud = new MatriculasCRUD(); Matriculas m = new Matriculas(); m.setIdEstudiante(found.getIdEstudiantes()); m.setSemestre(foundC.getSemestres());
            if (mcrud.create(m)) { JOptionPane.showMessageDialog(this, "Matriculado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "Error al matricular");
        });

        btnEliminar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow(); if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione una matrícula"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); int id = (int) modelo.getValueAt(fila,0);
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar matrícula ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf==JOptionPane.YES_OPTION) { MatriculasCRUD mcrud = new MatriculasCRUD(); if (mcrud.delete(id)) { JOptionPane.showMessageDialog(this, "✅ Eliminado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al eliminar"); }
        });
    }

    private void cargarTabla() {
        MatriculasCRUD mcrud = new MatriculasCRUD(); EstudianteSCRUD escrud = new EstudianteSCRUD(); CursosCRUD ccrud = new CursosCRUD();
        List<Matriculas> lista = mcrud.listAll(); DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); modelo.setRowCount(0);
        for (Matriculas m: lista) { Estudiantes e = escrud.read(m.getIdEstudiante()); Cursos c = ccrud.read(m.getSemestre()); modelo.addRow(new Object[]{m.getIdMatricula(), e!=null?e.getEstnombre():"-", e!=null?e.getEstcedula():"-", c!=null?c.getNombreCurso()+" ("+c.getSemestres()+")":"-"}); }
    }
}
