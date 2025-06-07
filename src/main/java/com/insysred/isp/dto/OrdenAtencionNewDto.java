package com.insysred.isp.dto;
import com.insysred.isp.enums.EstadoOrden;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class OrdenAtencionNewDto implements Serializable {

    private Long ordenTrabajo;

    private Long tecnico;

    private Instant fechaAtencion;

    private EstadoOrden estadoOrden;

    private String detalle;

}
