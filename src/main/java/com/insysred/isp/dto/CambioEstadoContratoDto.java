package com.insysred.isp.dto;

import com.insysred.isp.entities.EstadoContrato;
import lombok.Data;

import java.io.Serializable;

@Data
public class CambioEstadoContratoDto implements Serializable {
    private Long idContrato;
    private EstadoContrato estado;
}
