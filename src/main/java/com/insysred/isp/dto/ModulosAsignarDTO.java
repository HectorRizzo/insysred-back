package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModulosAsignarDTO implements Serializable {

    private Long idModulo;
    private Boolean checked;
}
