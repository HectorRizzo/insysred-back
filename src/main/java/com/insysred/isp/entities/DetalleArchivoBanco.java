package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.EstadoConciliacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_archivo_banco")
public class DetalleArchivoBanco implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "codigo_archivo", nullable = false)
	private ArchivoBanco archivo;

	@Column(name = "fecha", nullable = false)
	private Instant fecha;

	@Column(name = "documento", unique = true, nullable = false)
	private Long documento;

	@Column(name = "valor", nullable = false)
	private Double valor;

	@Column(name = "referencia", length = 100)
	private String referencia;

	@Column(name = "estado_conciliacion", length = 4)
	@Enumerated(EnumType.STRING)
	private EstadoConciliacion estadoConciliacion;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
