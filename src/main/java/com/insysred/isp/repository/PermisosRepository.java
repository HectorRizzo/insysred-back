package com.insysred.isp.repository;

import com.insysred.isp.entities.Modulos;
import com.insysred.isp.entities.PermisosXRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermisosRepository extends JpaRepository<PermisosXRoles, Long>, JpaSpecificationExecutor<PermisosXRoles> {

    @Query(value = "SELECT m FROM Modulos m ")
    List<Modulos> getModulos();

    @Query(value = "SELECT m FROM Modulos m JOIN PermisosXRoles pxr ON m.id = pxr.idModulo" +
            " JOIN UsuariosRoles ur ON pxr.idRol = ur.rolId JOIN RolOld r ON ur.rolId = r.id" +
            " WHERE ur.usuarioId = ?1 AND ur.sucursalId = ?2 AND pxr.activo = true" +
            " AND ur.activo = true AND m.activo = true AND r.isActive = true")
    List<Modulos> getModulosByUsuario(Long idUsuario, Long idSucursal);

    @Query(value = "SELECT m FROM Modulos m WHERE m.id = ?1")
    Optional<Modulos> getModuloById(Long id);

    @Query(value = "SELECT pxr FROM PermisosXRoles pxr WHERE pxr.idRol = ?1 AND pxr.idModulo = ?2")
    Optional<PermisosXRoles> getPermisoByRolModulo(Long idRol, Long idModulo);

    @Query(value = "SELECT pxr.id as id, pxr.idRol as idRol, r.nombre as nombreRol," +
            " pxr.idModulo as idModulo, m.nombreModulo as nombreModulo, pxr.activo as activo " +
            "FROM PermisosXRoles pxr JOIN RolOld r ON pxr.idRol = r.id JOIN Modulos m ON pxr.idModulo = m.id " +
            "WHERE r.nombre LIKE %?1% OR m.nombreModulo LIKE %?1% Order By pxr.id ASC")
    Page<Object[]> obtenerTodosPermisos(Pageable pageable, String filtro);

    @Query(value = "SELECT pxr FROM PermisosXRoles pxr WHERE pxr.idRol = ?1")
    List<PermisosXRoles> obtenerListPermisosXRol(Long idRol);
}
