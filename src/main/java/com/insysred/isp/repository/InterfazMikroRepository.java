package com.insysred.isp.repository;

import com.insysred.isp.entities.InterfazMikro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfazMikroRepository extends JpaRepository<InterfazMikro, Long> {

    @Query(value = "select i from InterfazMikro i where i.id = ?1 and i.router.id = ?2")
    List<InterfazMikro> getInterfazMikroByRouter(String idInterfaz, Long idRouter);

    @Query(value = "select i from InterfazMikro i where i.router.id = ?1 and i.type = ?2 order by i.id desc limit 1")
    List<InterfazMikro> getInterfazMikroByVlan(Long idRouter, String type);

    @Query(value = "select i from InterfazMikro i where i.router.id = ?1 and i.name = ?2 order by i.id desc limit 1")
    List<InterfazMikro> getInterfazMikroByName(Long idRouter, String nombre);
}
