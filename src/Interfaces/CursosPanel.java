package Interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

import Clases.Cursos;
import CRUD.CursosCRUD;

public class CursosPanel extends JPanel {

    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar;
    private JButton btnVerEstudiantes;
    private JPanel panelButtons;

    public CursosPanel(String role) {
        initComponents();
        configurarAcciones(role);
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(46, 204, 113));
        panelTop.setPreferredSize(new Dimension(800, 60));

        JLabel titulo = new JLabel("Gestor de Cursos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        panelTop.add(titulo);

        add(panelTop, BorderLayout.NORTH);

        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Semestre", "Nombre", "Paralelo" }));
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(39, 174, 96));
        tabla.getTableHeader().setForeground(Color.WHITE);

        scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panelButtons.setBackground(new Color(236, 240, 241));

        btnAgregar = crearBoton("Agregar", new Color(39, 174, 96));
        btnEditar = crearBoton("Editar", new Color(241, 196, 15));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnBuscar = crearBoton("Buscar", new Color(52, 152, 219));
        btnVerEstudiantes = crearBoton("Ver Estudiantes", new Color(52, 73, 94));

        panelButtons.add(btnAgregar);
        panelButtons.add(btnEditar);
        panelButtons.add(btnEliminar);
        panelButtons.add(btnBuscar);
        panelButtons.add(btnVerEstudiantes);

