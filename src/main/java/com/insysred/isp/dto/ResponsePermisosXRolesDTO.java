package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponsePermisosXRolesDTO implements Serializable {
    private Long id;
    private Long idRol;
    private String nombreRol;
    private Long idModulo;
    private String nombreModulo;
    private Boolean activo;

    public ResponsePermisosXRolesDTO() {
    }

    public ResponsePermisosXRolesDTO(Long id, Long idRol, String nombreRol, Long idModulo, String nombreModulo, Boolean activo) {
        this.id = id;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.idModulo = idModulo;
        this.nombreModulo = nombreModulo;
        this.activo = activo;
    }
}
