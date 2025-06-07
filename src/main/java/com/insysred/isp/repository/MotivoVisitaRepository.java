package com.insysred.isp.repository;

import com.insysred.isp.entities.MotivoVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotivoVisitaRepository extends JpaRepository<MotivoVisita, Long> {
    @Query(value = "select m from MotivoVisita m where m.isActive = true")
    List<MotivoVisita> getALlMotivoVisita();
}
