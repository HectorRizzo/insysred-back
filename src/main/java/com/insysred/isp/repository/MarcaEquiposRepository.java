package com.insysred.isp.repository;

import com.insysred.isp.dto.MarcaEquiposDto;
import com.insysred.isp.entities.MarcaEquipos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaEquiposRepository  extends JpaRepository<MarcaEquipos, Long>, JpaSpecificationExecutor<MarcaEquipos> {
    @Query("select m from MarcaEquipos m")
    List<MarcaEquipos> findAllEquipos();

    @Query("select m from MarcaEquipos m where m.nombreMarca like %:filtro% or m.nombreModelo like %:filtro%")
    Page<MarcaEquipos> findAllEquiposPaginacion(PageRequest pageRequest, String filtro);
}
