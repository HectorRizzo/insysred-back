package com.insysred.isp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExistenciaMovClienteXMovBancoDTO implements Serializable {
	private Long idMovClienteXMovBanco;
	private Long idMovCliente;
	private Long idMovBanco;

}
