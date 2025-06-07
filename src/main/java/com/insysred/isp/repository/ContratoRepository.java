package com.insysred.isp.repository;

import java.util.List;
import java.util.Optional;

import com.insysred.isp.entities.EstadoContrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.Contrato;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Factura;
import com.insysred.isp.dto.ClienteContratoDTO;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>, JpaSpecificationExecutor<Contrato> {

	@Query(value = "select c from Contrato c where c.cliente.id = ?1")
	List<Contrato> getByIdCliente(Long idCliente);

	@Query(value = "select c from Contrato c where c.isActive = true")
	List<Contrato> getByEstadoContratoValido();

	@Query(value = "select c from Contrato c where c.cliente.id = ?1")
	List<Contrato> getByIdClienteEstadoContrato(Long idCliente);

	@Query(value = "select c from Contrato c where c.cliente.id = ?1 and c.isActive = true")
	List<Contrato> getByIdClienteEstadoContratoValido(Long idCliente);

  @Query(value = "select c from Contrato c where c.numContrato =?1 and c.isActive = true")
  Optional<Contrato> getContratoByNum(Long numContrato);

	@Query("select new com.insysred.isp.dto.ClienteContratoDTO(cli, co.numContrato, co.estadoContrato , co.ip, p.name) from Contrato co right join co.cliente cli left join co.plan p where cli.id in :ids")
	List<ClienteContratoDTO> getClientesByContrato2(@Param("ids") List<Long> ids);


	@Query("select new com.insysred.isp.dto.ClienteContratoDTO(cli, co.numContrato, co.estadoContrato , co.ip, p.name) from Contrato co right join co.cliente cli left join co.plan p where cli.id in :ids")
	Page<ClienteContratoDTO> getClientesByContrato(PageRequest pageRequest, @Param("ids") List<Long> ids);

	@Query(value = "SELECT cli.id, co.numContrato, co.estadoContrato, co.ip, pl.name FROM Cliente cli LEFT JOIN Contrato co ON co.cliente.id = cli.id LEFT JOIN Planes pl ON co.plan.id = pl.id WHERE cli.id IN :ids")
	List<ClienteContratoDTO> getClientesByContrato(@Param("ids") List<Long> ids);

	@Query("select count(cli) from Contrato c right join c.cliente cli left join c.plan p")
	  Long countClientesByContrato(Specification<Cliente> spec);

	@Query("select e from EstadoContrato e where e.nombre = ?1")
	Optional<EstadoContrato> getEstadoContratoByNombre(String nombre);

	@Query("select ec from EstadoContrato ec where ec.activo = true")
	List<EstadoContrato> getEstadosContrato();
}
