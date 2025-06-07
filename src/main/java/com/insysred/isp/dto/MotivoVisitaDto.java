package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MotivoVisitaDto implements Serializable {
    private Long id;
    private String motivo;
    private String descripcion;
    private Boolean isActive;
}
