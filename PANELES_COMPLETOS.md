# Paneles Generados para Proyecto Final

A continuación se presentan todos los paneles generados con su código Java completo. Puedes copiar estos códigos a tus archivos correspondientes en NetBeans.

## 1. EstudiantesPanel.java

Este panel gestiona estudiantes (CRUD) con control de roles.

```java
package Interfaces;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Clases.Estudiantes;
import CRUD.EstudianteSCRUD;

public class EstudiantesPanel extends javax.swing.JPanel {

    private String role;

    public EstudiantesPanel() {
        initComponents();
    }

    public EstudiantesPanel(String role) {
        initComponents();
        this.role = role;
        configurarAcciones();
        cargarTabla();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEstudiantes = new javax.swing.JTable();
        panelButtons = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelTop.setBackground(new java.awt.Color(52, 152, 219));
        panelTop.setPreferredSize(new java.awt.Dimension(800, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Gestor de Estudiantes");
        panelTop.add(jLabel1);

        add(panelTop, java.awt.BorderLayout.NORTH);

        tblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Cédula", "Nombre", "Apellido", "Edad", "Teléfono"}
        ) {
            Class[] types = new Class [] {java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class};
            boolean[] canEdit = new boolean [] {false, false, false, false, false, false};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblEstudiantes);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 400));

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelButtons.setBackground(new java.awt.Color(236, 240, 241));
        panelButtons.setPreferredSize(new java.awt.Dimension(800, 50));
        panelButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));

        btnAgregar.setBackground(new java.awt.Color(46, 204, 113));
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setPreferredSize(new java.awt.Dimension(110, 36));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        panelButtons.add(btnAgregar);

        btnEditar.setBackground(new java.awt.Color(241, 196, 15));
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setPreferredSize(new java.awt.Dimension(110, 36));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        panelButtons.add(btnEditar);

        btnEliminar.setBackground(new java.awt.Color(231, 76, 60));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setPreferredSize(new java.awt.Dimension(110, 36));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        panelButtons.add(btnEliminar);

        btnBuscar.setBackground(new java.awt.Color(52, 152, 219));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setPreferredSize(new java.awt.Dimension(110, 36));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panelButtons.add(btnBuscar);

        add(panelButtons, java.awt.BorderLayout.SOUTH);
    }

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        JTextField tfCedula = new JTextField();
        JTextField tfNombre = new JTextField();
        JTextField tfApellido = new JTextField();
        JTextField tfEdad = new JTextField();
        JTextField tfTelefono = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Cédula:")); panel.add(tfCedula);
        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("Apellido:")); panel.add(tfApellido);
        panel.add(new JLabel("Edad:")); panel.add(tfEdad);
        panel.add(new JLabel("Teléfono:")); panel.add(tfTelefono);

        int res = JOptionPane.showConfirmDialog(this, panel, "Agregar estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String ced = tfCedula.getText().trim();
                String nom = tfNombre.getText().trim();
                String ape = tfApellido.getText().trim();
                int edad = Integer.parseInt(tfEdad.getText().trim());
                String tel = tfTelefono.getText().trim();

                EstudianteSCRUD crud = new EstudianteSCRUD();
                List<Estudiantes> lista = crud.readAll();
                int maxId = 0;
                for (Estudiantes ex : lista) if (ex.getIdEstudiantes() > maxId) maxId = ex.getIdEstudiantes();
                int nuevoId = maxId + 1;

                Estudiantes nuevo = new Estudiantes();
                nuevo.setIdEstudiantes(nuevoId);
                nuevo.setEstcedula(ced);
                nuevo.setEstnombre(nom);
                nuevo.setEstapellido(ape);
                nuevo.setEstedad(edad);
                nuevo.setEsttelefono(tel);

                if (crud.create(nuevo)) {
                    JOptionPane.showMessageDialog(this, "✅ Estudiante agregado");
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al agregar");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Edad inválida");
            }
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblEstudiantes.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione una fila para editar"); return; }
        DefaultTableModel modelo = (DefaultTableModel) tblEstudiantes.getModel();
        int id = (int) modelo.getValueAt(fila, 0);
        String ced = String.valueOf(modelo.getValueAt(fila, 1));
        String nom = String.valueOf(modelo.getValueAt(fila, 2));
        String ape = String.valueOf(modelo.getValueAt(fila, 3));
        String edadStr = String.valueOf(modelo.getValueAt(fila, 4));
        String tel = String.valueOf(modelo.getValueAt(fila, 5));

        JTextField tfCedula = new JTextField(ced);
        JTextField tfNombre = new JTextField(nom);
        JTextField tfApellido = new JTextField(ape);
        JTextField tfEdad = new JTextField(edadStr);
        JTextField tfTelefono = new JTextField(tel);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Cédula:")); panel.add(tfCedula);
        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("Apellido:")); panel.add(tfApellido);
        panel.add(new JLabel("Edad:")); panel.add(tfEdad);
        panel.add(new JLabel("Teléfono:")); panel.add(tfTelefono);

        int res = JOptionPane.showConfirmDialog(this, panel, "Editar estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String nced = tfCedula.getText().trim();
                String nnom = tfNombre.getText().trim();
                String nape = tfApellido.getText().trim();
                int nedad = Integer.parseInt(tfEdad.getText().trim());
                String ntel = tfTelefono.getText().trim();

                Estudiantes actualizado = new Estudiantes();
                actualizado.setIdEstudiantes(id);
                actualizado.setEstcedula(nced);
                actualizado.setEstnombre(nnom);
                actualizado.setEstapellido(nape);
                actualizado.setEstedad(nedad);
                actualizado.setEsttelefono(ntel);

                EstudianteSCRUD crud = new EstudianteSCRUD();
                if (crud.update(actualizado)) {
                    JOptionPane.showMessageDialog(this, "✅ Estudiante actualizado");
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al actualizar");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Edad inválida");
            }
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tblEstudiantes.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar"); return; }
        DefaultTableModel modelo = (DefaultTableModel) tblEstudiantes.getModel();
        int id = (int) modelo.getValueAt(fila, 0);
        int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar estudiante ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            EstudianteSCRUD crud = new EstudianteSCRUD();
            if (crud.delete(id)) {
                JOptionPane.showMessageDialog(this, "✅ Eliminado");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al eliminar");
            }
        }
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String criterio = JOptionPane.showInputDialog(this, "Ingrese cédula o nombre (vacío = todos):");
        EstudianteSCRUD crud = new EstudianteSCRUD();
        List<Estudiantes> lista = crud.readAll();
        DefaultTableModel modelo = (DefaultTableModel) tblEstudiantes.getModel();
        modelo.setRowCount(0);
        if (criterio == null || criterio.trim().isEmpty()) {
            for (Estudiantes e : lista) {
                modelo.addRow(new Object[] { e.getIdEstudiantes(), e.getEstcedula(), e.getEstnombre(), e.getEstapellido(), e.getEstedad(), e.getEsttelefono() });
            }
            return;
        }
        String c = criterio.trim().toLowerCase();
        for (Estudiantes e : lista) {
            if ((e.getEstcedula() != null && e.getEstcedula().toLowerCase().contains(c))
                    || (e.getEstnombre() != null && e.getEstnombre().toLowerCase().contains(c))
                    || (e.getEstapellido() != null && e.getEstapellido().toLowerCase().contains(c))) {
                modelo.addRow(new Object[] { e.getIdEstudiantes(), e.getEstcedula(), e.getEstnombre(), e.getEstapellido(), e.getEstedad(), e.getEsttelefono() });
            }
        }
    }

    private void configurarAcciones() {
        boolean isAdmin = "admin".equalsIgnoreCase(role);
        if (!isAdmin) {
            btnAgregar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }
    }

    private void cargarTabla() {
        EstudianteSCRUD crud = new EstudianteSCRUD();
        List<Estudiantes> lista = crud.readAll();
        DefaultTableModel modelo = (DefaultTableModel) tblEstudiantes.getModel();
        modelo.setRowCount(0);
        for (Estudiantes e : lista) {
            modelo.addRow(new Object[] { e.getIdEstudiantes(), e.getEstcedula(), e.getEstnombre(), e.getEstapellido(), e.getEstedad(), e.getEsttelefono() });
        }
    }

    private javax.swing.JPanel panelTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEstudiantes;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnBuscar;
}
```

