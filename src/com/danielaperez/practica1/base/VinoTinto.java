package com.danielaperez.practica1.base;

import java.time.LocalDate;

public class VinoTinto extends Vino {

    private String aroma;

    public VinoTinto(double precio, String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                     int porcentajeAlcohol, String denominacionOrigen, String aroma) {
        super(precio, marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol, denominacionOrigen);
        this.aroma = aroma;
    }

    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    @Override
    public String toString() {
        return "VinoTinto:" +getMarca()+" "+getFechaCreacion()+" "+getFechaCaducidad();
    }
}
