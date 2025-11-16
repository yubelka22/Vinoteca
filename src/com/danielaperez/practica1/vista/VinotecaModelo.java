package com.danielaperez.practica1.vista;

import com.danielaperez.practica1.base.Vino;
import com.danielaperez.practica1.base.VinoBlanco;

import com.danielaperez.practica1.base.VinoRosado;
import com.danielaperez.practica1.base.VinoTinto;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.ArrayList;

public class VinotecaModelo {


    private ArrayList<Vino> listaVinos;


    public VinotecaModelo() {
        listaVinos = new ArrayList<Vino>();
    }

    public ArrayList<Vino> obtenerVinos() {
        return listaVinos;
    }

    //altaVinoBlanco
    public void altaVinoBlanco(String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                               int porcentajeAlcohol, double precio, String denominacionOrigen, String color) {
        VinoBlanco nuevoVinoBlanco = new VinoBlanco(marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol,
                precio, denominacionOrigen, color);
        listaVinos.add(nuevoVinoBlanco);
    }

    //altaVinoTinto
    public void altaVinoTinto(String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                              int porcentajeAlcohol, double precio, String denominacionOrigen, String aroma) {
        VinoTinto nuevoVinoTinto = new VinoTinto(marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol,
                precio, denominacionOrigen, aroma);
        listaVinos.add(nuevoVinoTinto);
    }

    //altaVinoRosado
    public void altaRosado(String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                           int porcentajeAlcohol, double precio, String denominacionOrigen, String sabor) {
        VinoRosado nuevoVinoRosado = new VinoRosado(marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol,
                precio, denominacionOrigen, sabor);
        listaVinos.add(nuevoVinoRosado);
    }

    //existeVino
    public boolean existeMarca(String marca) {
        for (Vino unVino : listaVinos) {
            if (unVino.getMarca().equals(marca)) {
                return true;
            }
        }
        return false;
    }

