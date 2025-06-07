package com.insysred.isp.dto;

import lombok.Data;

import java.util.List;
@Data
public class AsignarSucursalUsuarioDTO {
    private Long idUsuario;
    private List<SucursalesXAsignarDTO> sucursales;
}
