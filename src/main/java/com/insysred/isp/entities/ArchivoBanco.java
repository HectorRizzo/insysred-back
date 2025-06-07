package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insysred.isp.enums.EstadoCarga;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "archivo_banco")
public class ArchivoBanco implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "codigo_banco", nullable = false)
	private TipoBanco banco;

	@Column(name = "nombre_archivo", length = 100)
	private String nombre;

	@Column(name = "fecha_inicio_carga")
	private Instant fechaInicioCarga;

	@Column(name = "fecha_fin_carga")
	private Instant fechaFinCarga;

	@Column(name = "registros_exito")
	private Integer registrosExito;

	@Column(name = "registros_error")
	private Integer registrosError;

	@Column(name = "registros_repetido")
	private Integer registrosRepetido;

	@Column(name = "registros_total")
	private Integer registrosTotal;

	@Column(name = "estado_carga")
	@Enumerated(EnumType.STRING)
	private EstadoCarga estadoCarga;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToMany(mappedBy = "archivo", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DetalleArchivoBanco> detalleArchivoConciliacion;

	@OneToMany(mappedBy = "archivo", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DetalleErrorArchivoBanco> detalleErrorArchivoConciliacion;
}
