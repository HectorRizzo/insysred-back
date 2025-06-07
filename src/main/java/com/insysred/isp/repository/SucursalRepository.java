package com.insysred.isp.repository;

import com.insysred.isp.entities.Sucursales;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursales, Long> {
  @Query(value = "Select suc from Sucursales suc where suc.isActive = true ")
  List<Sucursales> getSucursalByActive();

  @Query(value = "SELECT s from Sucursales s JOIN UsuarioSucursales uxs ON s.id = uxs.sucursalId WHERE uxs.usuarioId = ?1 AND s.isActive = true AND uxs.activo = true")
  List<Sucursales> getSucursalesByUsuarioId(Long usuarioId);

  @Query("SELECT s FROM Sucursales s JOIN FETCH s.usuarioOlds u WHERE s.id = :id")
  Object findByIdWithUsuarios(@Param("id") Long id);

  @Query(nativeQuery = true, value = "select CASE WHEN COUNT(DISTINCT cliente_id) = 1 THEN TRUE ELSE FALSE END AS presente from cliente_sucursal where cliente_id = ?1 and sucursal_id = ?2")
  Boolean estaAsignado(Long idCliente, Long idSucursal);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "delete from cliente_sucursal where cliente_id = ?1 and sucursal_id = ?2")
  void eliminaAsignacion(Long idCliente, Long idSucursal);

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value = "INSERT INTO public.cliente_sucursal(cliente_id, sucursal_id) VALUES (?1, ?2)")
  void crearAsignacion(Long idCliente, Long idSucursal);

//  @Query("SELECT uxs FROM USUARIOS_S
}
