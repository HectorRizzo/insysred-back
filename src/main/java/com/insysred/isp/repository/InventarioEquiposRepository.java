package com.insysred.isp.repository;

import com.insysred.isp.dto.InventarioEquipoDTO;
import com.insysred.isp.entities.InventarioEquipos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventarioEquiposRepository extends JpaRepository<InventarioEquipos, Long>, JpaSpecificationExecutor<InventarioEquipos> {

    @Query(value = "select i from InventarioEquipos i where i.id = ?1")
    InventarioEquipos getById(Long idInventarioEquipo);

    @Query("select i from InventarioEquipos i")
    Page<InventarioEquipoDTO> findAllEquipos(Pageable pageable);

}
