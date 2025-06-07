package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Mkservervlan;

@Repository
public interface MkservervlanRepository extends JpaRepository<Mkservervlan, Long>, JpaSpecificationExecutor<Mkservervlan> {

	@Query(value = "select c from Mkservervlan c where c.id = ?1")
	List<Mkservervlan> getByVlanId(Long id);

	@Query(value = "select c from Mkservervlan c")
	List<Mkservervlan> getAll();
	
	@Query(value = "select p from Mkservervlan p where p.esActivo = ?1")
	Page<Mkservervlan> findByStatus(Boolean status, Pageable pageable);
	
	@Query(value = "select p from Mkservervlan p where p.idRouter =?1 and p.esActivo = ?2")
	Page<Mkservervlan> findByIdRouterAndStatus(Long idRouter, Boolean status, Pageable pageable);
	
	@Query(value = "select p from Mkservervlan p where p.idRouter =?1 and p.nombre = ?2")
	Mkservervlan findByName(Long idRouter, String nombre );
	
	@Query(value = "select p from Mkservervlan p where p.idRouter =?1 and p.ip = ?2")
	List<Mkservervlan> findByIp(Long idRouter, String ip );
} 