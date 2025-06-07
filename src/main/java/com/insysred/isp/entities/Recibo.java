package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.FormaPago;
import com.insysred.isp.enums.TipoPago;

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
@Table(name = "recibo")
public class Recibo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "factura_id")
	private Factura factura;

	@Column(name = "fecha_pago")
	private Instant fechaPago;

	@Column(name = "forma_pago", length = 15)
    @Enumerated(EnumType.STRING)
	private FormaPago formaPago;

	@Column(name = "tipo_pago", length = 10)
    @Enumerated(EnumType.STRING)
	private TipoPago tipoPago;

	@ManyToOne
	@JoinColumn(name = "banco_id", nullable = true)
	private TipoBanco banco;

	@Column(name = "numero_comprobante", length = 50, nullable = true)
	private String numeroComprobante;

	@Column(name = "fecha_comprobante")
	private Instant fechaComprobante;

	@Column(name = "valor", columnDefinition = "Decimal(10,2)")
	private Double valor;

	@Column(name = "saldo", columnDefinition = "Decimal(10,2)")
	private Double saldo;

	@Column(name = "es_conciliacion")
	private Boolean esConciliacion;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
