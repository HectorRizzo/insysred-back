package com.insysred.isp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SucursalClienteDto implements Serializable {
  private Long id;
  private String nombre;
  private String establecimiento;
  private String puntoEmision;
  private String secuencial;
  private Long cliente;
  private Boolean cliente_presente;
}
