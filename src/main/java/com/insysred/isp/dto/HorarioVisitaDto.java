package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HorarioVisitaDto implements Serializable {
    private Long id;
    private String horaInicio;
    private String horaFin;
    private Boolean isActive;
}
