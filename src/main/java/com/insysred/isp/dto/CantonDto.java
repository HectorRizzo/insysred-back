package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CantonDto implements Serializable {
    private Long id;
    private String nombre;
    private ProvinciaDto provincia;
}