        add(panelButtons, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(110, 36));
        btn.setFocusPainted(false);
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        return btn;
    }

    private void configurarAcciones(String role) {
        boolean isAdmin = "admin".equalsIgnoreCase(role);
        boolean isSecretario = "secretario".equalsIgnoreCase(role);

        if (isSecretario) {
            btnAgregar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        if (isAdmin) {
            btnAgregar.setEnabled(true);
            btnEditar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }

        btnBuscar.addActionListener(ae -> {
            String criterio = JOptionPane.showInputDialog(this, "Ingrese nombre o paralelo (vacío = todos):");
            CursosCRUD crud = new CursosCRUD();
            List<Cursos> lista = crud.listAll();
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            if (criterio == null || criterio.trim().isEmpty()) {
                for (Cursos c : lista) {
                    modelo.addRow(new Object[] { c.getSemestres(), c.getNombreCurso(), c.getParalelo() });
                }
                return;
            }
            String c = criterio.trim().toLowerCase();
            for (Cursos curso : lista) {
                if ((curso.getNombreCurso() != null && curso.getNombreCurso().toLowerCase().contains(c))
                        || (curso.getParalelo() != null && curso.getParalelo().toLowerCase().contains(c))) {
                    modelo.addRow(new Object[] { curso.getSemestres(), curso.getNombreCurso(), curso.getParalelo() });
                }
            }
        });

        // Placeholders for full CRUD (admin)
        btnAgregar.addActionListener(ae -> {
            if (!isAdmin) return;
            JTextField tfSemestre = new JTextField();
            JTextField tfNombre = new JTextField();
            JTextField tfParalelo = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0,1));
            panel.add(new JLabel("Semestre (número):")); panel.add(tfSemestre);
            panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
            panel.add(new JLabel("Paralelo:")); panel.add(tfParalelo);

            int res = JOptionPane.showConfirmDialog(this, panel, "Agregar curso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    int semestre = Integer.parseInt(tfSemestre.getText().trim());
                    String nombre = tfNombre.getText().trim();
                    String paralelo = tfParalelo.getText().trim();
                    Cursos c = new Cursos();
                    CursosCRUD crud = new CursosCRUD();
                    c.setSemestres(semestre); // Semestres representa semestre
                    c.setNombreCurso(nombre); c.setParalelo(paralelo);
                    if (crud.create(c)) { JOptionPane.showMessageDialog(this, " Curso agregado"); cargarTabla(); }
                    else JOptionPane.showMessageDialog(this, " Error al agregar");
                } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Semestre inválido"); }
            }
        });

        btnEditar.addActionListener(ae -> {
            if (!isAdmin) return;
            int fila = tabla.getSelectedRow(); if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila,0);
            String nombre = String.valueOf(modelo.getValueAt(fila,1));
            String paralelo = String.valueOf(modelo.getValueAt(fila,2));
            String semestreStr = String.valueOf(modelo.getValueAt(fila,0));
            JTextField tfSemestre = new JTextField(semestreStr);
            JTextField tfNombre = new JTextField(nombre);
            JTextField tfParalelo = new JTextField(paralelo);
            JPanel panel = new JPanel(new GridLayout(0,1));
            panel.add(new JLabel("Semestre (número):")); panel.add(tfSemestre);
            panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
            panel.add(new JLabel("Paralelo:")); panel.add(tfParalelo);
            int res = JOptionPane.showConfirmDialog(this, panel, "Editar curso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res==JOptionPane.OK_OPTION) {
                try {
                    // No cambiar el semestre (Semestres) para evitar inconsistencias en matriculas.
                    Cursos c = new Cursos(); c.setSemestres(id); c.setNombreCurso(tfNombre.getText().trim()); c.setParalelo(tfParalelo.getText().trim());
                    CursosCRUD crud = new CursosCRUD();
                    if (crud.update(c)) { JOptionPane.showMessageDialog(this, "✅ Curso actualizado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al actualizar");
                } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Valor inválido"); }
            }
        });

        btnEliminar.addActionListener(ae -> {
            if (!isAdmin) return;
            int fila = tabla.getSelectedRow(); if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione fila"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila,0);
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar curso ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf==JOptionPane.YES_OPTION) { CursosCRUD crud = new CursosCRUD(); if (crud.delete(id)) { JOptionPane.showMessageDialog(this, "✅ Eliminado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al eliminar"); }
        });

        // Ver estudiantes matriculados en el curso
        btnVerEstudiantes.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un curso"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int semestre = (int) modelo.getValueAt(fila, 0);
            int Semestre = semestre; // Semestres representa semestre

            // mostrar lista de estudiantes matriculados
            CRUD.MatriculasCRUD mcrud = new CRUD.MatriculasCRUD();
            CRUD.EstudianteSCRUD escrud = new CRUD.EstudianteSCRUD();
            java.util.List<Clases.Matriculas> mats = mcrud.listAll();
            DefaultTableModel mdl = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Cédula", "Nombre", "Apellido"});
            for (Clases.Matriculas m : mats) {
                if (m.getSemestre() == Semestre) {
                    Clases.Estudiantes est = escrud.read(m.getIdEstudiante());
                    if (est != null) mdl.addRow(new Object[]{est.getIdEstudiantes(), est.getEstcedula(), est.getEstnombre(), est.getEstapellido()});
                }
            }

            javax.swing.JTable t = new javax.swing.JTable(mdl);
            JScrollPane sp = new JScrollPane(t);
            JPanel p = new JPanel(new BorderLayout());
            p.add(sp, BorderLayout.CENTER);

            // search panel
            javax.swing.JTextField tfSearch = new javax.swing.JTextField(20);
            javax.swing.JButton bSearch = new javax.swing.JButton("Buscar");
            javax.swing.JPanel top = new javax.swing.JPanel(new FlowLayout(FlowLayout.LEFT));
            top.add(new JLabel("Buscar por cédula o nombre:")); top.add(tfSearch); top.add(bSearch);
            p.add(top, BorderLayout.NORTH);

            // If admin, add button to matricular estudiante
            if (btnAgregar.isEnabled()) {
                javax.swing.JButton bMatricular = new javax.swing.JButton("Matricular estudiante");
                javax.swing.JPanel bottom = new javax.swing.JPanel(new FlowLayout(FlowLayout.RIGHT));
                bottom.add(bMatricular);
                p.add(bottom, BorderLayout.SOUTH);

                bMatricular.addActionListener(ev -> {
                    String criterio = JOptionPane.showInputDialog(this, "Ingrese cédula o nombre del estudiante a matricular:");
                    if (criterio==null || criterio.trim().isEmpty()) return;
                    String c = criterio.trim().toLowerCase();
                    java.util.List<Clases.Estudiantes> lista = escrud.readAll();
                    Clases.Estudiantes found = null;
                    for (Clases.Estudiantes es : lista) {
                        if ((es.getEstcedula()!=null && es.getEstcedula().toLowerCase().contains(c)) || (es.getEstnombre()!=null && es.getEstnombre().toLowerCase().contains(c)) || (es.getEstapellido()!=null && es.getEstapellido().toLowerCase().contains(c))) { found = es; break; }
                    }
                    if (found==null) { JOptionPane.showMessageDialog(this, "Estudiante no encontrado"); return; }
                    CRUD.MatriculasCRUD mc = new CRUD.MatriculasCRUD();
                    Clases.Matriculas nueva = new Clases.Matriculas();
                    nueva.setIdEstudiante(found.getIdEstudiantes());
                    nueva.setSemestre(Semestre);
                    if (mc.create(nueva)) { JOptionPane.showMessageDialog(this, "✅ Matriculado"); }
                    else JOptionPane.showMessageDialog(this, "❌ Error al matricular");
                });
            }

            javax.swing.JDialog dlg = new javax.swing.JDialog();
            dlg.setTitle("Estudiantes matriculados - Curso semestre " + semestre);
            dlg.setSize(600,400);
            dlg.setLocationRelativeTo(this);
            dlg.getContentPane().add(p);

            bSearch.addActionListener(ev -> {
                String q = tfSearch.getText().trim().toLowerCase();
                DefaultTableModel m2 = (DefaultTableModel) t.getModel();
                m2.setRowCount(0);
                for (Clases.Matriculas mm : mats) {
                    if (mm.getSemestre()==Semestre) {
                        Clases.Estudiantes est = escrud.read(mm.getIdEstudiante());
                        if (est!=null) {
                            if (q.isEmpty() || (est.getEstcedula()!=null && est.getEstcedula().toLowerCase().contains(q)) || (est.getEstnombre()!=null && est.getEstnombre().toLowerCase().contains(q)) || (est.getEstapellido()!=null && est.getEstapellido().toLowerCase().contains(q))) {
                                m2.addRow(new Object[]{est.getIdEstudiantes(), est.getEstcedula(), est.getEstnombre(), est.getEstapellido()});
                            }
                        }
                    }
                }
            });

            dlg.setModal(true);
            dlg.setVisible(true);
        });
    }

    private void cargarTabla() {
        CursosCRUD crud = new CursosCRUD();
        List<Cursos> lista = crud.listAll();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (Cursos c : lista) {
            modelo.addRow(new Object[] { c.getSemestres(), c.getNombreCurso(), c.getParalelo() });
        }
    }
}
