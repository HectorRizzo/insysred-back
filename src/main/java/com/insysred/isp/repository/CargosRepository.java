package com.insysred.isp.repository;

import com.insysred.isp.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargosRepository extends JpaRepository<Cargo, Long>, JpaSpecificationExecutor<Cargo> {
    @Query(nativeQuery = true, value = "select * from cargos_sucursal where cargo_id = :id_cargo and sucursal_id = :id_sucursal")
    List<Object> getCargoBySucursal(@Param("id_cargo") Long id_cargo, @Param("id_sucursal") Long id_sucursal);

    @Query(nativeQuery = true, value = "INSERT INTO public.cargos_sucursal(cargo_id, sucursal_id) VALUES (:id_cargo, :id_sucursal) RETURNING cargo_id")
    Long guardarAsignacion(@Param("id_cargo") Long id_cargo, @Param("id_sucursal") Long id_sucursal);

    @Query(value = "select c from Cargo c where c.nombre = ?1")
    Optional<Cargo> getByNombre(String nombre);

    @Query(value = "select c from Cargo c where c.activo = true")
    List<Cargo> getAllActivos();

    @Query(value = "select c from Cargo c where c.activo = true and c.id = ?1")
    Optional<Cargo> findByIdAndActivo(Long id);

    @Query(value = "select c from Cargo c where c.activo = true and c.departamento.id = ?1")
    List<Cargo> findByDepartamento(Long id);
}
