package com.danielaperez.practica1.vista;

import com.danielaperez.practica1.base.Vino;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class FormularioPrincipal {
    public JPanel panelPrincipal;
    public JRadioButton btnVinoBlanco;
    public JRadioButton btnVinoTinto;
    public  JRadioButton btnVinoRosado;
    public JTextField marcaTxt;
    public  JTextField precio;
    public JTextField porcentajeAlcohol;
    public DatePicker fechaCreacionDPicker;
    public DatePicker fechaCaducidadDPicker;
    public JComboBox denominacionOrigen;
    public JButton crearButton;
    public JButton importarButton;
    public JButton exportarButton;
    public JList list1;
    public JTextField atrubitoTxt;
    public JButton limpiarButton;

    public JFrame frame;

    public DefaultListModel <Vino> dlmVino;

    public FormularioPrincipal(){
        frame = new JFrame("VinotecaMVC");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        initComponents();
    }

    public void initComponents(){
        dlmVino = new DefaultListModel<Vino>();
        list1.setModel(dlmVino);
    }
}
