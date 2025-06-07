package com.insysred.isp.repository;

import com.insysred.isp.entities.HorarioVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioVisitaRepository extends JpaRepository<HorarioVisita, Long> {
    @Query(value = "select h from HorarioVisita h where h.isActive = true ")
    List<HorarioVisita> obtenerHorarios();
}
