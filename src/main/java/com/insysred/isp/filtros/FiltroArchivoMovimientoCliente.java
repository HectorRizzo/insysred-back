package com.insysred.isp.filtros;

import java.time.Instant;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import com.insysred.isp.entities.ArchivoMovimientoCliente;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Factura;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class FiltroArchivoMovimientoCliente {
	public static Specification<ArchivoMovimientoCliente> contieneTexto(String filtro) {
		return (root, query, builder) -> {
			Join<ArchivoMovimientoCliente, Cliente> cliente = root.join("cliente");

			return builder.and(builder.or(
					builder.like(builder.lower(cliente.get("identificacion")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("nombres")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("apellidos")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("razonSocial")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("email")), "%" + filtro.toLowerCase() + "%")),
					builder.equal(root.get("isActive"), true));
		};
	}

	public static Specification<ArchivoMovimientoCliente> contieneFecha(Instant fechaInicio, Instant fechaFin) {
		return (root, query, builder) -> {
			return builder.and(builder.or(builder.between(root.get("fechaCreacion"), fechaInicio, fechaFin)), builder.equal(root.get("isActive"), true));
		};
	}

	public static Specification<ArchivoMovimientoCliente> contieneFechaMasTexto(String filtro, Instant fechaInicio, Instant fechaFin) {
		return (root, query, builder) -> {
			Join<ArchivoMovimientoCliente, Cliente> cliente = root.join("cliente");
			return builder.and(
					builder.or(
							builder.like(builder.lower(cliente.get("identificacion")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("nombres")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("apellidos")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("razonSocial")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("email")), "%" + filtro.toLowerCase() + "%")),
					builder.and(builder.or(builder.between(root.get("fechaCreacion"), fechaInicio, fechaFin))),
					builder.equal(root.get("isActive"), true));
		};
	}
}