---

## 2. CursosPanel.java

Panel para gestionar cursos por semestre con matriculación de estudiantes.

```java
package Interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Clases.Cursos;
import Clases.Estudiantes;
import Clases.Matriculas;
import CRUD.CursosCRUD;
import CRUD.EstudianteSCRUD;
import CRUD.MatriculasCRUD;

public class CursosPanel extends JPanel {

    private JTable tabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar, btnVerEstudiantes;

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
        titulo.setForeground(Color.WHITE);
        panelTop.add(titulo);
        add(panelTop, BorderLayout.NORTH);

        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Semestre", "Nombre", "Paralelo"}));
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnVerEstudiantes = new JButton("Ver Estudiantes");
        bottom.add(btnAgregar); bottom.add(btnEditar); bottom.add(btnEliminar); bottom.add(btnBuscar); bottom.add(btnVerEstudiantes);
        add(bottom, BorderLayout.SOUTH);
    }

    private void configurarAcciones(String role) {
        boolean isAdmin = "admin".equalsIgnoreCase(role);
        
        if (!isAdmin) {
            btnAgregar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        btnBuscar.addActionListener(ae -> {
            String criterio = JOptionPane.showInputDialog(this, "Ingrese nombre (vacío = todos):");
            CursosCRUD crud = new CursosCRUD();
            List<Cursos> lista = crud.listAll();
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            if (criterio == null || criterio.trim().isEmpty()) {
                for (Cursos c : lista) modelo.addRow(new Object[] { c.getIdCursos(), c.getNombreCurso(), c.getParalelo() });
                return;
            }
            String c = criterio.trim().toLowerCase();
            for (Cursos curso : lista) {
                if ((curso.getNombreCurso() != null && curso.getNombreCurso().toLowerCase().contains(c))
                        || (curso.getParalelo() != null && curso.getParalelo().toLowerCase().contains(c))) {
                    modelo.addRow(new Object[] { curso.getIdCursos(), curso.getNombreCurso(), curso.getParalelo() });
                }
            }
        });

        btnAgregar.addActionListener(ae -> {
            JTextField tfSemestre = new JTextField();
            JTextField tfNombre = new JTextField();
            JTextField tfParalelo = new JTextField();
            JPanel p = new JPanel(new GridLayout(0, 1));
            p.add(new JLabel("Semestre:")); p.add(tfSemestre);
            p.add(new JLabel("Nombre:")); p.add(tfNombre);
            p.add(new JLabel("Paralelo:")); p.add(tfParalelo);
            int res = JOptionPane.showConfirmDialog(this, p, "Agregar", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    Cursos c = new Cursos();
                    c.setIdCursos(Integer.parseInt(tfSemestre.getText().trim()));
                    c.setNombreCurso(tfNombre.getText().trim());
                    c.setParalelo(tfParalelo.getText().trim());
                    if (new CursosCRUD().create(c)) {
                        JOptionPane.showMessageDialog(this, "✅ Agregado"); cargarTabla();
                    } else JOptionPane.showMessageDialog(this, "❌ Error");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
            }
        });

        btnEditar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un curso"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila, 0);
            String nombre = String.valueOf(modelo.getValueAt(fila, 1));
            String paralelo = String.valueOf(modelo.getValueAt(fila, 2));
            JTextField tfNombre = new JTextField(nombre);
            JTextField tfParalelo = new JTextField(paralelo);
            JPanel p = new JPanel(new GridLayout(0, 1));
            p.add(new JLabel("Nombre:")); p.add(tfNombre);
            p.add(new JLabel("Paralelo:")); p.add(tfParalelo);
            int res = JOptionPane.showConfirmDialog(this, p, "Editar", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                Cursos c = new Cursos();
                c.setIdCursos(id);
                c.setNombreCurso(tfNombre.getText().trim());
                c.setParalelo(tfParalelo.getText().trim());
                if (new CursosCRUD().update(c)) {
                    JOptionPane.showMessageDialog(this, "✅ Actualizado"); cargarTabla();
                } else JOptionPane.showMessageDialog(this, "❌ Error");
            }
        });

        btnEliminar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un curso"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar?") == JOptionPane.YES_OPTION) {
                if (new CursosCRUD().delete(id)) {
                    JOptionPane.showMessageDialog(this, "✅ Eliminado"); cargarTabla();
                } else JOptionPane.showMessageDialog(this, "❌ Error");
            }
        });

        btnVerEstudiantes.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un curso"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int semestre = (int) modelo.getValueAt(fila, 0);

            MatriculasCRUD mcrud = new MatriculasCRUD();
            EstudianteSCRUD escrud = new EstudianteSCRUD();
            List<Matriculas> mats = mcrud.listAll();
            DefaultTableModel mdl = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Cédula", "Nombre", "Apellido"});
            for (Matriculas m : mats) {
                if (m.getIdCurso() == semestre) {
                    Estudiantes est = escrud.read(m.getIdEstudiante());
                    if (est != null) mdl.addRow(new Object[]{est.getIdEstudiantes(), est.getEstcedula(), est.getEstnombre(), est.getEstapellido()});
                }
            }

            JTable t = new JTable(mdl);
            JPanel p = new JPanel(new BorderLayout());
            p.add(new JScrollPane(t), BorderLayout.CENTER);

            javax.swing.JTextField tfSearch = new javax.swing.JTextField(20);
            javax.swing.JButton bSearch = new javax.swing.JButton("Buscar");
            JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
            top.add(new JLabel("Buscar:")); top.add(tfSearch); top.add(bSearch);
            p.add(top, BorderLayout.NORTH);

            if (btnAgregar.isEnabled()) {
                javax.swing.JButton bMatricular = new javax.swing.JButton("Matricular");
                JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                bottom.add(bMatricular);
                p.add(bottom, BorderLayout.SOUTH);

                bMatricular.addActionListener(ev -> {
                    String criterio = JOptionPane.showInputDialog(this, "Ingrese cédula o nombre:");
                    if (criterio==null || criterio.trim().isEmpty()) return;
                    String c = criterio.trim().toLowerCase();
                    List<Estudiantes> lista = escrud.readAll();
                    Estudiantes found = null;
                    for (Estudiantes es : lista) {
                        if ((es.getEstcedula()!=null && es.getEstcedula().toLowerCase().contains(c)) || (es.getEstnombre()!=null && es.getEstnombre().toLowerCase().contains(c)) || (es.getEstapellido()!=null && es.getEstapellido().toLowerCase().contains(c))) {
                            found = es; break;
                        }
                    }
                    if (found==null) { JOptionPane.showMessageDialog(this, "No encontrado"); return; }
                    Matriculas nueva = new Matriculas();
                    nueva.setIdEstudiante(found.getIdEstudiantes());
                    nueva.setIdCurso(semestre);
                    if (mcrud.create(nueva)) { JOptionPane.showMessageDialog(this, "✅ Matriculado"); } else JOptionPane.showMessageDialog(this, "❌ Error");
                });
            }

            javax.swing.JDialog dlg = new javax.swing.JDialog();
            dlg.setTitle("Estudiantes - Semestre " + semestre);
            dlg.setSize(600, 400);
            dlg.setLocationRelativeTo(this);
            dlg.getContentPane().add(p);

            bSearch.addActionListener(ev -> {
                String q = tfSearch.getText().trim().toLowerCase();
                DefaultTableModel m2 = (DefaultTableModel) t.getModel();
                m2.setRowCount(0);
                for (Matriculas mm : mats) {
                    if (mm.getIdCurso()==semestre) {
                        Estudiantes est = escrud.read(mm.getIdEstudiante());
                        if (est!=null && (q.isEmpty() || (est.getEstcedula()!=null && est.getEstcedula().toLowerCase().contains(q)) || (est.getEstnombre()!=null && est.getEstnombre().toLowerCase().contains(q)))) {
                            m2.addRow(new Object[]{est.getIdEstudiantes(), est.getEstcedula(), est.getEstnombre(), est.getEstapellido()});
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
            modelo.addRow(new Object[] { c.getIdCursos(), c.getNombreCurso(), c.getParalelo() });
        }
    }
}
```