    //exportarXML
    public void exportarXML(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //implementacion DOM -> web
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument(null, "xml", null);

        //añado el nodo raiz (primera etiqueta)
        Element raiz = documento.createElement("Vino");
        documento.getDocumentElement().appendChild(raiz);

        Element nodoVino = null;
        Element nodoDatos = null;
        Text texto = null;

        //voy a añadir una etiqueta dentro de vino
        //en funcion de si es vino blanco, vino tinto o vino rosado
        for (Vino unVino : listaVinos) {
            if (unVino instanceof VinoBlanco) {
                nodoVino = documento.createElement("VinoBlanco");
            } else if (unVino instanceof VinoTinto) {
                nodoVino = documento.createElement("VinoTinto");
            } else if (unVino instanceof VinoTinto) {
                nodoVino = documento.createElement("VinoRosado");
            }
            raiz.appendChild(nodoVino);

            //dentro de la etiqueta vino
            //tengo vinoBlanco, vinoTinto y vinoRosado
            //atributos comunes (precio, marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol,
            //                    denominacionOrigen,)


            nodoDatos = documento.createElement("marca");
            nodoVino.appendChild(nodoDatos);

            texto = documento.createTextNode(unVino.getMarca());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("fecha-creación");
            nodoVino.appendChild(nodoDatos);

            //aqui parseo de double/int a string
            texto = documento.createTextNode(String.valueOf(unVino.getFechaCreacion()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("fecha-caducidad");
            nodoVino.appendChild(nodoDatos);

            texto = documento.createTextNode(String.valueOf(unVino.getFechaCaducidad()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("porcentaje-alcohol");
            nodoVino.appendChild(nodoDatos);

            texto = documento.createTextNode(String.valueOf(unVino.getPorcentajeAlcohol()));
            nodoVino.appendChild(nodoDatos);

            nodoDatos = documento.createElement("precio");
            nodoVino.appendChild(nodoDatos);

            nodoDatos = documento.createElement(String.valueOf(unVino.getPrecio()));
            nodoDatos.appendChild(nodoDatos);

            nodoDatos = documento.createElement("Denominación Origen");
            nodoVino.appendChild(nodoDatos);

            nodoDatos = documento.createElement(unVino.getDenominacionOrigen());
            nodoVino.appendChild(nodoDatos);

            //como hay un campo que depende del tipo de vehiculo
            //volvemos a comprobar
            if (unVino instanceof VinoBlanco) {
                nodoDatos = documento.createElement("Color");
                nodoVino.appendChild(nodoDatos);

                texto = documento.createTextNode(((VinoBlanco) unVino).getColor());
                nodoDatos.appendChild(texto);
            } else if (unVino instanceof VinoTinto) {
                nodoDatos = documento.createElement("Aroma");
                nodoVino.appendChild(nodoDatos);

                texto = documento.createTextNode(((VinoTinto) unVino).getAroma());
                nodoDatos.appendChild(texto);
            } else if (unVino instanceof VinoRosado) {
                nodoDatos = documento.createElement("Sabor");
                nodoVino.appendChild(nodoDatos);

                texto = documento.createTextNode(((VinoRosado) unVino).getSabor());
                nodoDatos.appendChild(texto);
            }
        }
        //guardo los datos en fichero
        Source source = new DOMSource(documento);
        Result result = new StreamResult(fichero);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);

    }

    //importarXML
    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        listaVinos = new ArrayList<Vino>();
        VinoBlanco nuevoVinoBlanco = null;
        VinoTinto nuevoVinoTinto = null;
        VinoRosado nuevoVinoRosado = null;


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        NodeList listaElementos = documento.getElementsByTagName("*");

        for (int i = 0; i < listaElementos.getLength(); i++) {
            Element nodoVino = (Element) listaElementos.item(i);

            if (nodoVino.getTagName().equals("Vino Blanco")) {
                nuevoVinoBlanco = new VinoBlanco();
                nuevoVinoBlanco.setMarca(nodoVino.getChildNodes().item(0).getTextContent());
                nuevoVinoBlanco.setFechaCreacion(LocalDate.parse(nodoVino.getChildNodes().item(1).getTextContent()));
                nuevoVinoBlanco.setFechaCaducidad(LocalDate.parse(nodoVino.getChildNodes().item(2).getTextContent()));
                nuevoVinoBlanco.setPorcentajeAlcohol(Integer.parseInt(nodoVino.getChildNodes().item(3).getTextContent()));
                nuevoVinoBlanco.setPrecio(Double.parseDouble(nodoVino.getChildNodes().item(4).getTextContent()));
                nuevoVinoBlanco.setDenominacionOrigen(nodoVino.getChildNodes().item(5).getTextContent());
                listaVinos.add(nuevoVinoBlanco);
            } else {
                if (nodoVino.getTagName().equals("Vino Tinto")) {
                    nuevoVinoTinto = new VinoTinto();
                    nuevoVinoTinto.setMarca(nodoVino.getChildNodes().item(0).getTextContent());
                    nuevoVinoTinto.setFechaCreacion(LocalDate.parse(nodoVino.getChildNodes().item(1).getTextContent()));
                    nuevoVinoTinto.setFechaCaducidad(LocalDate.parse(nodoVino.getChildNodes().item(2).getTextContent()));
                    nuevoVinoTinto.setPorcentajeAlcohol(Integer.parseInt(nodoVino.getChildNodes().item(3).getTextContent()));
                    nuevoVinoTinto.setPrecio(Double.parseDouble(nodoVino.getChildNodes().item(4).getTextContent()));
                    nuevoVinoTinto.setDenominacionOrigen(nodoVino.getChildNodes().item(5).getTextContent());
                    listaVinos.add(nuevoVinoTinto);
                } else if (nodoVino.getTagName().equals("Vino Rosado")) {
                    nuevoVinoRosado = new VinoRosado();
                    nuevoVinoRosado.setMarca(nodoVino.getChildNodes().item(0).getTextContent());
                    nuevoVinoRosado.setFechaCreacion(LocalDate.parse(nodoVino.getChildNodes().item(1).getTextContent()));
                    nuevoVinoRosado.setFechaCaducidad(LocalDate.parse(nodoVino.getChildNodes().item(2).getTextContent()));
                    nuevoVinoRosado.setPorcentajeAlcohol(Integer.parseInt(nodoVino.getChildNodes().item(3).getTextContent()));
                    nuevoVinoRosado.setPrecio(Double.parseDouble(nodoVino.getChildNodes().item(4).getTextContent()));
                    nuevoVinoRosado.setDenominacionOrigen(nodoVino.getChildNodes().item(5).getTextContent());
                    listaVinos.add(nuevoVinoRosado);
                }
            }
        }


    }
}
