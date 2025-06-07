package com.insysred.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.ConciliacionManualMovBanco;

@Repository
public interface ConciliacionManualMovBancoRepository
		extends JpaRepository<ConciliacionManualMovBanco, Long>, JpaSpecificationExecutor<ConciliacionManualMovBanco> {

}