---

## 3. MatriculasPanel.java

Panel para gestionar matrículas (admin).

```java
package Interfaces;

import java.util.List;
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
    private JButton btnAgregar, btnEliminar, btnBuscar;

    public MatriculasPanel() {
        initComponents();
        configurarAcciones();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new java.awt.BorderLayout());
        JPanel top = new JPanel(); top.add(new JLabel("Gestión de Matriculas"));
        add(top, java.awt.BorderLayout.NORTH);
        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID","Estudiante","Cédula","Curso(Semestre)"}));
        add(new JScrollPane(tabla), java.awt.BorderLayout.CENTER);
        JPanel bottom = new JPanel(new java.awt.FlowLayout());
        btnAgregar = new JButton("Matricular");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        bottom.add(btnAgregar); bottom.add(btnEliminar); bottom.add(btnBuscar);
        add(bottom, java.awt.BorderLayout.SOUTH);
    }

    private void configurarAcciones() {
        btnBuscar.addActionListener(ae -> {
            String criterio = JOptionPane.showInputDialog(this, "Buscar por cédula o nombre (vacío=todos):");
            MatriculasCRUD mcrud = new MatriculasCRUD();
            EstudianteSCRUD escrud = new EstudianteSCRUD();
            CursosCRUD ccrud = new CursosCRUD();
            List<Matriculas> lista = mcrud.listAll();
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            if (criterio==null || criterio.trim().isEmpty()) {
                for (Matriculas m: lista) {
                    Estudiantes e = escrud.read(m.getIdEstudiante());
                    Cursos c = ccrud.read(m.getIdCurso());
                    modelo.addRow(new Object[]{m.getIdMatricula(), e!=null?e.getEstnombre():"-", e!=null?e.getEstcedula():"-", c!=null?c.getNombreCurso()+" ("+c.getIdCursos()+")":"-"});
                }
                return;
            }
            String q = criterio.trim().toLowerCase();
            for (Matriculas m: lista) {
                Estudiantes e = escrud.read(m.getIdEstudiante());
                Cursos c = ccrud.read(m.getIdCurso());
                if (e!=null && ((e.getEstcedula()!=null && e.getEstcedula().toLowerCase().contains(q)) || (e.getEstnombre()!=null && e.getEstnombre().toLowerCase().contains(q)))) {
                    modelo.addRow(new Object[]{m.getIdMatricula(), e.getEstnombre(), e.getEstcedula(), c!=null?c.getNombreCurso()+" ("+c.getIdCursos()+")":"-"});
                }
            }
        });

        btnAgregar.addActionListener(ae -> {
            String criterio = JOptionPane.showInputDialog(this, "Ingrese cédula o nombre del estudiante:");
            if (criterio==null || criterio.trim().isEmpty()) return;
            EstudianteSCRUD escrud = new EstudianteSCRUD();
            List<Estudiantes> estList = escrud.readAll();
            Estudiantes found = null;
            String c = criterio.trim().toLowerCase();
            for (Estudiantes e: estList) if ((e.getEstcedula()!=null && e.getEstcedula().toLowerCase().contains(c)) || (e.getEstnombre()!=null && e.getEstnombre().toLowerCase().contains(c))) { found=e; break; }
            if (found==null) { JOptionPane.showMessageDialog(this, "Estudiante no encontrado"); return; }
            String cursoCrit = JOptionPane.showInputDialog(this, "Ingrese semestre del curso:");
            if (cursoCrit==null || cursoCrit.trim().isEmpty()) return;
            CursosCRUD ccrud = new CursosCRUD();
            List<Cursos> cursos = ccrud.listAll();
            Cursos foundC=null;
            String cc = cursoCrit.trim().toLowerCase();
            for (Cursos ccx: cursos) if ((String.valueOf(ccx.getIdCursos()).equals(cc)) || (ccx.getNombreCurso()!=null && ccx.getNombreCurso().toLowerCase().contains(cc))) { foundC=ccx; break; }
            if (foundC==null) { JOptionPane.showMessageDialog(this, "Curso no encontrado"); return; }
            MatriculasCRUD mcrud = new MatriculasCRUD();
            Matriculas m = new Matriculas();
            m.setIdEstudiante(found.getIdEstudiantes());
            m.setIdCurso(foundC.getIdCursos());
            if (mcrud.create(m)) { JOptionPane.showMessageDialog(this, "✅ Matriculado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al matricular");
        });

        btnEliminar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione una matrícula"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila,0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar matrícula ID " + id + "?") == JOptionPane.YES_OPTION) {
                MatriculasCRUD mcrud = new MatriculasCRUD();
                if (mcrud.delete(id)) { JOptionPane.showMessageDialog(this, "✅ Eliminado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al eliminar");
            }
        });
    }

    private void cargarTabla() {
        MatriculasCRUD mcrud = new MatriculasCRUD();
        EstudianteSCRUD escrud = new EstudianteSCRUD();
        CursosCRUD ccrud = new CursosCRUD();
        List<Matriculas> lista = mcrud.listAll();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (Matriculas m: lista) {
            Estudiantes e = escrud.read(m.getIdEstudiante());
            Cursos c = ccrud.read(m.getIdCurso());
            modelo.addRow(new Object[]{m.getIdMatricula(), e!=null?e.getEstnombre():"-", e!=null?e.getEstcedula():"-", c!=null?c.getNombreCurso()+" ("+c.getIdCursos()+")":"-"});
        }
    }
}
```

