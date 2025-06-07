package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class UsuarioSucursalDTO implements Serializable{
    private Long usuarioId;
    private Long SucursalId;
}
