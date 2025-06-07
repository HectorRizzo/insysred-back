package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AsociarRubroXContratoDto implements Serializable {

	private Long idRubro;
	private Long idContrato;
	private Integer cantidad;

}
