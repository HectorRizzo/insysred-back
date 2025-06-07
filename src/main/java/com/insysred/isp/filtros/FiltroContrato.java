package com.insysred.isp.filtros;

import org.springframework.data.jpa.domain.Specification;

import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Contrato;

import jakarta.persistence.criteria.Join;

public class FiltroContrato {
	public static Specification<Contrato> contieneTexto(String filtro) {
		return (root, query, builder) -> {
			Join<Contrato, Cliente> cliente = root.join("cliente");

			return builder.and(builder.or(
					builder.like(builder.lower(cliente.get("identificacion")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("nombres")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("apellidos")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("razonSocial")), "%" + filtro.toLowerCase() + "%"),
					builder.like(builder.lower(cliente.get("email")), "%" + filtro.toLowerCase() + "%")));
		};
	}
}
