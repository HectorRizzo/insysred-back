package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.enums.EstadoCredito;

import lombok.Data;

@Data
public class CreditoDTO implements Serializable {

	private Long id;
	private Contrato contrato;
	private Cliente cliente;
	private Instant fechaCredito;
	private Double valorCredito;
	private Double saldo;
	private EstadoCredito estadoCredito;
	private Boolean esConciliacion;
	private List<CreditoXFacturaDTO> creditoXFactura;

}
