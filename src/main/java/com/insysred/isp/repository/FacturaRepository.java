package com.insysred.isp.repository;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Factura;
import com.insysred.isp.enums.EstadoFactura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>, JpaSpecificationExecutor<Factura> {

	@Query(value = "select fa from Factura fa where (fa.cliente.identificacion like ?1 or fa.cliente.nombres like ?1 or fa.cliente.apellidos like ?1) and fa.fechaEmision BETWEEN ?2 and ?3")
	Page<Factura> getByFilter(String filtro, Instant fechaInicio, Instant fechaFin, PageRequest paginaRequest);
	
	@Query(value = "select fa from Factura fa where fa.contrato.numContrato = ?1 and fa.periodo.id = ?2")
	Factura getByIdContratoPeriodo(Long idContrato, Long idPeriodo);
	
	@Query(value = "select fa from Factura fa where fa.contrato.numContrato = ?1 and fa.estado = ?2 ORDER BY fa.fechaCreacion ASC LIMIT 1")
	Factura getByFacturaRecienteIdContrato(Long idContrato, EstadoFactura estado);

}
