package com.insysred.isp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insysred.isp.entities.Empleados;
import com.insysred.isp.enums.EstadoOrden;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
public class OrdenTrabajoDto implements Serializable {
    private Long id;

    private Long codigo;

    private Long numeroOrden;

    private SucursalDto sucursal;

    private ContratoDto contrato;

    private ClienteDto codigoCliente;

    private Instant fechaVisita;

    private HorarioVisitaDto horaVisita;

    private String personaContacto;

    private String celularContacto;

    private EstadoOrden estadoOrden;

    private MotivoVisitaDto motivo;

    private Boolean esActivo;

    private Instant fechaCrea;

    private String direccion;

    private List<Empleados> lsTecnicos;

    @JsonIgnore
    private String tecnicos;

    private String referenciaDireccion;

}
