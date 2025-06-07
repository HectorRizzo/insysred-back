package com.insysred.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Rubros;

@Repository
public interface RubrosRepository extends JpaRepository<Rubros, Long>, JpaSpecificationExecutor<Rubros> {
}
