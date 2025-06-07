package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insysred.isp.enums.EstadoFactura;

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
@Table(name = "factura")
public class Factura implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "contrato_id")
	private Contrato contrato;

	@Column(name = "valor", columnDefinition = "Decimal(10,2)")
	private Double valor;

	@Column(name = "iva", columnDefinition = "Decimal(10,2)")
	private Double iva;

	@Column(name = "total", columnDefinition = "Decimal(10,2)")
	private Double total;

	@Column(name = "estado", length = 10)
	@Enumerated(EnumType.STRING)
	private EstadoFactura estado;

	@Column(name = "saldo", columnDefinition = "Decimal(10,2)")
	private Double saldo;

	@ManyToOne
	@JoinColumn(name = "periodo_id")
	private Periodo periodo;

	@Column(name = "fecha_emision")
	private Instant fechaEmision;

	@Column(name = "fecha_pre_corte")
	private Instant fechaPreCorte;

	@Column(name = "fecha_vencimiento")
	private Instant fechaVencimiento;

	@Column(name = "numero_aprobacion_sri", length = 20, nullable = true)
	private String numeroAprobacionSri;

	@Column(name = "estado_aprobacion_sri", length = 10, nullable = true)
	private String estadoAprobacionSri;

	@Column(name = "justificacion_anulacion", length = 250, nullable = true)
	private String justificacionAnulacion;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DetalleFactura> detalleFacturas;

	@OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Recibo> recibos;

	@OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FacturaPromesaPago> promesasPago;

	@OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DescuentoFactura> descuentosFactura;

	@OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CreditoXFactura> creditoFactura;
	
}
