package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProcesarPeriodoDto implements Serializable {

	private Long idPeriodo;
	private Long[] idContratos;

}