---

## 4. UsuariosPanel.java

Panel para gestión de usuarios (admin).

```java
package Interfaces;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Clases.Usuario;
import CRUD.UsuarioCRUD;

public class UsuariosPanel extends JPanel {
    private JTable tabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar;

    public UsuariosPanel() {
        initComponents();
        configurarAcciones();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new java.awt.BorderLayout());
        JPanel top = new JPanel(); top.setPreferredSize(new java.awt.Dimension(800,60));
        top.add(new JLabel("Gestión de Usuarios"));
        add(top, java.awt.BorderLayout.NORTH);
        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][] {}, new String[]{"ID","Usuario","Rol"}));
        add(new JScrollPane(tabla), java.awt.BorderLayout.CENTER);
        JPanel bottom = new JPanel(new java.awt.FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        bottom.add(btnAgregar); bottom.add(btnEditar); bottom.add(btnEliminar); bottom.add(btnBuscar);
        add(bottom, java.awt.BorderLayout.SOUTH);
    }

    private void configurarAcciones() {
        btnBuscar.addActionListener(ae -> {
            String criterio = JOptionPane.showInputDialog(this, "Ingrese usuario o rol (vacío = todos):");
            UsuarioCRUD crud = new UsuarioCRUD();
            List<Usuario> lista = crud.listAll();
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            if (criterio==null || criterio.trim().isEmpty()) {
                for (Usuario u: lista) modelo.addRow(new Object[]{u.getIdUsuario(), u.getUsername(), u.getRol()});
                return;
            }
            String c = criterio.trim().toLowerCase();
            for (Usuario u: lista) {
                if ((u.getUsername()!=null && u.getUsername().toLowerCase().contains(c)) || (u.getRol()!=null && u.getRol().toLowerCase().contains(c))) {
                    modelo.addRow(new Object[]{u.getIdUsuario(), u.getUsername(), u.getRol()});
                }
            }
        });

        btnAgregar.addActionListener(ae -> {
            JTextField tfUser = new JTextField();
            JTextField tfPass = new JTextField();
            JTextField tfRol = new JTextField();
            JPanel p = new JPanel(new GridLayout(0,1));
            p.add(new JLabel("Usuario:")); p.add(tfUser);
            p.add(new JLabel("Contraseña:")); p.add(tfPass);
            p.add(new JLabel("Rol (admin/secretario):")); p.add(tfRol);
            int res = JOptionPane.showConfirmDialog(this, p, "Agregar usuario", JOptionPane.OK_CANCEL_OPTION);
            if (res==JOptionPane.OK_OPTION) {
                String user = tfUser.getText().trim();
                String pass = tfPass.getText().trim();
                String rol = tfRol.getText().trim();
                UsuarioCRUD crud = new UsuarioCRUD();
                List<Usuario> lista = crud.listAll();
                int maxId=0;
                for (Usuario uu: lista) if (uu.getIdUsuario()>maxId) maxId=uu.getIdUsuario();
                Usuario u = new Usuario();
                u.setIdUsuario(maxId+1);
                u.setUsername(user);
                u.setPassword(pass);
                u.setRol(rol);
                if (crud.create(u)) { JOptionPane.showMessageDialog(this, "✅ Usuario creado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error");
            }
        });

        btnEditar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione un usuario"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila,0);
            String user = String.valueOf(modelo.getValueAt(fila,1));
            String rol = String.valueOf(modelo.getValueAt(fila,2));
            JTextField tfUser = new JTextField(user);
            JTextField tfPass = new JTextField();
            JTextField tfRol = new JTextField(rol);
            JPanel p = new JPanel(new GridLayout(0,1));
            p.add(new JLabel("Usuario:")); p.add(tfUser);
            p.add(new JLabel("Contraseña (nuevo):")); p.add(tfPass);
            p.add(new JLabel("Rol:")); p.add(tfRol);
            int res = JOptionPane.showConfirmDialog(this, p, "Editar usuario", JOptionPane.OK_CANCEL_OPTION);
            if (res==JOptionPane.OK_OPTION) {
                Usuario u = new Usuario();
                u.setIdUsuario(id);
                u.setUsername(tfUser.getText().trim());
                String pass = tfPass.getText().trim();
                if (!pass.isEmpty()) u.setPassword(pass); else u.setPassword("");
                u.setRol(tfRol.getText().trim());
                UsuarioCRUD crud = new UsuarioCRUD();
                if (crud.update(u)) { JOptionPane.showMessageDialog(this, "✅ Actualizado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error");
            }
        });

        btnEliminar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione un usuario"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila,0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar usuario ID " + id + "?") == JOptionPane.YES_OPTION) {
                UsuarioCRUD crud = new UsuarioCRUD();
                if (crud.delete(id)) { JOptionPane.showMessageDialog(this, "✅ Eliminado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error");
            }
        });
    }

    private void cargarTabla() {
        UsuarioCRUD crud = new UsuarioCRUD();
        List<Usuario> lista = crud.listAll();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (Usuario u: lista) modelo.addRow(new Object[]{u.getIdUsuario(), u.getUsername(), u.getRol()});
    }
}
```

---

## Cómo usar estos paneles:

1. Copia el código correspondiente a cada panel
2. Reemplaza el contenido actual en tus archivos `.java`
3. Los `.form` se actualizarán automáticamente en NetBeans al editar
4. Usa los paneles en tus JFrames o en VentanaPrincipal mediante:
   ```java
   EstudiantesPanel est = new EstudiantesPanel("admin");
   CursosPanel cur = new CursosPanel("admin");
   MatriculasPanel mat = new MatriculasPanel();
   UsuariosPanel usr = new UsuariosPanel();
   ```

