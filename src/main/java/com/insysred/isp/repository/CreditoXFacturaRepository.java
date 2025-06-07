package com.insysred.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.CreditoXFactura;

@Repository
public interface CreditoXFacturaRepository
		extends JpaRepository<CreditoXFactura, Long>, JpaSpecificationExecutor<CreditoXFactura> {
}
