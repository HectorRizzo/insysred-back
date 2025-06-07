package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class DescuentoFacturaDto implements Serializable {

	private Long id;
	private Double valor;
	private String justificacion;
	private Instant fechaCreacion;

}
