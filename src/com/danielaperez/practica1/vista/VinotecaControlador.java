package com.danielaperez.practica1.vista;

import com.danielaperez.practica1.base.Vino;
import com.danielaperez.practica1.base.VinoBlanco;
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

public class VinotecaControlador {

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
            if (vista.atrubitoTxt.getText().isEmpty()){
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
            vista.marcaTxt.setText(null);
            vista.modeloTxt.setText(null);
            vista.fechaMatriculacionDPicker.setText(null);
            vista.kmsPlazasTxt.setText(null);
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
            //modelo.obtenerVehiculos -> contiene la lista de vehiculos
            for (Vino unVehiculo:modelo.obtenerVinos()) {
                vista.dlmVino.addElement(unVehiculo);
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
                case "Nuevo":
                    if (hayCamposVacios()) {
                        Util.mensajeError("Los siguientes campos estan vacios " +
                                "\n Matricula\nMarca\nModelo\nFecha matriculacion" +
                                "\n"+vista.kmsPlazasLbl.getText());
                        break;
                    }
                    if (modelo.existeMatricula(vista.matriculaTxt.getText())) {
                        Util.mensajeError("Ya existe un vehiculo con la matricula" +
                                "\n"+vista.matriculaTxt.getText());
                        break;
                    }
                    if (vista.cocheRadioButton.isSelected()) {
                        modelo.altaCoche(vista.matriculaTxt.getText(),vista.marcaTxt.getText(),
                                vista.modeloTxt.getText(),vista.fechaMatriculacionDPicker.getDate()
                                , Integer.parseInt(vista.kmsPlazasTxt.getText()));
                    } else {
                        modelo.altaMoto(vista.matriculaTxt.getText(),vista.marcaTxt.getText(),
                                vista.modeloTxt.getText(),vista.fechaMatriculacionDPicker.getDate()
                                , Double.parseDouble(vista.kmsPlazasTxt.getText()));
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
                case "Moto":
                    vista.kmsPlazasLbl.setText("kms");
                    break;
                case "Coche":
                    vista.kmsPlazasLbl.setText("N plazas");
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
                }
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                Vino vehiculoSeleccionado = vista.list1.getSelectedValue();
                vista.matriculaTxt.setText(vehiculoSeleccionado.getMatricula());
                vista.modeloTxt.setText(vehiculoSeleccionado.getModelo());
                vista.marcaTxt.setText(vehiculoSeleccionado.getMarca());
                vista.fechaMatriculacionDPicker.setDate(vehiculoSeleccionado.getFechaMatriculacion());
                if (vehiculoSeleccionado instanceof VinoBlanco) {
                    vista.btnVinoBlanco.doClick();
                    vista.kmsPlazasTxt.setText(String.valueOf(((VinoBlanco) vehiculoSeleccionado).getColor()));
                } else
                    if (vehiculoSeleccionado instanceof VinoTinto){
                    vista.btnVinoTinto.doClick();
                    vista.kmsPlazasTxt.setText(String.valueOf(((VinoTinto)vehiculoSeleccionado).getKms()));
                }else
                    if(){

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

