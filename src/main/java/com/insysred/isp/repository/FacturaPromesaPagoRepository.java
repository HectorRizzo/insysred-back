package com.insysred.isp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.FacturaPromesaPago;

@Repository
public interface FacturaPromesaPagoRepository
		extends JpaRepository<FacturaPromesaPago, Long>, JpaSpecificationExecutor<FacturaPromesaPago> {

	@Query(value = "select fp from FacturaPromesaPago fp where fp.factura.id =?1")
	Optional<FacturaPromesaPago> getByFacturaId(Long idFactura);
}
