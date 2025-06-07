package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AplicarDescuentoFacturaDto implements Serializable {
	private Long idFactura;
	private String justificacion;
	private Double valor;

}
