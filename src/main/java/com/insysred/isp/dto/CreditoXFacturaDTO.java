package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.entities.Factura;

import lombok.Data;

@Data
public class CreditoXFacturaDTO implements Serializable {

	private Long id;
	private Factura factura;
	private Double valorAplicado;
	private Instant fechaCreacion;

}
