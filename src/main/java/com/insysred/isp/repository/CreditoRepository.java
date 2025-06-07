package com.insysred.isp.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Credito;
import com.insysred.isp.enums.EstadoCredito;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long>, JpaSpecificationExecutor<Credito> {
	
	@Query(value = "select p from Credito p where p.contrato.numContrato = ?1 and p.cliente.id = ?2 and p.estadoCredito = ?3 and p.saldo > 0 and p.isActive = true ORDER BY p.fechaCreacion ASC LIMIT 1")
	Credito getByContratoClienteEstado(Long idContrato, Long idCliente, EstadoCredito estado);
}
