package com.insysred.isp.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class AsignarSucursalClienteDto implements Serializable {
  private Long idCliente;
  private List<Long> idSucursal;
}
