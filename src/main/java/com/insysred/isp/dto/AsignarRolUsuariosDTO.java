package com.insysred.isp.dto;

import lombok.Data;

import java.util.List;

@Data
public class AsignarRolUsuariosDTO {
    private Long idUsuario;
    private Long idSucursal;
    private List<RolesXAsignarDTO> roles;
}
