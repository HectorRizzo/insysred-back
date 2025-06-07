package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class RubrosXCobrosDTO implements Serializable {
	private Long id;
	private ContratoDto contrato;
	private RubrosDTO rubro;
	private FacturaDTO factura;
	private Integer cantidad;
	private Instant fechaAsignacion;
}
