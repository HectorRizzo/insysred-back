package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReporteReciboDetalleDTO implements Serializable {

	private String descripcion;
	private Double precio;

}
