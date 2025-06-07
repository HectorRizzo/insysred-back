package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AsignarDepartamentoDTO implements Serializable {
    private Long idEmpleado;
    private Long idDepartamento;
}
