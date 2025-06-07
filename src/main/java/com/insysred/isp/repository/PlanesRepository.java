package com.insysred.isp.repository;

import com.insysred.isp.entities.Planes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PlanesRepository extends JpaRepository<Planes, Long>, JpaSpecificationExecutor<Planes> {
@Query(value = "select p from Planes p where p.status = ?1")
Page<Planes> findByStatus(Boolean status,Pageable pageable);
}
