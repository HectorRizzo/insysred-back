package com.insysred.isp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.FacturaPromesaPago;
import com.insysred.isp.entities.Recibo;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long>, JpaSpecificationExecutor<Recibo> {

	@Query(value = "select re from Recibo re where re.factura.id =?1")
	Optional<Recibo> getByFacturaId(Long idFactura);
}
