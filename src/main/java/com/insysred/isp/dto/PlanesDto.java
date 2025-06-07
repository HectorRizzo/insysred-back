package com.insysred.isp.dto;

import lombok.Data;

@Data
public class PlanesDto {
    private Long id;
    private String name;
    private String descripcion;
    private Integer megabytes;
    private Double price;
    private Boolean status;
    private SucursalDto sucursales;
}
