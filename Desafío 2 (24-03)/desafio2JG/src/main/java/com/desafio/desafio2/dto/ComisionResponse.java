package com.desafio.desafio2.dto;

public class ComisionResponse {
    private double comision;
    private String mensaje;

    public ComisionResponse(double comision, String s) {
    }

    // Getters y setters
    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ComisionResponse{" +
                "comision=" + comision +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}