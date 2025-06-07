package com.insysred.isp.entities;

import java.io.Serializable;
import java.time.Instant;

import com.insysred.isp.enums.TipoCuentaBanco;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_banco")
public class TipoBanco implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", length = 50)
	private String nombre;

	@Column(name = "titular", length = 100)
	private String titular;

	@Column(name = "numero_cuenta", length = 50)
	private String numeroCuenta;

	@Column(name = "tipo_cuenta", length = 13)
	@Enumerated(EnumType.STRING)
	private TipoCuentaBanco tipoCuenta;

	@Column(name = "fecha_creacion")
	private Instant fechaCreacion;

	@Column(name = "feha_modificacion")
	private Instant fechaModificacion;

	@Column(name = "is_active")
	private Boolean isActive;
}
