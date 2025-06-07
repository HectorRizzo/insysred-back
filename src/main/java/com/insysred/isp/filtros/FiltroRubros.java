package com.insysred.isp.filtros;

import org.springframework.data.jpa.domain.Specification;

import com.insysred.isp.entities.Rubros;

public class FiltroRubros {
	public static Specification<Rubros> contieneTexto(String filtro) {
		return (root, query, builder) -> builder
				.and(builder.or(builder.like(builder.lower(root.get("nombre")), "%" + filtro.toLowerCase() + "%")));
	}
}
