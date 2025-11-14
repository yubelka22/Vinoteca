package com.danielaperez.practica1;

import com.danielaperez.practica1.vista.FormularioPrincipal;
import com.danielaperez.practica1.vista.VinotecaControlador;
import com.danielaperez.practica1.vista.VinotecaModelo;

public class Principal {
    public static void main(String[] args) {

        FormularioPrincipal vista = new FormularioPrincipal();
        VinotecaModelo vinotecaModelo = new VinotecaModelo();
        VinotecaControlador vinotecaControlador = new VinotecaControlador(vista, vinotecaModelo);
    }
}
