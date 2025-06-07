package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;
import com.insysred.isp.enums.FormaPago;
import com.insysred.isp.enums.TipoPago;

import lombok.Data;

@Data
public class ReciboDTO implements Serializable {
	private Long id;
	private Instant fechaPago;
	private FormaPago formaPago;
	private TipoPago tipoPago;
	private TipoBancoDTO banco;
	private String numeroComprobante;
	private Instant fechaComprobante;
	private Double valor;
}
