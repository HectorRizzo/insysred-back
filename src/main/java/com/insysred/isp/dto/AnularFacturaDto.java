package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AnularFacturaDto implements Serializable {
	private Long idFactura;
	private String justificacion;

}
