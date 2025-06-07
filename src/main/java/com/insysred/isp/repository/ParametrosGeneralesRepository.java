package com.insysred.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.ParametrosGenerales;

@Repository
public interface ParametrosGeneralesRepository extends JpaRepository<ParametrosGenerales, String> {
}
