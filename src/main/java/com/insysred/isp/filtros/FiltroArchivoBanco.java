package com.insysred.isp.filtros;

import java.time.Instant;

import org.springframework.data.jpa.domain.Specification;

import com.insysred.isp.entities.ArchivoBanco;

public class FiltroArchivoBanco {	
	public static Specification<ArchivoBanco> contieneTexto(String filtro) {
		return (root, query, builder) -> {
			return builder.and(builder.or(builder.like(builder.lower(root.get("nombre")), "%" + filtro.toLowerCase() + "%")));
		};		
	}
		
	public static Specification<ArchivoBanco> contieneFecha(Instant fechaInicio, Instant fechaFin) {
		return (root, query, builder) -> {
			return builder.and(builder.or(builder.between(root.get("fechaInicioCarga"), fechaInicio, fechaFin)));
		};
	}

	public static Specification<ArchivoBanco> contieneFechaMasTexto(String filtro, Instant fechaInicio, Instant fechaFin) {
		return (root, query, builder) -> {
			return builder.and(
					builder.or(builder.like(builder.lower(root.get("nombre")), "%" + filtro.toLowerCase() + "%")),
					builder.and(builder.or(builder.between(root.get("fechaInicioCarga"), fechaInicio, fechaFin))));
		};
	}
}
