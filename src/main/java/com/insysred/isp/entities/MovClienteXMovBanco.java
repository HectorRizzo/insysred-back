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
@Table(name = "mov_cliente_x_mov_banco")
public class MovClienteXMovBanco implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_mov_cliente")
	private ArchivoMovimientoCliente archivoMovimientoCliente;

	@ManyToOne
	@JoinColumn(name = "id_det_archivo_banco")
	private DetalleArchivoBanco detalleArchivoBanco;

	@Column(name = "numero_comprobante")
	private Long numeroComprobante;

	@Column(name = "valor_comprobante")
	private Double valorComprobante;

	@Column(name = "fecha_comprobante")
	private Instant fechaComprobante;

	@Column(name = "referencia_comprobante", length = 100)
	private String referenciaComprobante;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "contrato_id")
	private Contrato contrato;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
