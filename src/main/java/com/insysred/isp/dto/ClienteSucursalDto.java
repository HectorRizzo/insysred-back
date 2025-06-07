package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClienteSucursalDto implements Serializable {
    private Long idCliente;
    private Long idSucursal;
}
