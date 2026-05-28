package com.danielaperez.practica1.vista;

import com.danielaperez.practica1.base.Vino;
import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.*;

public class FormularioPrincipal {
    public JPanel panelPrincipal;
    public JRadioButton btnVinoBlanco;
    public JRadioButton btnVinoTinto;
    public JRadioButton btnVinoRosado;
    public JTextField marcaTxt;
    public JTextField precio;
    public JTextField porcentajeAlcohol;
    public DatePicker fechaCreacionDPicker;
    public DatePicker fechaCaducidadDPicker;
    public JComboBox denominacionOrigen;
    public JButton crearButton;
    public JButton importarButton;
    public JButton exportarButton;
    public JList<Vino> list1;
    public JTextField atributoTxt;
    public JButton limpiarButton;
    public JLabel atributos;
    public JButton btnEliminar;

    public JFrame frame;
    public DefaultListModel<Vino> dlmVino;

    public FormularioPrincipal() {
        frame = new JFrame("VinotecaMVC");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 550);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        ImageIcon img = new ImageIcon("img/copa-de-vino.png");
        frame.setIconImage(img.getImage());

        initComponents();
    }

    public void initComponents() {
        dlmVino = new DefaultListModel<Vino>();
        list1.setModel(dlmVino);
    }

    public DefaultListModel eliminarDatos() {
        DefaultListModel model = (DefaultListModel) list1.getModel();
        model.remove(list1.getSelectedIndex());
        return model;
    }
}