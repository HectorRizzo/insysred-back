package com.insysred.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.ArchivoBanco;

@Repository
public interface ArchivoBancoRepository
		extends JpaRepository<ArchivoBanco, Long>, JpaSpecificationExecutor<ArchivoBanco> {
}
