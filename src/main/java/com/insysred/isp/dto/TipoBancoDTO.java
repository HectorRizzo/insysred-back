package com.insysred.isp.dto;

import java.io.Serializable;

import com.insysred.isp.enums.TipoCuentaBanco;

import lombok.Data;

@Data
public class TipoBancoDTO implements Serializable {

	private Long id;
	private String nombre;
	private String titular;
	private String numeroCuenta;
	private TipoCuentaBanco tipoCuenta;

}
