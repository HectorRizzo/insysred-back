package com.insysred.isp.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.insysred.isp.enums.EstadoCarga;

import lombok.Data;

@Data
public class ArchivoBancoDTO implements Serializable {

	private Long id;
	private TipoBancoDTO banco;
	private String nombre;
	private Instant fechaInicioCarga;
	private Instant fechaFinCarga;
	private Integer registrosExito;
	private Integer registrosError;
	private Integer registrosRepetido;
	private Integer registrosTotal;
	private EstadoCarga estadoCarga;
	private List<DetalleArchivoBancoDTO> detalleArchivoConciliacion;
	private List<DetalleErrorArchivoBancoDTO> detalleErrorArchivoConciliacion;

}
