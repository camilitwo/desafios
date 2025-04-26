package com.desafios.sistordenes.dto;

import java.util.List;

public class EmailRequest {
    private List<String> emails;
    private List<OrdenResponse> ordenes;

    // Getters y Setters
    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<OrdenResponse> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<OrdenResponse> ordenes) {
        this.ordenes = ordenes;
    }
}
