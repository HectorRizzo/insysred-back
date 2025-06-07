package com.insysred.isp.dto;
import lombok.Data;
import java.io.Serializable;

@Data
public class SucursalDto implements Serializable {
    private Long id;
    private String nombre;
    private String direccion;
    private String establecimiento;
    private String puntoEmision;
    private String secuencial;
    private Boolean isActive;
    private EmpresaDto empresa;
}
