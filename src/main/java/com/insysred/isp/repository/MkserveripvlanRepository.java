package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Mkserveripvlan;

@Repository
public interface MkserveripvlanRepository extends JpaRepository<Mkserveripvlan, Long>, JpaSpecificationExecutor<Mkserveripvlan> {

	@Query(value = "select c from Mkserveripvlan c where c.id = ?1")
	List<Mkserveripvlan> getByIpId(Long id);
	
	@Query(value = "select c from Mkserveripvlan c")
	List<Mkserveripvlan> getAll();
	
	@Query(value = "select p from Mkserveripvlan p where p.esActivo = ?1")
	Page<Mkserveripvlan> findByStatus(Boolean status, Pageable pageable);
	
	@Query(value = "select p from Mkserveripvlan p where p.idRouter =?1 and p.idvlan = ?2 and p.esActivo = ?3")
	Page<Mkserveripvlan> findByIdRouterAndStatus(Long idRouter,  Long idVlan, Boolean esActivo, Pageable pageable);
	
	@Query(value = "select p from Mkserveripvlan p where p.idRouter =?1 and p.idvlan = ?2 and p.esActivo = ?3 and p.estado = ?4 ")
	Page<Mkserveripvlan> findByIdRouterAndStatus(Long idRouter,  Long idVlan, Boolean esActivo, String estado, Pageable pageable);
	
	@Query(value = "select p from Mkserveripvlan p where p.idRouter =?1 and p.ip = ?2")
	List<Mkserveripvlan> findByIp(Long idRouter, String ip );
} 