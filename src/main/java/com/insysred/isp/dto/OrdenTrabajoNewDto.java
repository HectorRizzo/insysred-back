package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrdenTrabajoNewDto implements Serializable {
    private Long sucursal;
    private Long cliente;
    private Long contrato;
    private Date fechaVisita;
    private Long horaVisita;
    private Long motivo;
    private List<EmpleadosDTO> tecnicos;
    private String personaContacto;
    private String telefonoContacto;
    private String direccionReferencia;
    private String referenciaDireccion;
}
