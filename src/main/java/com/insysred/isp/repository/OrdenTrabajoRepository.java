package com.insysred.isp.repository;

import com.insysred.isp.entities.OrdenTrabajo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long>, JpaSpecificationExecutor<OrdenTrabajo> {

    @Query("SELECT ot FROM OrdenTrabajo ot WHERE ot.esActivo = true ")
    Page<OrdenTrabajo> getAllActivos(Pageable pageable);
}
