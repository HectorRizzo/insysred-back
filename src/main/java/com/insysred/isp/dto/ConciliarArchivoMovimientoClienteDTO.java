package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class ConciliarArchivoMovimientoClienteDTO implements Serializable {

	private Long idArchivo;
	private Long idBanco;
	private Long numeroComprobante;
	private Double valorComprobante;
	private Instant fechaComprobante;
	private Boolean aprobacion;

	private Long idConMovClienteXMovBanco;
	private Long idConMovCliente;
	private Long idConMovBanco;

	private Boolean anular;

}
