package com.insysred.isp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Mkserverrules;

@Repository
public interface MkserverrulesRepository extends JpaRepository<Mkserverrules, Long>, JpaSpecificationExecutor<Mkserverrules> {

	@Query(value = "select c from Mkserverrules c where c.idServerrule = ?1")
	List<Mkserverrules> getByRuleId(Long idServerrule);

	@Query(value = "select c from Mkserverrules c")
	List<Mkserverrules> getAll();
} 