package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Mktservidor;

@Repository
public interface MktservidoresRepository extends JpaRepository<Mktservidor, Long>, JpaSpecificationExecutor<Mktservidor> {

	@Query(value = "select c from Mktservidor c where c.serverid = ?1")
	List<Mktservidor> getByServerId(Long serverId);

	@Query(value = "select c from Mktservidor c")
	List<Mktservidor> getAll();
} 