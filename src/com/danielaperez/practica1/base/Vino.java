package com.danielaperez.practica1.base;

import java.time.LocalDate;

public class Vino {

    private String marca;
    private LocalDate fechaCreacion;
    private LocalDate fechaCaducidad;
    private int porcentajeAlcohol;
    private double precio;
    private String sabor;
    private String denominacionOrigen;

    public Vino(double precio, String marca, LocalDate fechaCreacion, LocalDate fechaCaducidad,
                int porcentajeAlcohol, String denominacionOrigen) {
        this.precio = precio;
        this.marca = marca;
        this.fechaCreacion = fechaCreacion;
        this.fechaCaducidad = fechaCaducidad;
        this.porcentajeAlcohol = porcentajeAlcohol;
        this.denominacionOrigen = denominacionOrigen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getPorcentajeAlcohol() {
        return porcentajeAlcohol;
    }

    public void setPorcentajeAlcohol(int porcentajeAlcohol) {
        this.porcentajeAlcohol = porcentajeAlcohol;
    }

    public String getDenominacionOrigen() {
        return denominacionOrigen;
    }
    public void setDenominacionOrigen(String denominacionOrigen) {
        this.denominacionOrigen = denominacionOrigen;
    }

    @Override
    public String toString() {
        return "Vino{" +
                "precio=" + precio +
                ", marca='" + marca + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaCaducidad=" + fechaCaducidad +
                ", porcentajeAlcohol=" + porcentajeAlcohol +
                ", denominacionOrigen=" + denominacionOrigen +
                '}';
    }
}
