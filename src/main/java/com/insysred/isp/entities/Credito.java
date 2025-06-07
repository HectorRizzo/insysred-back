package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insysred.isp.enums.EstadoCredito;

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
@Table(name = "credito")
public class Credito implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contrato_id", nullable = false)
	private Contrato contrato;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@Column(name = "fecha_credito", nullable = false)
	private Instant fechaCredito;

	@Column(name = "valor_credito", columnDefinition = "Decimal(10,2)")
	private Double valorCredito;

	@Column(name = "saldo", columnDefinition = "Decimal(10,2)")
	private Double saldo;

	@Column(name = "estado_credito", length = 15)
	@Enumerated(EnumType.STRING)
	private EstadoCredito estadoCredito;

	@Column(name = "es_conciliacion")
	private Boolean esConciliacion;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToMany(mappedBy = "credito", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CreditoXFactura> creditoXFactura;
}
