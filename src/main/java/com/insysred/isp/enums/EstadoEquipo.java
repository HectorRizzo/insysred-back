package com.insysred.isp.enums;

public enum EstadoEquipo {
    EN_USO("EN USO"),
    LIBRE("LIBRE"),
    DANADO("DAÑADO");


    private final String estado;

    EstadoEquipo(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }
}