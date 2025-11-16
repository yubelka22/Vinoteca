package com.danielaperez.practica1.vista;

import com.danielaperez.practica1.base.Vino;
import com.danielaperez.practica1.base.VinoBlanco;
import com.danielaperez.practica1.base.VinoRosado;
import com.danielaperez.practica1.base.VinoTinto;
import com.danielaperez.practica1.util.Util;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class VinotecaControlador implements ActionListener, ListSelectionListener, WindowListener{

        private FormularioPrincipal vista;
        private VinotecaModelo modelo;
        private File ultimaRutaExportada;

        public VinotecaControlador(FormularioPrincipal vista, VinotecaModelo modelo) {
            this.vista = vista;
            this.modelo = modelo;

            try {
                cargarDatosConfiguracion();
            } catch (IOException e) {
                System.out.println("No existe fichero de configuracion");
            }

            //listener al arrancar el controlador
            addActionListener(this);
            addWindowListener(this);
            addListSelectionListener(this);
        }

        private boolean hayCamposVacios() {
            StringBuilder camposVacios = new StringBuilder();
            if (vista.marcaTxt.getText().isEmpty()){
                camposVacios.append("\n-Marca del vino");
            }
            if (vista.fechaCreacionDPicker.getDate()==null){
                camposVacios.append("\n-Fecha de Creación del Vino");
            }
            if (vista.fechaCaducidadDPicker.getDate()==null){
                camposVacios.append("\n-Fecha de Caducidad del Vino");
            }
            if (vista.porcentajeAlcohol.getText().isEmpty()){
                camposVacios.append("\n-Porcentaje de Alcohol del Vino");
            }
            if (vista.precio.getText().isEmpty()){
                camposVacios.append("\n-Precio del Vino");
            }
            if (vista.atributoTxt.getText().isEmpty()){
                camposVacios.append("\n-Color, Aroma y Sabor del Vino");
            }
            if (camposVacios.length()>0){
                Util.mensajeError("Los siguientes estan vacios: "+ camposVacios.toString());
                return true;
            }
            return false;
        }

        private void limpiarCampos() {
            vista.marcaTxt.setText(null);
            vista.fechaCreacionDPicker.setText(null);
            vista.fechaCaducidadDPicker.setText(null);
            vista.porcentajeAlcohol.setText(null);
            vista.precio.setText(null);
            vista.atributoTxt.setText(null);
        }

        //listener botones
        private void addActionListener(ActionListener listener) {
            vista.importarButton.addActionListener(listener);
            vista.exportarButton.addActionListener(listener);
            vista.crearButton.addActionListener(listener);
            vista.btnVinoBlanco.addActionListener(listener);
            vista.btnVinoTinto.addActionListener(listener);
            vista.btnVinoRosado.addActionListener(listener);
            vista.limpiarButton.addActionListener(listener);
        }

        //listener ventana (boton cerrar)
        private void addWindowListener(WindowListener listener) {
            vista.frame.addWindowListener(listener);
        }

        //listener de la lista
        private void addListSelectionListener(ListSelectionListener listener) {
            vista.list1.addListSelectionListener(listener);
        }

        public void refrescar() {
            vista.dlmVino.clear();
            //modelo.obtenerVinos -> contiene la lista de Vino
            for (Vino unVino:modelo.obtenerVinos()) {
                vista.dlmVino.addElement(unVino);
            }
        }

        private void cargarDatosConfiguracion() throws IOException {
            Properties configuracion = new Properties();
            configuracion.load(new FileReader("vehiculos.conf"));
            ultimaRutaExportada= new File(configuracion.getProperty("ultimaRutaExportada"));
        }

        private void actualizarDatosConfiguracion(File ultimaRutaExportada) {
            this.ultimaRutaExportada=ultimaRutaExportada;
        }

        private void guardarConfiguracion() throws IOException {
            Properties configuracion=new Properties();
            configuracion.setProperty("ultimaRutaExportada"
                    ,ultimaRutaExportada.getAbsolutePath());
            configuracion.store(new PrintWriter("vehiculos.conf")
                    ,"Datos configuracion vehiculos");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand=e.getActionCommand();

            switch (actionCommand) {
                case "Crear":
                    if (hayCamposVacios()) {
                        break;
                    }
                    int porcentajeAlcohol= 0;
                    try{
                        porcentajeAlcohol = Integer.parseInt(vista.porcentajeAlcohol.getText());
                    }catch (NumberFormatException ne){
                        Util.mensajeError("Introduce bien el porcentaje de alcohol (número entero)");
                        break;
                    }
                    porcentajeAlcohol = Integer.parseInt(vista.porcentajeAlcohol.getText());
                    double precio = Double.parseDouble(vista.precio.getText());

                    if (porcentajeAlcohol < 0 || precio < 0){
                        Util.mensajeError("No pueden ser negativos ni el precio ni el porcentaje de alchohol");
                        break;
                    }
                    if (modelo.existeMarca(vista.marcaTxt.getText())) {
                        Util.mensajeError("Ya existe un Vino con esa Marca" +
                                "\n"+vista.marcaTxt.getText());
                        break;
                    }

                    try {
                        if (vista.btnVinoBlanco.isSelected()) {
                            modelo.altaVinoBlanco(vista.marcaTxt.getText(), vista.fechaCreacionDPicker.getDate(),
                                    vista.fechaCaducidadDPicker.getDate(), porcentajeAlcohol, Double.parseDouble(vista.precio.getText()),
                                    vista.denominacionOrigen.getSelectedItem().toString(), vista.atributoTxt.getText());
                        } else
                            if(vista.btnVinoTinto.isSelected()){
                                modelo.altaVinoTinto(vista.marcaTxt.getText(), vista.fechaCreacionDPicker.getDate(),
                                        vista.fechaCaducidadDPicker.getDate(), porcentajeAlcohol, Double.parseDouble(vista.precio.getText()),
                                        vista.denominacionOrigen.getSelectedItem().toString(), vista.atributoTxt.getText());
                        }else
                            if(vista.btnVinoRosado.isSelected()){
                                modelo.altaRosado(vista.marcaTxt.getText(), vista.fechaCreacionDPicker.getDate(),
                                        vista.fechaCaducidadDPicker.getDate(), porcentajeAlcohol, Double.parseDouble(vista.precio.getText()),
                                        vista.denominacionOrigen.getSelectedItem().toString(), vista.atributoTxt.getText());
                            }
                    }catch (NumberFormatException ne){
                        Util.mensajeError("Introduce números decimales");
                    }
                    limpiarCampos();
                    refrescar();
                    break;
                case "Importar":
                    JFileChooser selectorFichero = Util.crearSelectorFichero(ultimaRutaExportada
                            ,"Archivos XML","xml");
                    int opt =selectorFichero.showOpenDialog(null);
                    if (opt==JFileChooser.APPROVE_OPTION) {
                        try {
                            modelo.importarXML(selectorFichero.getSelectedFile());
                        } catch (ParserConfigurationException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (SAXException ex) {
                            ex.printStackTrace();
                        }
                        limpiarCampos();
                        refrescar();
                    }
                    break;
                case "Exportar":
                    JFileChooser selectorFichero2=Util.crearSelectorFichero(ultimaRutaExportada
                            ,"Archivos XML","xml");
                    int opt2=selectorFichero2.showSaveDialog(null);
                    if (opt2==JFileChooser.APPROVE_OPTION) {
                        try {
                            modelo.exportarXML(selectorFichero2.getSelectedFile());
                        } catch (ParserConfigurationException ex) {
                            ex.printStackTrace();
                        } catch (TransformerException ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                case "VinoTinto":
                    vista.atributoTxt.setText("Aroma");
                    break;
                case "VinoBlanco":
                    vista.atributoTxt.setText("Color");
                    break;
                case "VinoRosado":
                    vista.atributoTxt.setText("Sabor");
                    break;
                case "Limpiar":
                    limpiarCampos();
                    break;
            }
        }

        @Override
        public void windowClosing(WindowEvent e) {
            int resp= Util.mensajeConfirmacion("¿Desea cerrar la ventana?","Salir");
            if (resp== JOptionPane.OK_OPTION) {
                try {
                    guardarConfiguracion();
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al guardar");
                }
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                Vino vinoSeleccionado = (Vino )vista.list1.getSelectedValue();
                vista.marcaTxt.setText(vinoSeleccionado.getMarca());
                vista.fechaCreacionDPicker.setDate(vinoSeleccionado.getFechaCreacion());
                vista.fechaCaducidadDPicker.setDate(vinoSeleccionado.getFechaCaducidad());
                vista.porcentajeAlcohol.setText(String.valueOf(vinoSeleccionado.getPorcentajeAlcohol()));
                vista.precio.setText((String.valueOf(vinoSeleccionado.getPrecio())));
                if (vinoSeleccionado instanceof VinoBlanco) {
                    vista.btnVinoBlanco.doClick();
                    vista.atributoTxt.setText(String.valueOf(((VinoBlanco) vinoSeleccionado).getColor()));
                } else
                    if (vinoSeleccionado instanceof VinoTinto){
                    vista.btnVinoTinto.doClick();
                    vista.atributoTxt.setText(String.valueOf(((VinoTinto)vinoSeleccionado).getAroma()));
                }else
                    if(vinoSeleccionado instanceof VinoRosado){
                        vista.btnVinoTinto.doClick();
                        vista.atributoTxt.setText(String.valueOf(((VinoRosado)vinoSeleccionado).getSabor()));
                    }
            }
        }

        //no los uso

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }

    }

