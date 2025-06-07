package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.RubrosXContrato;
import com.insysred.isp.enums.EstadoRubroXContrato;

@Repository
public interface RubrosXContratoRepository
		extends JpaRepository<RubrosXContrato, Long>, JpaSpecificationExecutor<RubrosXContrato> {

	@Query(value = "select rc from RubrosXContrato rc where rc.contrato.numContrato = ?1 and rc.isActive = true")
	Page<RubrosXContrato> getByIdContratoIsActive(Long idContrato, PageRequest paginaRequest);

	@Query(value = "select rc from RubrosXContrato rc where rc.factura.id = ?1")
	List<RubrosXContrato> getByFacturaId(Long idFactura);

	@Query(value = "select rc from RubrosXContrato rc where rc.contrato.id = ?1 and rc.estado = ?2 and rc.isActive = true")
	List<RubrosXContrato> getByIdContratoEstado(Long idContrato, EstadoRubroXContrato estado);
}