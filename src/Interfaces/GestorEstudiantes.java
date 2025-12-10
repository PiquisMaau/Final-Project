/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Clases.Estudiantes;
import CRUD.*;

/**
 *
 * @author Sebas
 */
public class GestorEstudiantes extends javax.swing.JFrame {

    private JPanel panelTop;
    private JPanel panelCenter;
    private JPanel panelButtons;
    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar, btnMostrartodo;

    public GestorEstudiantes() {
        initComponents2();
        setLocationRelativeTo(null);
    }

    private void initComponents2() {

        setTitle("Gestor de Estudiantes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelTop = new JPanel();
        panelTop.setBackground(new Color(52, 152, 219)); 
        panelTop.setPreferredSize(new Dimension(800, 80));

        JLabel titulo = new JLabel("Gestor de Estudiantes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        panelTop.add(titulo);
        add(panelTop, BorderLayout.NORTH);

        // ====== PANEL CENTRAL (TABLA) ======
        panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBackground(Color.WHITE);

        tabla = new JTable();
        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "ID", "Cédula", "Nombre", "Apellido", "Edad", "Teléfono" }));

        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(41, 128, 185));
        tabla.getTableHeader().setForeground(Color.WHITE);

        scroll = new JScrollPane(tabla);
        panelCenter.add(scroll, BorderLayout.CENTER);

        add(panelCenter, BorderLayout.CENTER);

        // ====== PANEL DE BOTONES ======
        panelButtons = new JPanel();
        panelButtons.setBackground(new Color(236, 240, 241));
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        btnEditar = crearBoton("Editar", new Color(241, 196, 15));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnBuscar = crearBoton("Buscar", new Color(52, 152, 219));
     btnMostrartodo = crearBoton("Mostrar Estudiantes", new Color(45, 90, 180));

        panelButtons.add(btnAgregar);
        panelButtons.add(btnEditar);
        panelButtons.add(btnEliminar);
        panelButtons.add(btnBuscar);
        panelButtons.add(btnMostrartodo);

        add(panelButtons, BorderLayout.SOUTH);

        // Configurar actions y cargar datos iniciales
        configurarAcciones();
        cargarTabla();
    }

    // ====== MÉTODO PARA CREAR BOTONES BONITOS ======
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setFocusPainted(false);
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
    }

    private Estudiantes crearEstudiante(String cedula, String nombre, String apellido, int edad, String telefono, int id) {
        Estudiantes e = new Estudiantes();
        e.setIdEstudiantes(id);
        e.setEstcedula(cedula);
        e.setEstnombre(nombre);
        e.setEstapellido(apellido);
        e.setEstedad(edad);
        e.setEsttelefono(telefono);
        return e;
    }

    private void configurarAcciones() {
        // AGREGAR
        btnAgregar.addActionListener(ae -> {
            JTextField tfCedula = new JTextField();
            JTextField tfNombre = new JTextField();
            JTextField tfApellido = new JTextField();
            JTextField tfEdad = new JTextField();
            JTextField tfTelefono = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Cédula:"));
            panel.add(tfCedula);
            panel.add(new JLabel("Nombre:"));
            panel.add(tfNombre);
            panel.add(new JLabel("Apellido:"));
            panel.add(tfApellido);
            panel.add(new JLabel("Edad:"));
            panel.add(tfEdad);
            panel.add(new JLabel("Teléfono:"));
            panel.add(tfTelefono);
            

            int res = JOptionPane.showConfirmDialog(this, panel, "Agregar estudiante", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
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
                    for (Estudiantes ex : lista) {
                        if (ex.getIdEstudiantes() > maxId) {
                            maxId = ex.getIdEstudiantes();
                        }
                    }
                    int nuevoId = maxId + 1;

                    Estudiantes nuevo = crearEstudiante(ced, nom, ape, edad, tel, nuevoId);
                    if (crud.create(nuevo)) {
                        JOptionPane.showMessageDialog(this, " Estudiante agregado");
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(this, " Error al agregar");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Edad inválida");
                }
            }
        });


        btnMostrartodo.addActionListener(ae -> {
            cargarTabla();
        });

        // EDITAR
        btnEditar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para editar");
                return;
            }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
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
            panel.add(new JLabel("Cédula:"));
            panel.add(tfCedula);
            panel.add(new JLabel("Nombre:"));
            panel.add(tfNombre);
            panel.add(new JLabel("Apellido:"));
            panel.add(tfApellido);
            panel.add(new JLabel("Edad:"));
            panel.add(tfEdad);
            panel.add(new JLabel("Teléfono:"));
            panel.add(tfTelefono);

            int res = JOptionPane.showConfirmDialog(this, panel, "Editar estudiante", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    String nced = tfCedula.getText().trim();
                    String nnom = tfNombre.getText().trim();
                    String nape = tfApellido.getText().trim();
                    int nedad = Integer.parseInt(tfEdad.getText().trim());
                    String ntel = tfTelefono.getText().trim();

                    Estudiantes actualizado = crearEstudiante(nced, nnom, nape, nedad, ntel, id);
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
        });

        // ELIMINAR
        btnEliminar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar");
                return;
            }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila, 0);
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar estudiante ID " + id + "?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                EstudianteSCRUD crud = new EstudianteSCRUD();
                if (crud.delete(id)) {

                    JOptionPane.showMessageDialog(this, "✅ Eliminado");
                    crud.reordenarIDs();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar");
                }
            }
        });

        // BUSCAR
        btnBuscar.addActionListener(ae -> {
            String criterio = JOptionPane.showInputDialog(this, "Ingrese cédula o nombre (vacío = todos):");
            EstudianteSCRUD crud = new EstudianteSCRUD();
            List<Estudiantes> lista = crud.readAll();
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            if (criterio == null || criterio.trim().isEmpty()) {
                for (Estudiantes e : lista) {
                    modelo.addRow(new Object[] { e.getIdEstudiantes(), e.getEstcedula(), e.getEstnombre(),
                            e.getEstapellido(), e.getEstedad(), e.getEsttelefono() });
                }
                return;
            }
            String c = criterio.trim().toLowerCase();
            for (Estudiantes e : lista) {
                if ((e.getEstcedula() != null && e.getEstcedula().toLowerCase().contains(c))
                        || (e.getEstnombre() != null && e.getEstnombre().toLowerCase().contains(c))
                        || (e.getEstapellido() != null && e.getEstapellido().toLowerCase().contains(c))) {
                    modelo.addRow(new Object[] { e.getIdEstudiantes(), e.getEstcedula(), e.getEstnombre(),
                            e.getEstapellido(), e.getEstedad(), e.getEsttelefono() });
                }
            }
        });
    }

    private void cargarTabla() {
        EstudianteSCRUD crud = new EstudianteSCRUD();
        List<Estudiantes> lista = crud.readAll();

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // limpiar

        for (Estudiantes e : lista) {
            modelo.addRow(new Object[] {
                    e.getIdEstudiantes(),
                    e.getEstcedula(),
                    e.getEstnombre(),
                    e.getEstapellido(),
                    e.getEstedad(),
                    e.getEsttelefono()
            });
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestorEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestorEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestorEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestorEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */

        new GestorEstudiantes().setVisible(true);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
