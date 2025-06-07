package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RouterNewDto implements Serializable {
    private String nombre;
    private String usuario;
    private String password;
    private String ip;
    private Long puerto;
    private String gateway;
    private Boolean isActive;
    private Long sucursal;
}
