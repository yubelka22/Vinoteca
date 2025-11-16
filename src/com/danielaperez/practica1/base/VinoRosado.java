package com.danielaperez.practica1.base;

import java.time.LocalDate;

public class VinoRosado extends Vino {

    private String sabor;


    public VinoRosado() {

    }

    public VinoRosado(String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                      int porcentajeAlcohol, double precio, String denominacionOrigen,  String sabor) {
        super(marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol, precio, denominacionOrigen);
        this.sabor = sabor;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    @Override
    public String toString() {
        return "VinoRosado:" + getMarca() + " " + getFechaCreacion() + " " + getFechaCaducidad();

    }
}
