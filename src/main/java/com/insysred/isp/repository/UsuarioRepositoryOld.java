package com.insysred.isp.repository;

import com.insysred.isp.entities.RolOld;
import com.insysred.isp.entities.UsuarioOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositoryOld extends JpaRepository<UsuarioOld, Long> {
    //List<RolOld> findByRolId(Long id);

    //@Query(value = "select u from Usuario u where u.sucursales = ?1")
    //List<Usuario> usuarioSucursal(Long codSucursal);

    //@Query(value = )
    List<UsuarioOld> findBySucursalesId(@Param("sucursalId") Long sucursalId);

    @Query(value = "select u from UsuarioOld u where u.isActive = true and u.rolOld.nombre ='TECNICO'")
    List<UsuarioOld> findToTecnicos();
}
