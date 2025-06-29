package com.insysred.isp.repository;

import com.insysred.isp.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long>, JpaSpecificationExecutor<Departamento> {

    @Query(value = "select d from Departamento d where d.activo = true")
    List<Departamento> getAllActivos();

    @Query(value = "select d from Departamento d where d.activo = true and d.id = ?1")
    Optional<Departamento> findByIdAndActivo(Long id);


}
