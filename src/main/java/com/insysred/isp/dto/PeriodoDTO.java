package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.EstadoPeriodo;

import lombok.Data;

@Data
public class PeriodoDTO implements Serializable {

	private Long id;
	private String nombre;
	private String mes;
	private Integer anio;
	private Instant fechaDesde;
	private Instant fechaHasta;
	private EstadoPeriodo estadoPeriodo;

}
