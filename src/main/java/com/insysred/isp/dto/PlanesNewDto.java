package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlanesNewDto implements Serializable {
    private String name;
    private String descripcion;
    private Integer megabytes;
    private Double price;
    private Boolean status;
    private Long sucursales;
}
