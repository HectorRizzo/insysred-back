package com.insysred.isp.dto;

import com.insysred.isp.entities.EstadoContrato;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ContratoDto implements Serializable {
    private Long numContrato;
    private ClienteDto cliente;
    private SucursalDto sucursal;
    private RoutersDto servidor;
    private CantonDto canton;
    private PlanesDto plan;
    private String longitud;
    private String latitud;
    private String ubicacion;
    private String referencia;
    private String ip;
    private String mac;
    private Instant fechaCrea;
    private Instant fechaModifica;
    private Instant fechaContrato;
    private Instant fechaInstala;
    private Instant fechaFin;
    private Boolean isActive;
    private EstadoContrato estadoContrato;
    private Boolean envioMicrotick;
}
