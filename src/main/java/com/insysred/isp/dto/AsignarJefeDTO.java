package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AsignarJefeDTO implements Serializable {
    private Long idEmpleado;
    private Long idJefe;
}
