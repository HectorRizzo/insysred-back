package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UsuarioDto implements Serializable {
    private Long id;
    private String password;
    private Boolean activo;
    private String username;
    private String fechaCreacion;
    private String fechaModificacion;
    private Boolean esPrimerInicio;
    private RolDto rol;
    private List<SucursalDto> sucursales;
    private EmpleadosDTO empleado;
}
