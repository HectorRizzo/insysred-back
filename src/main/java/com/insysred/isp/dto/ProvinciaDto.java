package com.insysred.isp.dto;

import com.insysred.isp.enums.Pais;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProvinciaDto implements Serializable {
    private Long id;
    private Pais pais;
    private String nombre;
}
