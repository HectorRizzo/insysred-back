package com.insysred.isp.repository;

import com.insysred.isp.entities.RepresentanteLegal;
import com.insysred.isp.entities.Routers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepresentanteLegalRepository extends JpaRepository<RepresentanteLegal, Long> {

    @Query(value = "select rl from RepresentanteLegal rl where rl.identificacion =?1")
    RepresentanteLegal getByIdent(String identificacion);
    @Query(value = "select r from Routers r where r.sucursal.id = ?1")
    List<Routers> getRoutersBySucur(Long idSucuc);

}
