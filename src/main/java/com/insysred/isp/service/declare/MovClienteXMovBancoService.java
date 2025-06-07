package com.insysred.isp.service.declare;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.insysred.isp.entities.MovClienteXMovBanco;

public interface MovClienteXMovBancoService {

	Page<MovClienteXMovBanco> obtenerConciliacion(PageRequest paginaRequest, String filtroCliente,
			String filtroComprobante, Long filtroBanco, Instant fechaInicio, Instant fechaFin, String estado);

	void conciliacionManual(Long idMovClienteXMovBanco);

}
