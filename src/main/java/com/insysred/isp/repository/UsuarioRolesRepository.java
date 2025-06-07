package com.insysred.isp.repository;

import com.insysred.isp.entities.UsuarioSucursales;
import com.insysred.isp.entities.UsuariosRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRolesRepository extends JpaRepository<UsuariosRoles, Long>, JpaSpecificationExecutor<UsuariosRoles> {

    @Query(value = "SELECT uxr FROM UsuariosRoles uxr WHERE uxr.usuarioId = ?1 AND uxr.sucursalId = ?2")
    List<UsuariosRoles> findByUsuarioSucursalId(Long usuarioId, Long sucursalId);

}
