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
@Table(name = "descuento_factura")
public class DescuentoFactura implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "factura_id", nullable = false)
	private Factura factura;

	@Column(name = "valor", columnDefinition = "Decimal(10,2)")
	private Double valor;

	@Column(name = "justificacion", nullable = false)
	private String justificacion;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
