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

import Clases.Estudiantes;
import CRUD.EstudianteSCRUD;

public class EstudiantesPanel extends JPanel {

    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar;
    private JPanel panelButtons;

    public EstudiantesPanel(String role) {
        initComponents();
        configurarAcciones(role);
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(52, 152, 219));
        panelTop.setPreferredSize(new Dimension(800, 60));

        JLabel titulo = new JLabel("Gestor de Estudiantes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        panelTop.add(titulo);

        add(panelTop, BorderLayout.NORTH);

        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Cédula", "Nombre", "Apellido", "Edad", "Teléfono" }));
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(41, 128, 185));
        tabla.getTableHeader().setForeground(Color.WHITE);

        scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panelButtons.setBackground(new Color(236, 240, 241));

        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        btnEditar = crearBoton("Editar", new Color(241, 196, 15));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnBuscar = crearBoton("Buscar", new Color(52, 152, 219));

        panelButtons.add(btnAgregar);
        panelButtons.add(btnEditar);
        panelButtons.add(btnEliminar);
        panelButtons.add(btnBuscar);

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
        // Enable/disable based on role
        boolean isAdmin = "admin".equalsIgnoreCase(role);
        boolean isSecretario = "secretario".equalsIgnoreCase(role);

        // Secretary: only view and search
        if (isSecretario) {
            btnAgregar.setEnabled(true);
            btnEditar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }

        if (isAdmin) {
            btnAgregar.setEnabled(true);
            btnEditar.setEnabled(true);
            btnEliminar.setEnabled(true);
        }

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
                    // Validaciones: cédula y teléfono deben ser numéricos; nombre y apellido solo letras
                    if (ced.isEmpty() || nom.isEmpty() || ape.isEmpty() || tel.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios");
                        return;
                    }
                    if (!isNumeric(ced)) {
                        JOptionPane.showMessageDialog(this, "La cédula debe contener sólo números");
                        return;
                    }
                    if (!isNumeric(tel)) {
                        JOptionPane.showMessageDialog(this, "El teléfono debe contener sólo números");
                        return;
                    }
                    if (!isAlpha(nom) || !isAlpha(ape)) {
                        JOptionPane.showMessageDialog(this, "Nombre y apellido deben contener sólo letras");
                        return;
                    }

                    EstudianteSCRUD crud = new EstudianteSCRUD();
                    List<Estudiantes> lista = crud.readAll();
                    int maxId = 0;
                    for (Estudiantes ex : lista) {
                        if (ex.getIdEstudiantes() > maxId) {
                            maxId = ex.getIdEstudiantes();
                        }
                    }
                    int nuevoId = maxId + 1;

                    Estudiantes nuevo = new Estudiantes();
                    nuevo.setIdEstudiantes(nuevoId);
                    nuevo.setEstcedula(ced);
                    nuevo.setEstnombre(nom);
                    nuevo.setEstapellido(ape);
                    nuevo.setEstedad(edad);
                    nuevo.setEsttelefono(tel);

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
                    // Validaciones
                    if (nced.isEmpty() || nnom.isEmpty() || nape.isEmpty() || ntel.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios");
                        return;
                    }
                    if (!isNumeric(nced)) {
                        JOptionPane.showMessageDialog(this, "La cédula debe contener sólo números");
                        return;
                    }
                    if (!isNumeric(ntel)) {
                        JOptionPane.showMessageDialog(this, "El teléfono debe contener sólo números");
                        return;
                    }
                    if (!isAlpha(nnom) || !isAlpha(nape)) {
                        JOptionPane.showMessageDialog(this, "Nombre y apellido deben contener sólo letras");
                        return;
                    }

                    Estudiantes actualizado = new Estudiantes();
                    actualizado.setIdEstudiantes(id);
                    actualizado.setEstcedula(nced);
                    actualizado.setEstnombre(nnom);
                    actualizado.setEstapellido(nape);
                    actualizado.setEstedad(nedad);
                    actualizado.setEsttelefono(ntel);

                    EstudianteSCRUD crud = new EstudianteSCRUD();
                    if (crud.update(actualizado)) {
                        JOptionPane.showMessageDialog(this, " Estudiante actualizado");
                        cargarTabla();
                    } else {
                        JOptionPane.showMessageDialog(this, " Error al actualizar");
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
            modelo.addRow(new Object[] { e.getIdEstudiantes(), e.getEstcedula(), e.getEstnombre(), e.getEstapellido(), e.getEstedad(), e.getEsttelefono() });
        }
    }

    // Validación simple: solo dígitos
    private boolean isNumeric(String s) {
        return s != null && s.matches("\\d+");
    }

    // Validación de nombre/apellido: letras (incluye acentos) y espacios
    private boolean isAlpha(String s) {
        return s != null && s.matches("[a-zA-ZÀ-ÿ\\s]+");
    }
}
