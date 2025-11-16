package com.danielaperez.practica1.base;

import java.time.LocalDate;

public class VinoTinto extends Vino {

    private String aroma;

    public VinoTinto(){

    }
    public VinoTinto(String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                     int porcentajeAlcohol, double precio, String denominacionOrigen,   String aroma) {
        super(marca, fechaCreacion, fechaCaducidad, porcentajeAlcohol, precio,  denominacionOrigen);
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
