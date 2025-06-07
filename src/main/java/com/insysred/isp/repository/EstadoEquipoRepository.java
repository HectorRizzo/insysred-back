package com.insysred.isp.repository;

import com.insysred.isp.entities.EstadoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoEquipoRepository  extends JpaRepository<EstadoEquipo, Long> {
}
