package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class MovClienteXMovBancoDTO implements Serializable {

	private Long id;
	private ArchivoMovimientoClienteDTO archivoMovimientoCliente;
	private DetalleArchivoBancoDTO detalleArchivoBanco;
	private Long numeroComprobante;
	private Double valorComprobante;
	private Instant fechaComprobante;
	private String referenciaComprobante;
	private ClienteDto cliente;
	private ContratoDto contrato;
	private Boolean isActive;

}
