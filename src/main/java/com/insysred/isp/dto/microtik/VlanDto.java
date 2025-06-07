package com.insysred.isp.dto.microtik;

import lombok.Data;

import java.io.Serializable;

@Data
public class VlanDto implements Serializable {
    private String nombre;
    private String comentario;
    private String interfaz;
}
