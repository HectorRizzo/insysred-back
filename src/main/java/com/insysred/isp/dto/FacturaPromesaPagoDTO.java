package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class FacturaPromesaPagoDTO implements Serializable {
	private Long id;
	private Instant fechaPromesaPago;
}
