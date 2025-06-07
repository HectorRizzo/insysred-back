package com.insysred.isp.repository;

import com.insysred.isp.entities.OrdenAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenAtencionRepository extends JpaRepository<OrdenAtencion, Long> {
}
