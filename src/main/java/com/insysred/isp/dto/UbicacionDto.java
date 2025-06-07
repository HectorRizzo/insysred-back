package com.insysred.isp.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UbicacionDto implements Serializable {
    private Long id;
    private String direccion;
    private String georeferencia;
}
