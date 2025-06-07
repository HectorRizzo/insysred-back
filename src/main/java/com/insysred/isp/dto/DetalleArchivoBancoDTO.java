package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class DetalleArchivoBancoDTO implements Serializable {

	private Instant fecha;
	private Long documento;
	private Double valor;
	private String referencia;

}
