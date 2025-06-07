package com.insysred.isp.dto;

import java.io.Serializable;
import java.util.Date;


import com.insysred.isp.entities.EstadoContrato;
import lombok.Data;

@Data
public class ArchivoMovimientoClienteContratoDetDTO implements Serializable {

	private Long idContrato;
	private String direccion;
	private Date fechaContrato;
	private EstadoContrato estadoContrato;

}
