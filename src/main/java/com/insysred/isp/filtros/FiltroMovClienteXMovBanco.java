package com.insysred.isp.filtros;

import java.time.Instant;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import com.insysred.isp.entities.ArchivoBanco;
import com.insysred.isp.entities.ArchivoMovimientoCliente;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.DetalleArchivoBanco;
import com.insysred.isp.entities.MovClienteXMovBanco;
import com.insysred.isp.entities.TipoBanco;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class FiltroMovClienteXMovBanco {
	public static Specification<MovClienteXMovBanco> contieneFiltro(String filtroCliente, String filtroComprobante, Long filtroBanco,
			Instant fechaInicio, Instant fechaFin, String estado) {
		return (root, query, builder) -> {
			Join<MovClienteXMovBanco, Cliente> cliente = root.join("cliente", JoinType.LEFT);
			Join<MovClienteXMovBanco, ArchivoMovimientoCliente> movCliente = root.join("archivoMovimientoCliente", JoinType.LEFT);
			Join<MovClienteXMovBanco, DetalleArchivoBanco> movBanco = root.join("detalleArchivoBanco", JoinType.LEFT);
			
			Join<ArchivoMovimientoCliente, TipoBanco> movClienteTipoBanco = movCliente.join("banco", JoinType.LEFT);
			Join<DetalleArchivoBanco, ArchivoBanco> movBancoArchivo = movBanco.join("archivo", JoinType.LEFT);
			Join<ArchivoBanco, TipoBanco> movBancoTipoBanco = movBancoArchivo.join("banco", JoinType.LEFT);
			
			Predicate predicateBanco;
			Predicate predicateComprobante;
			Predicate predicateMovimiento;
			Predicate predicateCliente;
			Predicate predicateFecha;

			if (ObjectUtils.isNotEmpty(estado)) {
				predicateMovimiento = builder.or(
						builder.equal(movCliente.get("estadoConciliacion"), estado),
						builder.equal(movBanco.get("estadoConciliacion"), estado));
			} else {
				predicateMovimiento = builder.isNotNull(root.get("id"));
			}

			if (ObjectUtils.isNotEmpty(filtroCliente)) {
				predicateCliente = builder.or(
						builder.like(builder.lower(cliente.get("identificacion")), "%" + filtroCliente.toLowerCase() + "%"),
						builder.like(builder.lower(cliente.get("nombres")), "%" + filtroCliente.toLowerCase() + "%"),
						builder.like(builder.lower(cliente.get("apellidos")), "%" + filtroCliente.toLowerCase() + "%"),
						builder.like(builder.lower(cliente.get("razonSocial")), "%" + filtroCliente.toLowerCase() + "%"));
			} else {
				predicateCliente = builder.isNotNull(root.get("id"));
			}
			
			if (ObjectUtils.isNotEmpty(filtroBanco)) {
				predicateBanco = builder.or(
						builder.equal(movClienteTipoBanco.get("id"), filtroBanco),
						builder.equal(movBancoTipoBanco.get("id"), filtroBanco));
			} else {
				predicateBanco = builder.isNotNull(root.get("id"));
			}
			
			if (ObjectUtils.isNotEmpty(filtroComprobante)) {
				predicateComprobante = builder.or(
						builder.like(builder.toString(movCliente.get("numeroComprobante")), "%" + filtroComprobante.toLowerCase() + "%"),
						builder.like(builder.toString(movBanco.get("documento")), "%" + filtroComprobante.toLowerCase() + "%"));
			} else {
				predicateComprobante = builder.isNotNull(root.get("id"));
			}
			
			if (ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
				predicateFecha = builder.between(root.get("fechaCreacion"), fechaInicio, fechaFin);
			} else {
				predicateFecha = builder.isNotNull(root.get("id"));
			}

			return builder.and(predicateBanco, predicateComprobante, predicateMovimiento, predicateCliente, predicateFecha);

		};
	}

}
