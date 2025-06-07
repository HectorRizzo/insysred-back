package com.insysred.isp.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReporteReciboDTO implements Serializable {

	private String idRecibo;
	private String fechaRecibo;
	private String nombreCliente;
	private String identificacion;
	private String sexo;
	private String direccion;
	private String telefonos;
	private String formaPago;
	private String codigoActivacion;
	private Double totalRecibo;
	private String usuarioEmision;
	private String fechaEmision;
	private List<ReporteReciboDetalleDTO> detalles;

}
