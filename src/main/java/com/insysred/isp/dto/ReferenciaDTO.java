package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReferenciaDTO implements Serializable {
    private Long id;
    private String parentesco;
    private String apellidos;
    private String nombres;
    private String telfFijo;
    private String telfMovil;
    private ClienteDto cliente;
    private ProvinciaDto provincia;
    private CantonDto canton;
    private String direccion;
}
