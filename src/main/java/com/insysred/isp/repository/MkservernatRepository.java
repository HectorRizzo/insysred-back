package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Mkservernat;
 

@Repository
public interface MkservernatRepository extends JpaRepository<Mkservernat, Long>, JpaSpecificationExecutor<Mkservernat> {

	@Query(value = "select c from Mkservernat c where c.idServernat = ?1")
	List<Mkservernat> getByNatId(Long idServernat);

	@Query(value = "select c from Mkservernat c")
	List<Mkservernat> getAll();
} 