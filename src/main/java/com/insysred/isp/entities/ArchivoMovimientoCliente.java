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
@Table(name = "archivo_movimiento_cliente")
public class ArchivoMovimientoCliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "contrato_id", nullable = false)
	private Contrato contrato;

	@Column(name = "ruta_archivo", length = 200, nullable = false)
	private String rutaArchivo;

	@Column(name = "nombre_original", length = 100, nullable = false)
	private String nombreOriginal;

	@Column(name = "nombre_interno", length = 100, nullable = false)
	private String nombreInterno;

	@Column(name = "extension", length = 5, nullable = false)
	private String extension;

	@ManyToOne
	@JoinColumn(name = "banco_id")
	private TipoBanco banco;

	@Column(name = "numero_comprobante")
	private Long numeroComprobante;

	@Column(name = "valor_comprobante")
	private Double valorComprobante;

	@Column(name = "fecha_comprobante")
	private Instant fechaComprobante;

	@Column(name = "aprobacion")
	private Boolean aprobacion;

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
