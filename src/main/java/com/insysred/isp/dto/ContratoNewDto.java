package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ContratoNewDto implements Serializable {
    private Long cliente;
    private Long sucursal;
    private Long servidor;
    private Long plan;
    private String longitud;
    private String latitud;
    private String referencia;
    private String ubicacion;
    private String ip;
    private String mac;
    private Instant fechaCrea;
    private Instant fechaModifica;
    private Instant fechaContrato;
    private Instant fechaInstala;
    private Instant fechaFin;
    private Boolean isActive;
}
