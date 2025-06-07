package com.insysred.isp.repository;

import com.insysred.isp.entities.Cliente;
import com.insysred.isp.enums.TipoDocumento;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    @Query(nativeQuery = true, value = "select * from cliente_sucursal where cliente_id = :id_cliente and sucursal_id = :id_sucursal")
    List<Object> getClienteBySucursal(@Param("id_cliente") Long id_cliente, @Param("id_sucursal") Long id_sucursal);

   @Query(nativeQuery = true, value = "INSERT INTO public.cliente_sucursal(cliente_id, sucursal_id) VALUES (:id_cliente, :id_sucursal) RETURNING cliente_id")
   Long guardarAsignacion(@Param("id_cliente") Long id_cliente, @Param("id_sucursal") Long id_sucursal);
   
   @Query(value = "select c from Cliente c where c.tipoDocumento = ?1 and c.identificacion = ?2")
   Cliente getByIdentificacion(TipoDocumento tipoDocumento, String identificacion);

    @Query("select cli from Cliente cli")
    List<Cliente> getClientes(Specification<Cliente> spec);

    @Query("select cli from Cliente cli where cli.identificacion = ?1")
    List<Cliente> getClientesByIdentificacion(String identificacion);
}
