package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "parametros_generales")
@Data
public class ParametrosGenerales implements Serializable {

	@Id
	@Column(name = "nemonico", length = 100, nullable = false)
	private String nemonico;

	@Column(name = "descripcion", length = 500)
	private String descripcion;

	@Column(name = "valor_varchar", length = 1000)
	private String valorVarchar;

	@Column(name = "valor_number")
	private Long valorNumber;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;

}
