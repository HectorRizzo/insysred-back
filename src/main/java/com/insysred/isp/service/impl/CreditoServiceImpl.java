package com.insysred.isp.service.impl;

import java.time.Instant;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.insysred.isp.dto.CreditoDTO;
import com.insysred.isp.entities.Credito;
import com.insysred.isp.filtros.FiltroCredito;
import com.insysred.isp.mapper.CreditoMapper;
import com.insysred.isp.repository.CreditoRepository;
import com.insysred.isp.service.declare.CreditoService;

@Service
public class CreditoServiceImpl implements CreditoService {

	@Autowired
	private CreditoRepository creditoRepository;

	@Autowired
	private CreditoMapper creditoMapper;

	@Override
	public Page<Credito> obtenerCreditos(PageRequest paginaRequest, String filtro, Instant fechaInicio,
			Instant fechaFin, String estado) {
		if (ObjectUtils.isNotEmpty(filtro) && ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return creditoRepository.findAll(FiltroCredito.contieneFechaMasTexto(filtro, fechaInicio, fechaFin),
					paginaRequest);
		} else if (ObjectUtils.isNotEmpty(fechaInicio) && ObjectUtils.isNotEmpty(fechaFin)) {
			return creditoRepository.findAll(FiltroCredito.contieneFecha(fechaInicio, fechaFin), paginaRequest);
		} else {
			return creditoRepository.findAll(FiltroCredito.contieneTexto(filtro, estado), paginaRequest);
		}
	}

	@Override
	public CreditoDTO obtenerCreditoId(Long idCredito) {
		return creditoMapper.toDto(creditoRepository.getReferenceById(idCredito));
	}
}
