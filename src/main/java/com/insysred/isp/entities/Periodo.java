package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.EstadoPeriodo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "descripcion", nullable = false)
	private String nombre;

	@Column(name = "mes", nullable = false)
	private String mes;

	@Column(name = "anio", nullable = false)
	private Integer anio;

	@Column(name = "fecha_desde")
	private Instant fechaDesde;

	@Column(name = "fecha_hasta")
	private Instant fechaHasta;

	@Column(name = "estado_periodo")
	@Enumerated(EnumType.STRING)
	private EstadoPeriodo estadoPeriodo;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;

}
