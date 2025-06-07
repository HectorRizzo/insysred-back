package com.insysred.isp.repository;

import com.insysred.isp.entities.Sucursales;
import com.insysred.isp.entities.UsuarioSucursales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioSucursalesRepository extends JpaRepository<UsuarioSucursales, Long>, JpaSpecificationExecutor<UsuarioSucursales> {

    @Query(value = "SELECT uxs FROM UsuarioSucursales uxs WHERE uxs.usuarioId = ?1")
    List<UsuarioSucursales> findByUsuarioId(Long usuarioId);


}
