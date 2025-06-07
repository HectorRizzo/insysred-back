package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.EstadoConciliacion;

import lombok.Data;

@Data
public class ArchivoMovimientoClienteDTO implements Serializable {

	private Long id;
	private ClienteDto cliente;
	private ContratoDto contrato;
	private String nombreOriginal;
	private TipoBancoDTO banco;
	private Long numeroComprobante;
	private Double valorComprobante;
	private Instant fechaComprobante;
	private Boolean aprobacion;
	private Instant fechaCreacion;
	private String imagen;
	private EstadoConciliacion estadoConciliacion;

}
