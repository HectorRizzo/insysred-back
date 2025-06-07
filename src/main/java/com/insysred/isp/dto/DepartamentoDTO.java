package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DepartamentoDTO implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
