package com.insysred.isp.repository;

import com.insysred.isp.entities.Routers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouterRepository extends JpaRepository<Routers, Long> {
    @Query(value = "select r from Routers r where r.sucursal.id = ?1")
    List<Routers> getRoutersBySucur(Long idSucuc);

    @Query(value = "select r from Routers r where r.ip = ?1")
    Optional<Routers> getByIp(String ip);


}
