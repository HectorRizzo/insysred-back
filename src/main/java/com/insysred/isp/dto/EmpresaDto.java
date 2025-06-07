package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpresaDto implements Serializable {
    private Long id;
    private String ruc;
    private String nombre;
    private String nombreComercial;
    private String direccion;
    private Boolean isActive;
    private RepresentanteLegalDto representanteLegal;
    private String telefonoFijo;
    private String telefonoMovil;
    private String correo;
    private byte[] logo;
}
