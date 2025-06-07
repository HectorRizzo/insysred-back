package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AsignaSucursalDto implements Serializable {
    private Long idRouter;
    private Long idSucursal;
}
