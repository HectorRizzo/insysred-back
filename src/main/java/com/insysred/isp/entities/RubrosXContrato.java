package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.EstadoRubroXContrato;

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
@Table(name = "rubros_x_contrato")
public class RubrosXContrato implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contrato_id")
	private Contrato contrato;

	@ManyToOne
	@JoinColumn(name = "rubro_id")
	private Rubros rubro;

	@ManyToOne
	@JoinColumn(name = "factura_id")
	private Factura factura;

	@Column(name = "cantidad")
	private Integer cantidad;

	@Column(name = "fecha_asignacion")
	private Instant fechaAsignacion;
	
    @Column(name = "estado", length = 15)
    @Enumerated(EnumType.STRING)
    private EstadoRubroXContrato estado;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
