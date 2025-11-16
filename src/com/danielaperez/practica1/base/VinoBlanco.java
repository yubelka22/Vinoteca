package com.danielaperez.practica1.base;

import java.time.LocalDate;

public class VinoBlanco extends Vino {

    private String color;

    public VinoBlanco(){

    }

    public VinoBlanco(String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                      int porcentajeAlcohol, double precio, String denominacionOrigen, String color) {
        super(marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol, precio, denominacionOrigen);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "VinoBlanco:"  +getMarca()+" "+getFechaCreacion()+" "+getFechaCaducidad();
    }
}
