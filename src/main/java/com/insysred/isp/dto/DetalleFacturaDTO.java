package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DetalleFacturaDTO implements Serializable {
	private Long id;
	private PlanesDto plan;
	private RubrosDTO rubro;
	private Integer cantidad;
	private Double valor;
}
