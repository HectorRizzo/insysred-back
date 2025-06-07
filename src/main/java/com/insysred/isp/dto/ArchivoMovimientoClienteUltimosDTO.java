package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class ArchivoMovimientoClienteUltimosDTO implements Serializable {

	private Long idContrato;
	private Instant fechaCreacion;
	private String imagen;

}
