package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_error_archivo_banco")
public class DetalleErrorArchivoBanco implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "codigo_archivo", nullable = false)
	private ArchivoBanco archivo;

	@Column(name = "fecha")
	private Instant fecha;

	@Column(name = "documento")
	private Long documento;

	@Column(name = "valor")
	private Double valor;

	@Column(name = "referencia", length = 100)
	private String referencia;

	@Column(name = "mensaje_error", length = 100)
	private String mensajeError;

	@Column(name = "causa_error", length = 1000)
	private String causaError;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
