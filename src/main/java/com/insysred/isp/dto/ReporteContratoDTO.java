package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReporteContratoDTO implements Serializable {

	private String nombreEmpresaCliente;
	private String identificacionCliente;
	private String nombreCliente;
	private String direccionCliente;
	private String referenciaCliente;
	private String telefonosCliente;
	private String correoCliente;
	private String cantonCliente;
	private String provinciaCliente;
	
	private String contratoPrestacion;
	private String clausulaPrimera;
	private String clausulaSegunda;
	private String clausulaTercera;
	private String clausulaCuarta;
	private String clausulaQuinta;
	private String clausulaSexta;
	private String clausulaSeptima;
	private String clausulaOctava;
	private String clausulaNovena;
	private String clausulaDecima;
	private String clausulaUndecima;
	private String clausulaDuodecima;

}
