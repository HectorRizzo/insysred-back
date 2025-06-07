package com.insysred.isp.service.declare;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.insysred.isp.dto.CreditoDTO;
import com.insysred.isp.entities.Credito;

public interface CreditoService {

	Page<Credito> obtenerCreditos(PageRequest paginaRequest, String filtro, Instant fechaInicio, Instant fechaFin,
			String estado);

	CreditoDTO obtenerCreditoId(Long idCredito);

}
