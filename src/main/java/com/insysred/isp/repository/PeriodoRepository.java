package com.insysred.isp.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Periodo;
import com.insysred.isp.enums.EstadoPeriodo;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long>, JpaSpecificationExecutor<Periodo> {

	@Query(value = "select p from Periodo p where p.anio = ?1 and p.estadoPeriodo = ?2 order by p.anio desc, p.fechaDesde asc")
	List<Periodo> getByFechaPeriodo(Integer anio, EstadoPeriodo estadoPeriodo);
	
	@Query(value = "select p from Periodo p where p.fechaDesde >= ?1 and p.fechaHasta <= ?2 order by p.fechaDesde asc")
	List<Periodo> getByFechaPeriodoIndividual(Instant intervaloMenor, Instant intervaloMayor);

}
