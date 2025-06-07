package com.insysred.isp.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private String mensaje;
    private Integer codigo;
    private String descripcion;

    public ErrorDTO(String mensaje, Integer codigo, String descripcion) {
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
}
