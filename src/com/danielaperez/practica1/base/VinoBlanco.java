package com.danielaperez.practica1.base;

import java.time.LocalDate;

public class VinoBlanco extends Vino {

    private String color;

    public VinoBlanco(double precio, String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                      int porcentajeAlcohol, String denominacionOrigen, String color) {
        super(precio, marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol, denominacionOrigen);
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
