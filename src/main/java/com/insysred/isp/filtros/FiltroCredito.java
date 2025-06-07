package com.insysred.isp.filtros;

import java.time.Instant;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Credito;
import com.insysred.isp.entities.Factura;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class FiltroCredito {
	public static Specification<Credito> contieneTexto(String filtro, String estado) {
		return (root, query, builder) -> {
			Join<Credito, Cliente> cliente = root.join("cliente");
			Predicate predicate;

			if (!estado.equalsIgnoreCase("") || ObjectUtils.isNotEmpty(estado)) {
				predicate = builder.equal(root.get("estadoCredito"), estado);
			} else {
				predicate = builder.isNotNull(root.get("id"));
			}

			return builder.and(
					builder.or(
							builder.like(builder.lower(cliente.get("identificacion")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("nombres")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("apellidos")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("razonSocial")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("email")), "%" + filtro.toLowerCase() + "%")),
					builder.and(predicate));
		};
	}

	public static Specification<Credito> contieneFecha(Instant fechaInicio, Instant fechaFin) {
		return (root, query, builder) -> {
			return builder.and(builder.or(builder.between(root.get("fechaCreacion"), fechaInicio, fechaFin)));
		};
	}

	public static Specification<Credito> contieneFechaMasTexto(String filtro, Instant fechaInicio, Instant fechaFin) {
		return (root, query, builder) -> {
			Join<Credito, Cliente> cliente = root.join("cliente");
			return builder.and(
					builder.or(
							builder.like(builder.lower(cliente.get("identificacion")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("nombres")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("apellidos")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("razonSocial")), "%" + filtro.toLowerCase() + "%"),
							builder.like(builder.lower(cliente.get("email")), "%" + filtro.toLowerCase() + "%")),
					builder.and(builder.or(builder.between(root.get("estadoCredito"), fechaInicio, fechaFin))));
		};
	}
}
