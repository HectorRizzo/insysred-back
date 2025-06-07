package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Mkservervlanintrfc;

@Repository
public interface MkservervlanintrfcRepository extends JpaRepository<Mkservervlanintrfc, Long>, JpaSpecificationExecutor<Mkservervlanintrfc> {

	@Query(value = "select c from Mkservervlanintrfc c where c.serverId = ?1")
	List<Mkservervlanintrfc> getByItrfcById(Long serverId);

	@Query(value = "select c from Mkservervlanintrfc c")
	List<Mkservervlanintrfc> getAll();
	
	@Query(value = "select c from Mkservervlanintrfc c where c.serverId = ?1 and c.interfaceInterface = ?2")
	List<Mkservervlanintrfc> getByItrfcByVlan(Long serverId, String vlanName);
} 