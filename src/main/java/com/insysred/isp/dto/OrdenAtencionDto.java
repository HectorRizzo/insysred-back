package com.insysred.isp.dto;
import com.insysred.isp.enums.EstadoOrden;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class OrdenAtencionDto implements Serializable {

    private Long id;

    private OrdenTrabajoDto ordenTrabajo;

    private ClienteDto tecnico;

    private Instant fechaAtencion;

    private EstadoOrden estadoOrden;

    private String detalle;

}
