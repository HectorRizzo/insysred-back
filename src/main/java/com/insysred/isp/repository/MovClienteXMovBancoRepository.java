package com.insysred.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insysred.isp.entities.MovClienteXMovBanco;

@Repository
public interface MovClienteXMovBancoRepository
		extends JpaRepository<MovClienteXMovBanco, Long>, JpaSpecificationExecutor<MovClienteXMovBanco> {

	@Query(value = "select c from MovClienteXMovBanco c where c.archivoMovimientoCliente.numeroComprobante = ?1 and c.isActive = true")
	MovClienteXMovBanco getByNumeroComprobante(Long numeroComprobante);

	@Query(value = "select c from MovClienteXMovBanco c left join c.archivoMovimientoCliente mc left join c.detalleArchivoBanco mb where (mc.numeroComprobante = ?1 or mb.documento = ?1) and c.isActive = true")
	MovClienteXMovBanco getByNumeroComprobanteExiste(Long numeroComprobante);

}
