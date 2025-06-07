package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.insysred.isp.enums.EstadoFactura;

import lombok.Data;

@Data
public class FacturaDTO implements Serializable {
	private Long id;
	private ClienteDto cliente;
	private ContratoDto contrato;
	private Double valor;
	private Double iva;
	private Double total;
	private EstadoFactura estado;
	private Double saldo;
	private PeriodoDTO periodo;
	private Instant fechaEmision;
	private Instant fechaPreCorte;
	private Instant fechaVencimiento;
	private List<DetalleFacturaDTO> detalleFacturas;
	private List<FacturaPromesaPagoDTO> promesasPago;
	private List<ReciboDTO> recibos;
	private List<DescuentoFacturaDto> descuentosFactura;
	private List<CreditoFacturaDTO> creditoFactura;
}
