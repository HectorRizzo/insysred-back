package com.insysred.isp.repository;

import com.insysred.isp.entities.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Long>, JpaSpecificationExecutor<Empleados> {

    @Query("SELECT e FROM Empleados e WHERE e.activo = true AND e.id = ?1")
    Optional<Empleados> findByIdAndActivo(Long id);

    @Query("SELECT e FROM Empleados e WHERE e.activo = true AND e.idCargo = ?1")
    List<Empleados> obtenerEmpleadosPorCargo(Long idCargo);


}
