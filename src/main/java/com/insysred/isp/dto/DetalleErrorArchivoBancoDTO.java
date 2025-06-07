package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class DetalleErrorArchivoBancoDTO implements Serializable {
	
	private Instant fecha;
	private String documento;
	private Double valor;
	private String referencia;
	private String mensajeError;
	private String causaError;

}
