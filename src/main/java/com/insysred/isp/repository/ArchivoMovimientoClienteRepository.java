package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.ArchivoMovimientoCliente;

@Repository
public interface ArchivoMovimientoClienteRepository
		extends JpaRepository<ArchivoMovimientoCliente, Long>, JpaSpecificationExecutor<ArchivoMovimientoCliente> {

	@Query(value = "select c from ArchivoMovimientoCliente c where c.cliente.id = ?1 order by c.id desc limit ?2")
	List<ArchivoMovimientoCliente> getByIdCliente(Long idCliente, Integer limit);
	
	@Query(value = "select c from ArchivoMovimientoCliente c where c.numeroComprobante = ?1")
	ArchivoMovimientoCliente getByNumeroComprobante(Long numeroComprobante);

}
