package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProcesarPeriodoResultadoDto implements Serializable {

	private Double totalValorFacturas;
	private Integer conteoFacturas;

}
