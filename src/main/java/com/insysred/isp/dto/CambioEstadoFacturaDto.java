package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.EstadoFactura;
import com.insysred.isp.enums.FormaPago;
import com.insysred.isp.enums.TipoPago;

import lombok.Data;

@Data
public class CambioEstadoFacturaDto implements Serializable {
	private Long idFactura;
	private EstadoFactura estado;
	private Double saldo;
	private Double valor;
	
	private FormaPago formaPago;
	private TipoPago tipoPago;
	private Long idBanco;
	
	private String numeroComprobante;
	private Instant fechaComprobante;

	private Instant fechaPromesaPago;

}
