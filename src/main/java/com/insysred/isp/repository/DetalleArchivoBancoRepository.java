package com.insysred.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.DetalleArchivoBanco;

@Repository
public interface DetalleArchivoBancoRepository
		extends JpaRepository<DetalleArchivoBanco, Long>, JpaSpecificationExecutor<DetalleArchivoBanco> {

	@Query(value = "select c from DetalleArchivoBanco c where c.documento = ?1 and c.isActive = true")
	DetalleArchivoBanco getByDocumento(Long documento);
}
