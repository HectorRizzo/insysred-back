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
@Table(name = "detalle_factura")
public class DetalleFactura implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "factura_id")
	private Factura factura;

	@ManyToOne
	@JoinColumn(name = "rubro_id")
	private Rubros rubro;

	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Planes plan;

	@Column(name = "cantidad")
	private Integer cantidad;

	@Column(name = "valor", columnDefinition = "Decimal(10,2)")
	private Double valor;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
