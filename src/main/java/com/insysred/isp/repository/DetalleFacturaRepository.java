package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.DetalleFactura;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long>, JpaSpecificationExecutor<DetalleFactura> {

	@Query(value = "select dp from DetalleFactura dp where dp.factura.id =?1")
	List<DetalleFactura> getByFacturaId(Long idFactura);
}
