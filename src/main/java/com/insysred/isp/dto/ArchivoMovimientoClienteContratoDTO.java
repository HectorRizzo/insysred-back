package com.insysred.isp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.insysred.isp.enums.Sexo;

import lombok.Data;

@Data
public class ArchivoMovimientoClienteContratoDTO implements Serializable {

	private String identificacion;
	private String nombre;
	private Sexo sexo;
	private Date fechaNacimiento;
	private List<ArchivoMovimientoClienteContratoDetDTO> contratos;

}
