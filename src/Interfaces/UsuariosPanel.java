package Interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
    private JScrollPane scroll;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar;
    private JPanel panelButtons;

    public UsuariosPanel() {
        initComponents();
        configurarAcciones();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(800, 60));
        JLabel titulo = new JLabel("Gestión de Usuarios");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        top.add(titulo);
        add(top, BorderLayout.NORTH);

        tabla = new JTable();
        tabla.setModel(new DefaultTableModel(new Object[][] {}, new String[]{"ID","Usuario","Rol"}));
        scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        panelButtons.add(btnAgregar); panelButtons.add(btnEditar); panelButtons.add(btnEliminar); panelButtons.add(btnBuscar);
        add(panelButtons, BorderLayout.SOUTH);
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
            int res = JOptionPane.showConfirmDialog(this, p, "Agregar usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res==JOptionPane.OK_OPTION) {
                String user = tfUser.getText().trim(); String pass = tfPass.getText().trim(); String rol = tfRol.getText().trim();
                UsuarioCRUD crud = new UsuarioCRUD();
                List<Usuario> lista = crud.listAll(); int maxId=0; for (Usuario uu: lista) if (uu.getIdUsuario()>maxId) maxId=uu.getIdUsuario();
                Usuario u = new Usuario(); u.setIdUsuario(maxId+1); u.setUsername(user); u.setPassword(pass); u.setRol(rol);
                if (crud.create(u)) { JOptionPane.showMessageDialog(this, "✅ Usuario creado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al crear usuario");
            }
        });

        btnEditar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow(); if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione un usuario"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int id = (int) modelo.getValueAt(fila,0);
            String user = String.valueOf(modelo.getValueAt(fila,1));
            String rol = String.valueOf(modelo.getValueAt(fila,2));
            JTextField tfUser = new JTextField(user); JTextField tfPass = new JTextField(); JTextField tfRol = new JTextField(rol);
            JPanel p = new JPanel(new GridLayout(0,1)); p.add(new JLabel("Usuario:")); p.add(tfUser); p.add(new JLabel("Contraseña (nuevo):")); p.add(tfPass); p.add(new JLabel("Rol:")); p.add(tfRol);
            int res = JOptionPane.showConfirmDialog(this, p, "Editar usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res==JOptionPane.OK_OPTION) {
                Usuario u = new Usuario(); u.setIdUsuario(id); u.setUsername(tfUser.getText().trim()); String pass = tfPass.getText().trim(); if (!pass.isEmpty()) u.setPassword(pass); else u.setPassword(""); u.setRol(tfRol.getText().trim());
                UsuarioCRUD crud = new UsuarioCRUD(); if (crud.update(u)) { JOptionPane.showMessageDialog(this, "✅ Usuario actualizado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al actualizar");
            }
        });

        btnEliminar.addActionListener(ae -> {
            int fila = tabla.getSelectedRow(); if (fila==-1) { JOptionPane.showMessageDialog(this, "Seleccione un usuario"); return; }
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); int id = (int) modelo.getValueAt(fila,0);
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf==JOptionPane.YES_OPTION) { UsuarioCRUD crud = new UsuarioCRUD(); if (crud.delete(id)) { JOptionPane.showMessageDialog(this, "✅ Eliminado"); cargarTabla(); } else JOptionPane.showMessageDialog(this, "❌ Error al eliminar"); }
        });
    }

    private void cargarTabla() {
        UsuarioCRUD crud = new UsuarioCRUD(); 
        List<Usuario> lista = crud.listAll(); DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); modelo.setRowCount(0);
        for (Usuario u: lista) modelo.addRow(new Object[]{u.getIdUsuario(), u.getUsername(), u.getRol()});
    }
}
