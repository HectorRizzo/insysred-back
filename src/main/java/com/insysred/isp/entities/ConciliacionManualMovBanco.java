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
@Table(name = "conciliacion_manual_mov_banco")
public class ConciliacionManualMovBanco implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_det_archivo_banco")
	private DetalleArchivoBanco detalleArchivoBanco;

	@Column(name = "estado_conciliacion_ant", length = 4)
	@Enumerated(EnumType.STRING)
	private EstadoConciliacion estadoConciliacionAnt;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;

}
