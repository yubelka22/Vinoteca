package com.danielaperez.practica1.base;

import java.time.LocalDate;

public class VinoRosado extends Vino{

    private String sabor;

    public VinoRosado(double precio, String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                      int porcentajeAlcohol, String denominacionOrigen, String sabor) {
        super(precio, marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol, denominacionOrigen);
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
        return "VinoRosado:"  +getMarca()+" "+getFechaCreacion()+" "+getFechaCaducidad();

    }
}
