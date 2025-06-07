package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermisosRolDTO implements Serializable {

    private Long idRol;
    private List<ModulosAsignarDTO> modulos;
}
