package com.insysred.isp.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class RoutersDto implements Serializable {
    private Long id;
    private String nombre;
    private String usuario;
    private String password;
    private String ip;
    private Integer puerto;
    private String gateway;
    private Boolean isActive;
    private SucursalDto sucursal;
}
