package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolDto implements Serializable {
    private Long id;
    private String nombre;
    private Boolean isActive;
    private String descripcion;
}
