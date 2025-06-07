package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CambioEstadoDto implements Serializable {
    private Long idComponent;
    private Boolean estatus;
}
