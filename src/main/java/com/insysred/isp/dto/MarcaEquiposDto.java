package com.insysred.isp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class MarcaEquiposDto {
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @Getter
    private String nombreMarca;
    @Getter
    private String nombreModelo;
    @Getter
    private Boolean activo;
    private Date fechaCreacion;
    private Date fechaModificacion;

    // Add this constructor
    public MarcaEquiposDto(Long id, String nombreMarca, String nombreModelo) {
        this.id = id;
        this.nombreMarca = nombreMarca;
        this.nombreModelo = nombreModelo;
    }

}
