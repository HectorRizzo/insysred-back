package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class CreditoFacturaDTO implements Serializable {

	private Long id;
	private CreditoDTO credito;
	private Double valorAplicado;
	private Instant fechaCreacion;

}
